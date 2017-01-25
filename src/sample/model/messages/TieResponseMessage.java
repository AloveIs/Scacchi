package sample.model.messages;

import javafx.application.Platform;
import sample.client.NetworkManager;
import sample.server.GameServer;
import sample.server.ServerPlayer;

/**
 * Created by Pietro on 17/01/2017.
 */
public class TieResponseMessage extends Message{

	private boolean tie;

	public TieResponseMessage(ActionType type, String message, boolean tie) {
		super(type, message);
		this.tie = tie;
	}

	public TieResponseMessage(ActionType type, boolean tie) {
		super(type);
		this.tie = tie;
	}

	public TieResponseMessage(String message, boolean tie) {
		super(message);
		this.tie = tie;
	}

	public TieResponseMessage(boolean tie) {
		super();
		this.tie = tie;
	}


	@Override
	public void haveEffect() {
		if (tie)
			Platform.runLater(()->NetworkManager.getInstance().getController().tieAccepted());
		else {
			Platform.runLater(()->NetworkManager.getInstance().getController().tieRefused());
		}
	}

	@Override
	public void serverAction(GameServer game, ServerPlayer player) {
		game.toOpponent(player, this);
		if (tie){
			System.out.println("\t\ttie is " + tie);
			game.closeAll();
			game.interruptAll();
		}
	}
}
