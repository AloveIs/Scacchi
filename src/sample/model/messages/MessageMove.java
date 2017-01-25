package sample.model.messages;

import javafx.application.Platform;
import sample.client.NetworkManager;
import sample.model.Move;
import sample.model.pieces.PieceColor;
import sample.server.GameServer;
import sample.server.ServerPlayer;


public class MessageMove extends Message {

	private Move move;
	private PieceColor newTurn;

	public MessageMove(ActionType type, String message, Move move, PieceColor nextTurn) {
		super(type, message);
		this.move = move;
		this.newTurn = nextTurn;
	}

	public MessageMove(ActionType type, Move move,PieceColor nextTurn) {
		super(type);
		this.move = move;
		this.newTurn = nextTurn;
	}

	public MessageMove(String message, Move move,PieceColor nextTurn) {
		super(message);
		type = ActionType.VALID;
		this.move = move;
		this.newTurn = nextTurn;
	}

	public MessageMove(Move move,PieceColor nextTurn) {
		this.message = null;
		this.type = ActionType.VALID;
		this.move = move;
		this.newTurn = nextTurn;
	}

	public MessageMove(Move move) {
		this.message = null;
		this.type = ActionType.VALID;
		this.move = move;
		this.newTurn = null;
	}

	@Override
	public void haveEffect() {
		NetworkManager.getInstance().getChessboard().rootMove(move);
		NetworkManager.getInstance().turnProperty().set(newTurn);
		Platform.runLater(()->{
			NetworkManager.getInstance().getController().updateChessboardView();
			if (message == null)
				NetworkManager.getInstance().getController().snackbarMessage("Tocca al " + newTurn.toString());
			else
				NetworkManager.getInstance().getController().snackbarMessage(message + "Tocca al " + newTurn.toString());

		});
	}

	@Override
	public void serverAction(GameServer game, ServerPlayer player) {
		game.move(this.move, player);

	}
}
