import java.net.Socket;

public class Client extends ClientHandler
{
    private String userName;

    public Client(Socket socket, String userName, AccountController accController, Databases db)
    {
        super(socket, db);
        this.userName=userName;
        accController.setUsername(userName);
        accController.setUserNameTextField();
        accController.setBalance(Integer.toString(db.getBalance(userName)));
        accController.setBalanceTextArea();
        accController.setDb(db);
    }
}
