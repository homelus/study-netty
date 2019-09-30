package homelus.study.netty.ch2.client;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TestClient {

    private final static AtomicInteger cnt = new AtomicInteger();

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket[] sockets = new Socket[3000];

        for (int i = 0; i < sockets.length; i++) {
            try {
                sockets[i] = new Socket("localhost", 8888);
                System.out.println(i);
            } catch (IOException e) {
                System.err.println("error connection " + e);
            }
        }

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        while (true) {
            for (Socket socket : sockets) {
                service.submit(unchecked(() -> {
                    socket.getOutputStream()
                            .write((cnt.incrementAndGet() + " Hello DoS Attack ").getBytes());
                    while (socket.getInputStream().available() > 0) {
                        socket.getInputStream().read();
                    }
                }));
            }
        }
    }

    private static Runnable unchecked(FunctionWithException o) {
        return new Thread(() -> {
            try {
                o.apply();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }

    private interface FunctionWithException {
        void apply() throws Exception;
    }

}
