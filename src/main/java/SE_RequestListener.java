

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
    JTextArea statusLabel;
    JButton btnStartSandse;
    public SE_RequestListener(JTextArea statusLabel, JButton btnStartSandse) {
        this.statusLabel = statusLabel;
        this.btnStartSandse = btnStartSandse;
    }
    @Override
    public void run() {
        btnStartSandse.setText("Loading...");
        btnStartSandse.setEnabled(false);
        Connection connection = ConnectionManager.getConnection();
        if(connection!=null) {
            try {
                serverSocket = new ServerSocket(6969);
                statusLabel.append(SE_StartMessage+6969);
                btnStartSandse.setText("Stop SAnD_SE");
                btnStartSandse.setEnabled(true);
                while(active) {
                    Socket socket = serverSocket.accept();
                    new SE_RequestHandler(statusLabel, socket).start();
                }
            }catch(BindException e) {
                statusLabel.append(SE_StartFailedMessage);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        btnStartSandse.setText("Start SAnD_SE");
        btnStartSandse.setEnabled(true);

    }

    public void stopListening() {
        active = false;
        if(serverSocket!=null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        statusLabel.append(SE_StopMessage);
        btnStartSandse.setText("Start SAnD_SE");


    }
}
