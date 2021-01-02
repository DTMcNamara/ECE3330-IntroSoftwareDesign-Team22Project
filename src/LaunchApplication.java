import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LaunchApplication extends Application {

    public static Stage stageObject;

    @Override
    public void start(Stage stage) throws Exception {
        stageObject = stage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePageView.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Gull & Bull Financial");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage(){
        return stageObject;
    }

}
