package homelus.study.netty.ch2.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NonBlockingServer {

    private Map<SocketChannel, List<byte[]>> keepDataTrack = new HashMap<>();
    private ByteBuffer buffer = ByteBuffer.allocate(2 * 1024);

    public static void main(String[] args) {
        NonBlockingServer server = new NonBlockingServer();
        server.startEchoServer();
    }

    private void startEchoServer() {
        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            if (serverSocketChannel.isOpen() && selector.isOpen()) {
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.bind(new InetSocketAddress(8888));

                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("접속 대기중");

                while (true) {
                    selector.select();
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        keys.remove();

                        if (!key.isValid()) {
                            continue;
                        }

                        if (key.isAcceptable()) {
                            this.acceptOP(key, selector);
                        } else if (key.isReadable()) {
                            this.readOP(key);
                        } else if (key.isWritable()) {
                            this.writeOP(key);
                        }
                    }
                }
            }
            else {
                System.out.println("서버 소켓 생성 실패");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void acceptOP(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        final SocketChannel socketChannel = serverChannel.accept();
        socketChannel.configureBlocking(false);

        System.out.println("클라이언트 연결: " + socketChannel.getRemoteAddress());

        keepDataTrack.put(socketChannel, new ArrayList<>());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void readOP(SelectionKey key) {
        try {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            buffer.clear();
            int numRead = -1;

            try {
                numRead = socketChannel.read(buffer);
            } catch (IOException e) {
                System.err.println("데이터 읽기 에러!");
            }

            if (numRead == -1) {
                this.keepDataTrack.remove(socketChannel);
                System.out.println("클라이언트 연결 종료: " + socketChannel.getRemoteAddress());
                socketChannel.close();
                key.cancel();
                return;
            }

            TimeUnit.SECONDS.sleep(5);

            byte[] data = new byte[numRead];
            System.arraycopy(buffer.array(), 0, data, 0, numRead);
            System.out.println(new String(data, StandardCharsets.UTF_8) + " from " + socketChannel.getRemoteAddress());

            doEchoJob(key, data);
        } catch (IOException e) {
            System.err.println(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doEchoJob(SelectionKey key, byte[] data) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        List<byte[]> channelData = keepDataTrack.get(socketChannel);
        channelData.add(data);

        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void writeOP(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        final List<byte[]> channelData = keepDataTrack.get(socketChannel);
        Iterator<byte[]> its = channelData.iterator();

        while (its.hasNext()) {
            byte[] it = its.next();
            its.remove();
            socketChannel.write(ByteBuffer.wrap(it));
        }

        key.interestOps(SelectionKey.OP_READ);
    }


}
