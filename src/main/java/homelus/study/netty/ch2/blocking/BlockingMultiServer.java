package homelus.study.netty.ch2.blocking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlockingMultiServer {

    private Executor executor;

    public static void main(String[] args) throws IOException {
        BlockingMultiServer server = new BlockingMultiServer();
        server.run();
    }

    private void run() throws IOException {

        prepare();

        ServerSocket server = new ServerSocket(8888);

        while (true) {
            System.out.println("접속 대기중");
            final Socket socket = server.accept();

            executor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 실행");
                    OutputStream outputStream = socket.getOutputStream();
                    InputStream inputStream = socket.getInputStream();

                    while (true) {
                        final int read = inputStream.read();
//                        System.out.println(read);
                        outputStream.write(read);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            });
        }

    }

    private void prepare() {
        executor = Executors.newFixedThreadPool(200);
//        executor = Executors.newCachedThreadPool();
    }

}
