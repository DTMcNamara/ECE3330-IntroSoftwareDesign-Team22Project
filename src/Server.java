import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
    private static final int PORT = 23600;
    private static ArrayList<ClientHandler> clientArrayList = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(60);
    private static ServerSocket server;
    private static Socket socket;

    public static void main(String[] args) throws IOException
    {
        final int backLog = 5000;
        server = new ServerSocket(PORT, backLog);

            while (true)
            {
                System.out.println("Listening...");
                socket = server.accept();
                System.out.println("Accepted");
                Databases dbKey = new Databases();
                ClientHandler clientThread = new ClientHandler(socket, dbKey);
                clientArrayList.add(clientThread);
                pool.execute(clientThread);
            }


    }
}
