package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.controller.ControllerLaunchScreen;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private Scene scene;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/startScreen.fxml"));
        primaryStage.setTitle("Scacchi");
        scene = new Scene(root);

        Image sfondo = new Image("sample/view/sfondo1.jpg");

        primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(sfondo.getWidth()/sfondo.getHeight()));
        primaryStage.minHeightProperty().bind(scene.widthProperty().divide(sfondo.getWidth()/sfondo.getHeight()));
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);


    }
}
