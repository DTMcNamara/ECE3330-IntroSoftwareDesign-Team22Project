import java.net.Socket;

public class ClientHandler implements Runnable
{
    private final Socket socket;
    protected Databases db;

    public ClientHandler(Socket socket, Databases db)
    {
        this.socket = socket;
        this.db = db;
    }

    @Override
    public synchronized void run()
    {
        db = new Databases();
    }

}


