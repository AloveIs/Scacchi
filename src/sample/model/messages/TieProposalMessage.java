package sample.model.messages;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.client.NetworkManager;
import sample.server.GameServer;
import sample.server.ServerPlayer;

/**
 * Created by Pietro on 14/01/2017.
 */
public class TieProposalMessage extends Message {

	public TieProposalMessage() {
		super(ActionType.SPECIAL);
	}

	@Override
	public void haveEffect() {
		Platform.runLater(()->{
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.getStyleClass().add("selectWhiteButton");
			//### HEADING
			Label head = new Label(NetworkManager.getInstance().getOpponent().getName() + "ha proposto un pareggio");
			layout.setHeading(head);
			//### ACTION
			JFXButton acceptButton = new JFXButton("Accetta");
			acceptButton.getStyleClass().add("acceptButton");

			JFXButton declineButton = new JFXButton("Rifiuta");
			declineButton.getStyleClass().add("declineButton");

			layout.setActions(new HBox(20, acceptButton, declineButton));
			//#################### END
			JFXDialog jfxDialog = new JFXDialog(NetworkManager.getInstance().getController().stackPane,layout, JFXDialog.DialogTransition.CENTER);
			jfxDialog.show();
			//reset the nameField after an illegal name is entered

			acceptButton.setOnAction(event -> {
				NetworkManager.getInstance().send(new TieResponseMessage(true));
				NetworkManager.getInstance().getController().tieAccepted();
				jfxDialog.close();
			});

			declineButton.setOnAction(event -> {
				NetworkManager.getInstance().send(new TieResponseMessage(false));
				jfxDialog.close();
			});
		});
	}

	@Override
	public void serverAction(GameServer game, ServerPlayer player) {
		game.toOpponent(player, new TieProposalMessage());
	}
}
