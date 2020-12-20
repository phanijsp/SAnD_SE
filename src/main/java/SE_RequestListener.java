

import java.sql.Connection;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class SE_RequestListener extends Thread{
    final private String SE_StartMessage = "\nStatus: SE running at port ";
    final private String SE_StartFailedMessage = "\nStatus: SE failed to start";
    final private String SE_StopMessage = "\nStatus: Stopped SAnD_SE";

    private ServerSocket serverSocket = null;
    private boolean active = true;


    @Override
    public void run() {

        Connection connection = ConnectionManager.getConnection();
        if(connection!=null) {
            try {
                serverSocket = new ServerSocket(6969);
                System.out.println(SE_StartMessage+6969);
                SE_RequestMaster se_requestMaster = new SE_RequestMaster(3);
                System.out.println("Started RequestMaster");
                while(active) {
                    Socket socket = serverSocket.accept();
                    se_requestMaster.addToQueue(socket);
//                    new SE_RequestHandler(socket).start();
                }
            }catch(BindException e) {
                System.out.println(SE_StartFailedMessage);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }


    }
}
