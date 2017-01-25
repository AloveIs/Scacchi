package sample.model.messages;

import com.jfoenix.controls.JFXSnackbar;
import javafx.application.Platform;
import sample.client.NetworkManager;
import sample.server.GameServer;
import sample.server.ServerPlayer;

/**
 * Created by Pietro on 14/01/2017.
 */
public class NonValidMoveMessage extends Message {

	public NonValidMoveMessage(ActionType type, String message) {
		super(ActionType.NONVALID, message);
	}

	public NonValidMoveMessage(ActionType type) {
		super(ActionType.NONVALID);
	}

	public NonValidMoveMessage(String message) {
		super(message);
		this.type = ActionType.NONVALID;
	}

	public NonValidMoveMessage() {
		super();
		this.type = ActionType.NONVALID;
	}

	@Override
	public void haveEffect() {
		Platform.runLater(()->NetworkManager.getInstance().getController().snackbarMessage(message));
	}

	@Override
	public void serverAction(GameServer game, ServerPlayer player) {
	}

	@Override
	public void triggerNote(JFXSnackbar snackbar) {
		super.triggerNote(snackbar);
	}
}
