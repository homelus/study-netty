package homelus.study.netty.ch2.blocking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingSingleServer {

    public static void main(String[] args) throws IOException {
        BlockingSingleServer server = new BlockingSingleServer();
        server.run();
    }

    private void run() throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("접속 대기중");

        while (true) {
            Socket socket = server.accept();
            System.out.println("connect client");

            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            while (true) {
                int request = in.read();
                out.write(request);
            }
        }
    }

}
