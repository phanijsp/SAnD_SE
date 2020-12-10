import java.net.Socket;
import java.util.Queue;

public class SE_RequestWorker extends Thread {
    Queue<Socket> socketQueue = null;

    SE_RequestWorker(Queue<Socket> socketQueue) {
        this.socketQueue = socketQueue;
    }

    @Override
    public void run() {
        while (socketQueue != null) {
            Socket socket = socketQueue.poll();
            if (socket != null) {
                System.out.println("Worker working with client " + socket.getInetAddress().getHostAddress());
                new SE_RequestHandler(socket).run();
            }else{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
