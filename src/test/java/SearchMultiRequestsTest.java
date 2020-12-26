import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SearchMultiRequestsTest {
    private static int numConnections = 5;
    public static void main(String[] args) {
        for(int i = 0 ; i < numConnections ; i++){
            final int finali = i;
            new Thread(() -> {
                try {
                    Socket socket = new Socket("35.225.251.47", 6969);
                    new DataOutputStream(socket.getOutputStream()).writeUTF("·½ÄÒÕØÞðøúþĂĔĜĦtron");
                    System.out.println("Request id : "+finali+(String) new ObjectInputStream(socket.getInputStream()).readObject());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
