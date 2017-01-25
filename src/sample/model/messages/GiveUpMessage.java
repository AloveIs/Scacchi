package sample.model.messages;

import javafx.application.Platform;
import sample.client.NetworkManager;
import sample.server.GameServer;
import sample.server.ServerPlayer;

/**
 * Created by Pietro on 16/01/2017.
 */
public class GiveUpMessage extends Message {

	public GiveUpMessage(ActionType type, String message) {
		super(type, message);
	}

	public GiveUpMessage(ActionType type) {
		super(type);
	}

	public GiveUpMessage(String message) {
		super(message);
	}

	public GiveUpMessage() {
		super();
	}

	@Override
	public void serverAction(GameServer game, ServerPlayer player) {
		game.toOpponent(player, this);
		game.interruptAll();
		game.closeAll();
	}

	@Override
	public void haveEffect() {
		Platform.runLater(()->{
			NetworkManager.getInstance().getController().snackbarMessage(NetworkManager.getInstance().getOpponent().getName() +" si è arreso!");
			NetworkManager.getInstance().getController().opponentGaveUp();
		});
	}
}
