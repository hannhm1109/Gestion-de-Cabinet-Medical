package ma.enset.gestionconsultationbdcc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the main view
        Parent root = FXMLLoader.load(getClass().getResource("/ma/enset/gestionconsultationbdcc/views/main-view.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Cabinet Medical Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}