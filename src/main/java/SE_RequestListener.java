

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SE_RequestListener extends Thread {


    @Override
    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            ServerSocket serverSocket = new ServerSocket(6969);
            String SE_StartMessage = "\nStatus: SE running at port ";
            System.out.println(SE_StartMessage + 6969);
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> new SE_RequestHandler(socket).run());
            }
        } catch (BindException e) {
            String SE_StartFailedMessage = "\nStatus: SE failed to start";
            System.out.println(SE_StartFailedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
