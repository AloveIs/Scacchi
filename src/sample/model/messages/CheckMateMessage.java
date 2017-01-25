package sample.model.messages;

import javafx.application.Platform;
import sample.client.NetworkManager;
import sample.model.Move;
import sample.server.GameServer;
import sample.server.ServerPlayer;

/**
 * Created by Pietro on 14/01/2017.
 */
public class CheckMateMessage extends Message{

	private Move move;

	public CheckMateMessage(ActionType type, String message, Move move) {
		super(ActionType.SPECIAL, message);
		this.move = move;
	}

	public CheckMateMessage(ActionType type, Move move) {
		super(ActionType.SPECIAL);
		this.move = move;
	}

	public CheckMateMessage(String message, Move move) {
		super(message);
		this.move = move;
	}

	public CheckMateMessage() {
		super();
	}

	@Override
	public void haveEffect() {
		Platform.runLater(()->{
			NetworkManager.getInstance().getChessboard().rootMove(move);
			NetworkManager.getInstance().getController().updateChessboardView();
			NetworkManager.getInstance().getController().snackbarMessage(message);
			NetworkManager.getInstance().getController().endMatch();
		});
	}

	@Override
	public void serverAction(GameServer game, ServerPlayer player) {
		game.interruptAll();
	}
}
