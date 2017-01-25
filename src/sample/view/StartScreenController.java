package sample.view;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import sample.client.NetworkManager;
import sample.client.SessionManager;
import sample.view.popups.LoginPopup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/** The controller of the start screen
 * Created by Pietro on 25/12/2016.
 */
public class StartScreenController implements Initializable {

	@FXML
	JFXButton playOnlineButton;

	@FXML
	JFXButton playLocallyButton;

	@FXML
	StackPane stackPaneStartScreen;

	//variablesused by this class

	private Scene localGameScene;
	private GameViewController localGameController;
	private OnlineGameViewController onlineGameController;


	@Override
	public void initialize(URL location, ResourceBundle resources){
		playOnlineButton.setOnAction(event	->{
			LoginPopup popup = new LoginPopup(stackPaneStartScreen);
			if ( NetworkManager.getInstance().getPlayer() != null){
				playOnline();
			}
		});

		playLocallyButton.setOnAction(event	-> playLocally());
	}

	private void playOnline() {
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/chessboardOnline.fxml"));
				Parent root = null;
				try {
					root = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				onlineGameController = (OnlineGameViewController) loader.getController();
			}
		});
	}

	private void playLocally(){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/chessboardLocal.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		localGameController = (GameViewController) loader.getController();
		localGameScene = new Scene(root);
		localGameController.setScene(localGameScene);
		SessionManager.getInstance().setScene(localGameScene);
	}

}




		/*localGameScene = new Scene(root);
		localGameController.setScene(localGameScene);
		SessionManager.getInstance().setScene(localGameScene);*/

/*
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/chessboardLocal.fxml"));
		try {
			Parent root = loader.load();
			localGameController = (GameViewController) loader.getController();
			localGameScene = new Scene(root);
		}catch (Exception e){
			e.printStackTrace();
		}

		/*
		final JFXDialog jfxDialog;
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Label("Caricamento ..."));
		layout.setBody(new Label("Cosa vui fare adesso?"));



		jfxDialog = new JFXDialog(stackPaneStartScreen,layout, JFXDialog.DialogTransition.CENTER);
		jfxDialog.show();

		*/