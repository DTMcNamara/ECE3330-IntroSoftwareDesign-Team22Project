import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    private Scene scene;

    @FXML
    private TextField userName1;

    @FXML
    private TextField passWordCreation;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField registerEmailTextField;

    @FXML
    private Text emailErrorText;

    @FXML
    private Text userNameErrorText;

    private Databases db = new Databases();

    public void RegisterAccount() throws IOException {
        String userFName =firstNameField.getText();
        String userLName=lastNameField.getText();
        String userPassword=passWordCreation.getText();
        String userInputUsername = userName1.getText();
        String userInputEmail = registerEmailTextField.getText();
        checkUsername();
        checkEmail();

        if(!(checkIfEmpty(userName1)) && !(checkIfEmpty(passWordCreation)) && !(checkIfEmpty(firstNameField)) && !(checkIfEmpty(lastNameField)) && !(checkIfEmpty(registerEmailTextField)) && checkEmail() && checkUsername()){
            FXMLLoader load = new FXMLLoader(getClass().getResource("AccountView.fxml"));
            Parent window = load.load();
            AccountController accController = load.<AccountController>getController();
            accController.setUsername(userInputUsername);
            accController.setUserNameTextField();
            accController.setBalance(Integer.toString(0));
            accController.setBalanceTextArea();
            accController.setDb(db);
            accController.setTransactionField();
            this.scene = new Scene(window);
            displayScene();
            db.createUser(userInputUsername,userInputEmail,userLName,userFName,userPassword);
        }
    }

    public void cancelSignUp() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("HomePageView.fxml"));
        Parent window = load.load();
        this.scene = new Scene(window);
        displayScene();
    }

    public boolean checkUsername(){
        String userInputUsername = userName1.getText();
        if(db.checkUniqueUN(userInputUsername)){
            return true;
        }else{
            userNameErrorText.setText("User Name Taken.");
            userNameErrorText.setVisible(true);
            return false;
        }
    }

    public boolean checkEmail(){
        String userInputEmail = registerEmailTextField.getText();
        if(userInputEmail.contains("@")){
            if(db.checkUniqueEA(userInputEmail)){
                return true;
            }
            else{
                emailErrorText.setText("Email Already Exists!");
                emailErrorText.setVisible(true);
                return false;
            }
        }
        //TODO Add text under email to say enter correct email address.
        else {
            emailErrorText.setText("Email is Invalid!");
            emailErrorText.setVisible(true);
            return false;
        }
    }
    public boolean checkIfEmpty(TextField textField){
        if(textField.getText() == null || textField.getText().trim().isEmpty()){
            return true;
        }
        return false;
    }

    public void displayScene(){
        Stage stage = LaunchApplication.getStage();
        stage.setScene(this.scene);
    }
}
