package sample.client;

import javafx.stage.Stage;
import sample.model.Chessboard;
import sample.model.MoveController;


public class SessionManager{
	private static SessionManager ourInstance = new SessionManager();
	private MoveController moveController;
	private Chessboard chessboard;
	private Stage stage;

	public static SessionManager getInstance() {
		return ourInstance;
	}

	private SessionManager() {
		this.chessboard = new Chessboard();
		this.moveController = new MoveController(chessboard);
		this.stage = null;
	}

	public Chessboard getChessboard() {
		return chessboard;
	}

	public void setChessboard(Chessboard chessboard) {
		this.chessboard = chessboard;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public MoveController getMoveController() {
		return moveController;
	}
}
