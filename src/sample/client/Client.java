package sample.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.view.ImagePicker;


/** Main class for the client application
 * Created by Pietro on 15/12/2016.
 */
public class Client extends Application{


	@Override
	public void start(Stage primaryStage) throws Exception{
		SessionManager.getInstance().setStage(primaryStage);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/startScreen.fxml"));

		Parent root = loader.load();

		primaryStage.getIcons().add(new Image("/sample/view/images/icon.png"));
		primaryStage.setTitle("Scacchi");
		//primaryStage.setFullScreen(true);
		Scene scene = new Scene(root);

		SessionManager.getInstance().setStartScreen(scene);
		primaryStage.setMinHeight(800);
		primaryStage.setMinWidth(1000);
		primaryStage.setScene(scene);
		primaryStage.show();
		ImagePicker.initalize(); //otherwise throws Internal graphics not initialized yet: javafx
		NetworkManager.getInstance().loadScene();
	}

	public static void main(String[] args) {
		//create the instance of the SessionManager singleton
		SessionManager.getInstance();
		NetworkManager.getInstance();
		launch(args);

	}
}