
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

import javax.swing.JTextArea;
public class SE_RequestHandler extends Thread{
    JTextArea textArea;
    Socket socket;
    public SE_RequestHandler(JTextArea textArea, Socket socket) {
        this.textArea = textArea;
        this.socket = socket;
    }

    @Override
    public void run() {
        textArea.append("\nClient at "+socket.getInetAddress().toString());
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            String searchQuery = dataInputStream.readUTF();
            textArea.append("\nClient at "+socket.getInetAddress().toString()+" searched for "+searchQuery);

            Thread.sleep(5000);

            dataOutputStream.writeUTF("{\"name\":\"blablabla\"}");
            dataOutputStream.writeUTF("!Q2!89!@09!@");

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
