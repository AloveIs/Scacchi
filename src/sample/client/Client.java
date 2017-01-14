package sample.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.view.GameViewController;
import sample.view.ImagePicker;
import sun.nio.ch.Net;


/** Main class for the client application
 * Created by Pietro on 15/12/2016.
 */
public class Client extends Application{


	@Override
	public void start(Stage primaryStage) throws Exception{
		SessionManager.getInstance().setStage(primaryStage);

		//Parent root = FXMLLoader.load(getClass().getResource("../view/startScreen.fxml"));
		//Parent root = FXMLLoader.load(getClass().getResource("../view/prova2.fxml"));
		//FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/prova1.fxml"));


		//local game loader
		//FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/chessboard1.fxml"));

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/startScreen1.fxml"));

		Parent root = loader.load();

		primaryStage.getIcons().add(new Image("/sample/view/icon.png"));
		primaryStage.setTitle("Scacchi");
		//primaryStage.setFullScreen(true);
		Scene scene = new Scene(root);

		//########### da utilizzare solo con scacchiera1
		//GameViewController controller = (GameViewController) loader.getController();
		//controller.setScene(scene);
		//##############################

		//already present in CSS
		//Image sfondo = new Image("sample/view/sfondo2.jpg");
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