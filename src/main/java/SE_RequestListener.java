

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;


public class SE_RequestListener extends Thread {
    final private String SE_StartMessage = "\nStatus: SE running at port ";
    final private String SE_StartFailedMessage = "\nStatus: SE failed to start";

    private ServerSocket serverSocket = null;
    private boolean active = true;


    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(6969);
            System.out.println(SE_StartMessage + 6969);
            while (active) {
                Socket socket = serverSocket.accept();
                new SE_RequestHandler(socket).start();
            }
        } catch (BindException e) {
            System.out.println(SE_StartFailedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
