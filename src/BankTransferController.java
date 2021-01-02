import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class BankTransferController {

    private Scene scene;

    private Databases db;

    private String balance;

    private String thisUser;

    @FXML
    private TextArea amountToBankArea;

    @FXML
    private TextArea transferMessage;


    public void transferToBank()throws IOException
    {
        transferMessage.setText("$"+amountToBankArea.getText()+" will be transferred to your bank.");
        transferMessage.setVisible(true);
        db.updateBalance(thisUser,-Integer.parseInt(amountToBankArea.getText()));
        setBalance(Integer.toString(Integer.parseInt(balance)-Integer.parseInt(amountToBankArea.getText())));
    }

    public void returnHome() throws IOException
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

    private void displayScene()
    {
        Stage stage = LaunchApplication.getStage();
        stage.setScene(this.scene);
    }

    public void setDb(Databases db)
    {
        this.db = db;
    }

    public void setBalance(String balance)
    {
        this.balance = balance;
    }

    public void setThisUser(String thisUser)
    {
        this.thisUser = thisUser;
    }
}
