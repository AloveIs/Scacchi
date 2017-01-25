package sample.view.popups;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import sample.client.NetworkManager;
import sample.client.SessionManager;
import sample.model.pieces.PieceColor;

/**
 * Created by Pietro on 19/01/2017.
 */
public class GiveUpPopup {

	private JFXDialog jfxDialog;

	public GiveUpPopup(StackPane stackPane) {

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.getStyleClass().add("selectWhiteButton");
		//### HEADING
		Label head = new Label("ti sei arreso, ha vinto : " + NetworkManager.getInstance().getOpponent().getName() + "!");
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




		exitBtn.setOnAction(event -> {
			//on abort close the popup
			jfxDialog.close();
			Platform.runLater(()-> SessionManager.getInstance().loadStartScreen());
		});
	}
}
