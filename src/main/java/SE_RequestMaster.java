import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SE_RequestMaster {
    private int workerCount;
    private Queue<Socket> socketQueue = null;
    private ArrayList<SE_RequestWorker> se_requestWorkers;

    SE_RequestMaster(int workerCount) {
        this.workerCount = workerCount;
        socketQueue = new LinkedList<>();
        se_requestWorkers = new ArrayList<>();
        for (int i = 0; i < workerCount; i++) {
            se_requestWorkers.add(new SE_RequestWorker(socketQueue));
        }
        startWorkers();
        System.out.println("Current Worker Count : " + se_requestWorkers.size());
    }

    public void addToQueue(Socket socket) {
        if (socketQueue != null) {
            socketQueue.add(socket);
            System.out.println("Current Queue Size : " + socketQueue.size());
        }
    }
    private void startWorkers(){
        if(se_requestWorkers!=null){
            for (SE_RequestWorker se_requestWorker : se_requestWorkers) {
                se_requestWorker.start();
            }
        }
    }
}
