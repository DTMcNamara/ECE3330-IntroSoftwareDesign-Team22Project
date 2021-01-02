import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class AccountController {

    private Scene scene;

    private static String username;

    private static String balance;

    private static Databases db;


    @FXML
    private Text balanceText;

    @FXML
    private AnchorPane transactionPane;

    @FXML
    private Text userNameText;

    @FXML
    private AnchorPane logOutAnchor;

    @FXML
    private TextArea transactionField;

    public ArrayList<String> list = new ArrayList<String>();




    public void transferToBank() throws IOException{
        FXMLLoader load = new FXMLLoader(getClass().getResource("BankTransferView.fxml"));
        Parent window = load.load();
        BankTransferController bankTransfer = load.<BankTransferController>getController();
        bankTransfer.setThisUser(username);
        bankTransfer.setBalance(balance);
        bankTransfer.setDb(db);
        this.scene = new Scene(window);
        displayScene();
    }

    public void returnHome() throws IOException
    {
        FXMLLoader load = new FXMLLoader(getClass().getResource("AccountView.fxml"));
        Parent window = load.load();
        AccountController accController = load.<AccountController>getController();
        accController.setUserNameTextField();
        accController.setBalance(Integer.toString(db.getBalance(username)));
        accController.setBalanceTextArea();
        accController.setDb(db);
        accController.setTransactionField();
        this.scene = new Scene(window);
        displayScene();
    }

    public void payAction() throws IOException
    {
        FXMLLoader load = new FXMLLoader(getClass().getResource("TransferFundsView.fxml"));
        Parent window = load.load();
        TransferFundsController transferFunds = load.<TransferFundsController>getController();
        transferFunds.setThisUser(username);
        transferFunds.setBalance(balance);
        transferFunds.setDb(db);
        this.scene = new Scene(window);
        displayScene();
    }

    public void logOutAccountAction() throws IOException
    {
        FXMLLoader load = new FXMLLoader(getClass().getResource("HomePageView.fxml"));
        Parent window = load.load();
        this.scene = new Scene(window);
        displayScene();
    }

    public void deleteAccountAction() throws IOException
    {
        db.deleteUser(username);
        FXMLLoader load = new FXMLLoader(getClass().getResource("HomePageView.fxml"));
        Parent window = load.load();
        this.scene = new Scene(window);
        displayScene();
    }

    public void setTransactionField()
    {
        ArrayList<String>record=db.getMasterRecord(username);

        for(int i = 0; i < record.size(); i++)
        {
            list.add(record.get(i));
            transactionField.appendText(list.get(i) + "\n");
        }
    }

    private void displayScene(){
        Stage stage = LaunchApplication.getStage();
        stage.setScene(this.scene);
    }

    public void logOutAnchorAction(){
        logOutAnchor.setLayoutX(0.01);
    }

    @FXML
    public void setUserNameTextField()
    {
        userNameText.setText("Welcome " + username + "!");
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBalanceTextArea() {
        balanceText.setText("$" + balance);
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @FXML
    public void refreshBalance() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("AccountView.fxml"));
        Parent window = load.load();
        AccountController accController = load.<AccountController>getController();
        accController.setUserNameTextField();
        accController.setBalance(Integer.toString(db.getBalance(username)));
        accController.setBalanceTextArea();
        accController.setDb(db);
        accController.setTransactionField();
        this.scene = new Scene(window);
        displayScene();
    }


    public void setDb(Databases db)
    {
        this.db = db;
    }


}
