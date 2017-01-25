package sample.client;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.Chessboard;
import sample.model.MoveController;


public class SessionManager{
	private static SessionManager ourInstance = new SessionManager();
	private MoveController moveController;
	private Chessboard chessboard;
	private Scene startScreen;
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

	public void setScene(Scene scene){
		Platform.runLater(() -> stage.setScene(scene));
	}

	public void setStartScreen(Scene scene){
		this.startScreen = scene;
	}

	public MoveController getMoveController() {
		return moveController;
	}

	public void resetChessboard(){
		chessboard = new Chessboard();
		moveController = new MoveController(chessboard);
	}

	/**  Load the start screen
	 */
	public void loadStartScreen() {
		setScene(startScreen);
	}

	public void setMoveController(MoveController moveController) {
		this.moveController = moveController;
	}
}
