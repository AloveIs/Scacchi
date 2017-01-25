package sample.model.messages;

import javafx.application.Platform;
import sample.client.NetworkManager;
import sample.client.SessionManager;
import sample.model.Player;
import sample.model.pieces.PieceColor;


public class NewGameMessage extends Message {

	private Player white;
	private Player black;
	private PieceColor turn;


	public NewGameMessage(String message, Player white, Player black) {
		super(ActionType.SPECIAL, message);
		this.white = white;
		this.black = black;
		turn = PieceColor.WHITE;
	}

	public NewGameMessage(Player white, Player black) {
		super(ActionType.SPECIAL);
		this.white = white;
		this.black = black;
		turn = PieceColor.WHITE;
	}

	public NewGameMessage(Player white, Player black, PieceColor firstTurn) {
		super(ActionType.SPECIAL);
		this.white = white;
		this.black = black;
		turn = firstTurn;
	}

	@Override
	public void haveEffect(){
		Platform.runLater(()->{
			NetworkManager.getInstance().hasGameProperty().set(true);
			SessionManager.getInstance().setScene(NetworkManager.getInstance().getScene());
			NetworkManager.getInstance().setOpponent(NetworkManager.getInstance().getPlayer().getSide() == PieceColor.WHITE ? black : white);
			NetworkManager.getInstance().getController().setPlayers();
			NetworkManager.getInstance().turnProperty().set(turn);
			Platform.runLater(() -> NetworkManager.getInstance().getController().snackbarMessage("Inizia " + turn.toString()));
		});
	}
}
