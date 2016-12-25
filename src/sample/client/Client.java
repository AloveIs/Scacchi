package sample.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.view.GameViewController;


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

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/chessboard1.fxml"));

		Parent root = loader.load();

		GameViewController controller = (GameViewController) loader.getController();
		primaryStage.getIcons().add(new Image("sample/view/icon.png"));
		primaryStage.setTitle("Scacchi");
		//primaryStage.setFullScreen(true);
		Scene scene = new Scene(root , 800, 800);
		controller.setScene(scene);

		//already present in CSS
		//Image sfondo = new Image("sample/view/sfondo2.jpg");

		primaryStage.setMinHeight(800);
		primaryStage.setMinWidth(1000);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		//create the instance of the SessionManager singleton
		SessionManager.getInstance();
		launch(args);
	}
}