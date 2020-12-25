import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SearchTest {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6969);
            new DataOutputStream(socket.getOutputStream()).writeUTF("·½ÄÒÕØÞðøúþĂĔĜĦsai");
            String result = (String) new ObjectInputStream(socket.getInputStream()).readObject();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
