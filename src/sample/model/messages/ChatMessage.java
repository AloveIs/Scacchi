package sample.model.messages;

import javafx.application.Platform;
import sample.client.NetworkManager;
import sample.server.GameServer;
import sample.server.ServerPlayer;

/**
 * Created by Pietro on 19/01/2017.
 */
public class ChatMessage extends Message {
	public ChatMessage(String message) {
		this.message = message;
	}

	@Override
	public void haveEffect() {
		Platform.runLater(()->{
			NetworkManager.getInstance().getController().chatMessage(this);
		});
	}

	@Override
	public void serverAction(GameServer game, ServerPlayer player) {
		game.toOpponent(player,this);
	}
}
