package sample.view.popups;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import sample.client.NetworkManager;
import sample.client.SessionManager;
import sample.model.pieces.PieceColor;

/** Popup to make a login after a game has finished without having to go back to
 * the main screen
 * Created by Pietro on 19/01/2017.
 */
public class InnerLoginPopup {

	private final JFXDialog jfxDialog;

	public InnerLoginPopup(StackPane stackPane) {

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.getStyleClass().add("selectWhiteButton");
		//### HEADING
		Label head = new Label("Inserisci le tue informazioni : ");
		layout.setHeading(head);
		//### BODY
		JFXTextField nameField = new JFXTextField();
		nameField.setText(NetworkManager.getInstance().getPlayer().getName());
		nameField.setFocusColor(Paint.valueOf("gray"));
		nameField.setPromptText("Nome");

		JFXButton colorBtn = new JFXButton("Bianco");
		colorBtn.getStyleClass().add("bigButton");
		colorBtn.getStyleClass().add("selectWhiteButton");


		colorBtn.setOnAction(event -> {
			if (colorBtn.getText().equals("Bianco")){
				colorBtn.setText("Nero");
				colorBtn.getStyleClass().remove("selectWhiteButton");
				colorBtn.getStyleClass().add("selectBlackButton");

				nameField.getStyleClass().remove("selectWhiteButton");
				nameField.getStyleClass().add("selectBlackButton");

				head.getStyleClass().remove("selectWhiteButton");
				head.getStyleClass().add("selectBlackButton");

				layout.getStyleClass().remove("selectWhiteButton");
				layout.getStyleClass().add("selectBlackButton");
			}else {
				colorBtn.setText("Bianco");
				colorBtn.getStyleClass().remove("selectBlackButton");
				colorBtn.getStyleClass().add("selectWhiteButton");

				nameField.getStyleClass().remove("selectBlackButton");
				nameField.getStyleClass().add("selectWhiteButton");

				head.getStyleClass().remove("selectBlackButton");
				head.getStyleClass().add("selectWhiteButton");

				layout.getStyleClass().remove("selectBlackButton");
				layout.getStyleClass().add("selectWhiteButton");
			}
		});

		VBox container = new VBox(nameField , colorBtn);
		container.setSpacing(10);
		container.setAlignment(Pos.CENTER);
		layout.setBody(container);
		//### ACTION
		JFXButton loginBtn = new JFXButton("Login");
		loginBtn.getStyleClass().add("popupButton");

		JFXButton cancelBtn = new JFXButton("Annulla");
		cancelBtn.getStyleClass().add("popupButton");

		HBox actionContainer = new HBox(loginBtn, cancelBtn);
		actionContainer.setSpacing(10);

		layout.setActions(actionContainer);
		//#################### END
		jfxDialog = new JFXDialog(stackPane,layout, JFXDialog.DialogTransition.CENTER);
		jfxDialog.show();
		//reset the nameField after an illegal name is entered
		nameField.addEventHandler(MouseEvent.MOUSE_ENTERED, event ->{
			nameField.setUnFocusColor(Paint.valueOf("gray"));
		});

		loginBtn.setOnAction(event -> {
			String name = nameField.getText();
			System.out.println(name);
			if ( name == null || name.trim().equals("") || name.trim().equals(" ")|| name.trim().equals("\n")){
				nameField.setUnFocusColor(Paint.valueOf("#fc002b"));
			}else{
				PieceColor color = colorBtn.getText().equals("Bianco") ? PieceColor.WHITE : PieceColor.BLACK ;
				NetworkManager.getInstance().setPlayer(name, color);

				//place a spinner while it connects
				layout.getActions().clear();
				layout.getHeading().clear();
				JFXSpinner spinner = new JFXSpinner();
				spinner.setRadius(20);
				layout.setBody(spinner);
				if (NetworkManager.getInstance().connect()){
					NetworkManager.getInstance().hasGameProperty().addListener((observable, oldValue, newValue) -> {
						Platform.runLater(() ->{
							if (newValue == true)jfxDialog.close();});
					});
				}else{
					Platform.runLater(() -> {
						head.setText("Errore durante la connessione al server!");
						layout.setHeading(head);
						layout.setActions(cancelBtn);
						layout.getBody().clear();
					});
				}
			}
		});

		cancelBtn.setOnAction(event -> {
			jfxDialog.close();
			SessionManager.getInstance().loadStartScreen();
		});

		nameField.setOnKeyPressed(event -> {
			if (event.getCode() != KeyCode.ENTER)
				return;
			String name = nameField.getText();
			System.out.println(name);
			if ( name == null || name.trim().equals("") || name.trim().equals(" ")|| name.trim().equals("\n")){
				nameField.setUnFocusColor(Paint.valueOf("#fc002b"));
			}else{
				PieceColor color = colorBtn.getText().equals("Bianco") ? PieceColor.WHITE : PieceColor.BLACK ;
				NetworkManager.getInstance().setPlayer(name, color);

				//place a spinner while it connects
				layout.getActions().clear();
				layout.getHeading().clear();
				JFXSpinner spinner = new JFXSpinner();
				spinner.setRadius(20);
				layout.setBody(spinner);
				if (NetworkManager.getInstance().connect()){
					NetworkManager.getInstance().hasGameProperty().addListener((observable, oldValue, newValue) -> {
						Platform.runLater(() ->{
							System.out.println("Value changed from " + oldValue + " to " + newValue);
							if (newValue == true)jfxDialog.close();});
					});
				}else{
					Platform.runLater(() -> {
						head.setText("Errore durante la connessione al server!");
						layout.setHeading(head);
						layout.setActions(cancelBtn);
						layout.getBody().clear();
					});
				}
			}
		});

	}
}
