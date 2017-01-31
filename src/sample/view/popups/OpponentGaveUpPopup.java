package sample.view.popups;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import sample.client.NetworkManager;
import sample.client.SessionManager;

/**
 * Created by Pietro on 19/01/2017.
 */
public class OpponentGaveUpPopup {

	private JFXDialog jfxDialog;

	public OpponentGaveUpPopup(StackPane stackPane) {
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.getStyleClass().add("selectWhiteButton");
		//### HEADING
		Label head = new Label(NetworkManager.getInstance().getOpponent().getName() + "si è arreso, hai vinto!");
		layout.setHeading(head);
		//### BODY
		layout.setBody(new Label("Cosa vuoi fare ora?"));
		//### ACTION
		JFXButton newGameBtn = new JFXButton("Nuova partita");
		newGameBtn.getStyleClass().add("popupButton");

		JFXButton exitBtn = new JFXButton("Esci");
		exitBtn.getStyleClass().add("popupButton");

		layout.setActions(new HBox(10,newGameBtn, exitBtn));
		//#################### END
		jfxDialog = new JFXDialog(stackPane,layout, JFXDialog.DialogTransition.CENTER);
		jfxDialog.show();
		//reset the nameField after an illegal name is entered

		newGameBtn.setOnAction(event -> {
			jfxDialog.close();
			new InnerLoginPopup(stackPane);
		});

		exitBtn.setOnAction(event -> {
			//on abort close the popup
			jfxDialog.close();
			Platform.runLater(()-> SessionManager.getInstance().loadStartScreen());
		});
	}
}
