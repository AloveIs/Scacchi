package sample.server;

import sample.model.Chessboard;
import sample.model.MoveController;

/** //TODO: FARE LA DOCUMENTAZIONE
 * Created by Pietro on 11/12/2016.
 */
public class GameHandler extends Thread {

	private final ServerPlayer white;
	private final ServerPlayer black;
	private MoveController moveController;
	private boolean turnWhite = true;

	public GameHandler(ServerPlayer white, ServerPlayer black) {
		this.white = white;
		this.black = black;
		this.moveController = new MoveController();
	}


	public void action(){



	}
}
