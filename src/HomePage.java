import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

public class HomePage
{
    @FXML
    public TextField userName;

    @FXML
    public PasswordField passWord;

    @FXML
    private Text couldNotLogIn;

    @FXML
    private Text incorrectUserNamePasswordText;

    @FXML
    private Text noServerConnection;

    private static Socket sock;

    private Scene scene;

    public void signInAction() throws IOException
    {
        try
        {
            sock = new Socket(InetAddress.getLocalHost(),23600);
            String userName = this.userName.getText();
            String passWord = this.passWord.getText();
            Account account = new Account(userName, passWord);

            if (account.checkUser()) {
                FXMLLoader load = new FXMLLoader(getClass().getResource("AccountView.fxml"));
                Parent window = load.load();
                AccountController accController = load.<AccountController>getController();
                accController.setDb(account.getDb());
                accController.setUsername(userName);
                accController.setTransactionField();
                Client client = new Client(sock, userName, accController, account.getDb());
                client.run();
                this.scene = new Scene(window);
                displayScene();
            }
            else
            {
                couldNotLogIn.setVisible(true);
                incorrectUserNamePasswordText.setVisible(true);
            }
        }
        catch (ConnectException e){
            noServerConnection.setVisible(true);
            System.out.println("no server connected");
        }
    }

    public void registerHereAction() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterAccountView.fxml"));
        Parent window = loader.load();
        this.scene = new Scene(window);
        displayScene();
    }

    private void displayScene()
    {
        Stage stage = LaunchApplication.getStage();
        stage.setScene(this.scene);
    }
}
