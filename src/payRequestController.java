import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;


public class payRequestController {

    private Scene scene;

    private Databases db;

    @FXML
    private TextArea userNamePayArea;

    @FXML
    private TextField payAmountToUserField;

    @FXML
    private RadioButton privacyButton;

    @FXML
    private Text invalidAmountEnteredText;

    @FXML
    private Text paymentUnsuccessful;

    @FXML
    private TextField memoField;

    private String targetUser;
    private String thisUser;
    private String balance;

    private final String numberRegX = ".*^[0-9]+$.*";

    public void cancelTransferAction() throws IOException
    {
        FXMLLoader load = new FXMLLoader(getClass().getResource("TransferFundsView.fxml"));
        Parent window = load.load();
        TransferFundsController transFunds = load.<TransferFundsController>getController();
        transFunds.setBalance(balance);
        transFunds.setDb(db);
        this.scene = new Scene(window);
        displayScene();
    }

    public void payToUserAction() throws IOException
    {
            if (payAmountToUserField.getText().matches(numberRegX))
            {
                int amount = Integer.parseInt(payAmountToUserField.getText());
                if (amount < Integer.parseInt(balance))
                {
                    int privacy = 0;
                    if(privacyButton.isSelected())
                    {
                        privacy=1;
                    }
                    invalidAmountEnteredText.setVisible(false);
                    paymentUnsuccessful.setVisible(false);
                    String memo = memoField.getText();
                    db.userTransaction(thisUser, targetUser, amount, privacy, memo);
                    setBalance(Integer.toString(Integer.parseInt(balance) - amount));
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
                else
                {
                    invalidAmountEnteredText.setVisible(false);
                    paymentUnsuccessful.setVisible(true);
                }
            }
            else
            {
                paymentUnsuccessful.setVisible(false);
                invalidAmountEnteredText.setVisible(true);
            }
    }

    public void RequestFromUserButton() throws IOException
    {
        if (payAmountToUserField.getText().matches(numberRegX))
        {
            int amount = Integer.parseInt(payAmountToUserField.getText());
            if (amount < (db.getBalance(targetUser)))
            {
                int privacy = 0;
                if(privacyButton.isSelected())
                {
                    privacy=1;
                }
                invalidAmountEnteredText.setVisible(false);
                paymentUnsuccessful.setVisible(false);
                String memo = memoField.getText();
                db.userTransaction(targetUser, thisUser, amount, privacy, memo);
                setBalance(Integer.toString(Integer.parseInt(balance) + amount));
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
            else
            {
                invalidAmountEnteredText.setVisible(false);
                paymentUnsuccessful.setVisible(true);
            }
        }
        else
        {
            paymentUnsuccessful.setVisible(false);
            invalidAmountEnteredText.setVisible(true);
        }
    }

    public void setUserNamePayArea(String userName)
    {
        targetUser = userName;
        userNamePayArea.setText("How much would you like to pay or request from " + userName + "?");
    }

    private void displayScene()
    {
        Stage stage = LaunchApplication.getStage();
        stage.setScene(this.scene);
    }

    public void setThisUser(String thisUser)
    {
        this.thisUser=thisUser;
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
