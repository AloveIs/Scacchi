package sample.view;

import com.jfoenix.controls.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Created by Pietro on 26/12/2016.
 */
public class LocalPopup {

	private final JFXDialog jfxDialog;

	public LocalPopup(StackPane stackPane , String message) {

		VBox body = new VBox();
		body.setSpacing(5);

		JFXTextField whiteName = new JFXTextField("Bianco");

		JFXTextField blackName = new JFXTextField("Nero");
		blackName.setStyle("-fx-background-color: #262626");
		blackName.setStyle("-fx-fill: whitesmoke");
/*
		body.addEventHandler( whiteName, blackName);
*/
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Label("Inserisci i nomi dei giocatori :"));
		layout.setBody();
		layout.setActions(new JFXButton(("Cose")));
		jfxDialog = new JFXDialog(stackPane,layout, JFXDialog.DialogTransition.CENTER);
		jfxDialog.show();
	}

	void close(){
		jfxDialog.close();
	}
}
