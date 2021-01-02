import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class TransferFundsController
{
    private Scene scene;

    private static Databases db;

    private static String thisUser;

    private static String balance;

    @FXML
    private TextField searchUserNameEmailField;

    @FXML
    private Text userNotFound;

    public void cancelTransferAction() throws IOException
    {
        FXMLLoader load = new FXMLLoader(getClass().getResource("AccountView.fxml"));
        Parent window = load.load();
        AccountController accController = load.<AccountController>getController();
        accController.setUserNameTextField();
        accController.setBalance(Integer.toString(db.getBalance(thisUser)));
        accController.setBalanceTextArea();
        accController.setDb(db);
        accController.setTransactionField();
        this.scene = new Scene(window);
        displayScene();
    }

    public void searchUserNameAction() throws IOException
    {
            String userInput = searchUserNameEmailField.getText();
            if (db.userExists(userInput))
            {
                FXMLLoader load = new FXMLLoader(getClass().getResource("PayRequestView.fxml"));
                Parent window = load.load();
                payRequestController payController = load.<payRequestController>getController();
                payController.setUserNamePayArea(userInput);
                payController.setThisUser(thisUser);
                payController.setBalance(balance);
                payController.setDb(db);
                this.scene = new Scene(window);
                displayScene();
            }
            else
            {
                userNotFound.setVisible(true);
            }
    }

    private void displayScene()
    {
        Stage stage = LaunchApplication.getStage();
        stage.setScene(this.scene);
    }

    public void setThisUser(String thisUser)
    {
        this.thisUser = thisUser;
    }

    public void setBalance(String balance)
    {
        this.balance = balance;
    }

    public void setDb(Databases db)
    {
        this.db = db;
    }
}
