package sample.view;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.beans.binding.Bindings;
import sample.client.SessionManager;
import sample.model.ActionType;
import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.MoveController;
import sample.model.messages.Message;
import sample.model.pieces.Piece;
import sample.model.pieces.PieceColor;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GameViewController implements Initializable {

	@FXML
	SplitPane root;

	@FXML
	GridPane board;

	@FXML
	StackPane stackPane;

	@FXML
	AnchorPane paneContainer;

	@FXML
	Label whiteUsername;

	@FXML
	Label blackUsername;

	@FXML
	Group groupBoard;

	@FXML
	JFXSnackbar notifyer;

	@FXML
	Button button;



	private Scene scene;


	private	ArrayList<Coordinate> highlighted = new ArrayList<>();

	//todo: shall i use array instead of arrylists? afterall they're all 64 long

	private	Piece[] currentChessboard = new Piece[64];

	private ArrayList<Pane> squares = new ArrayList<>();

	/**List of all the ImageView into the game board, ordered by index */
	private ArrayList<ImageView> imageViewSquares = new ArrayList<>();
	private Chessboard chessboard = SessionManager.getInstance().getChessboard();
	private PieceColor turn;
	private MoveController moveController = SessionManager.getInstance().getMoveController();

	public void setScene(Scene scene){
		this.scene = scene;
		board.prefWidthProperty().bind(Bindings.min(scene.widthProperty(),scene.heightProperty()).multiply(0.8));
		board.prefHeightProperty().bind(Bindings.min(scene.widthProperty(),scene.heightProperty()).multiply(0.8));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//todo: do this in scene builder
		/*board.setPrefWidth(600);
		board.setPrefHeight(600);*/

		setupSquares();
		setupChessboard(chessboard);
		updateTurn();

		// tell the snackbar where to appear
		notifyer.registerSnackbarContainer(paneContainer);
	}


	private void squarePressedHandler(int index){

		Piece p;
		p = chessboard.getPiece(index);

		if (highlighted.isEmpty()){
			if (p == null || p.getSide() != turn){
				return;
			}

			highlighted.add(0,p.getPosition());
			highlighted.addAll(p.accessiblePositions());
			if (highlighted.size() == 1){
				removeHighlight();
				return;
			}
			highlight();

		}else if (highlighted.contains(new Coordinate(index))){

			Coordinate pointed = new Coordinate(index);

			if (pointed.equals(highlighted.get(0))){
			}else{
				Message msg;
				System.out.println("Muovi " + chessboard.getPiece(highlighted.get(0)) + "in" + pointed);
				msg = moveController.move(highlighted.get(0), pointed);
				updateTurn();
				updateChessboardView();
				if (msg.getType() == ActionType.SPECIAL){
					endMatch();
				}
				msg.triggerNote(notifyer);
			}
			removeHighlight();
		}else{
			removeHighlight();
			squarePressedHandler(index);
		}
	}

	private void endMatch() {
		final JFXDialog jfxDialog;
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Label("Ha vinto " + turn.toString() + "!"));
		layout.setBody(new Label("Cosa vui fare adesso?"));
		JFXButton goBackHome = new JFXButton("Menù");
		goBackHome.getStyleClass().add("popupButton");
		JFXButton restart = new JFXButton("Nuova partita");
		restart.getStyleClass().add("popupButton");
		HBox buttonContainer = new HBox(goBackHome , restart);
		buttonContainer.setSpacing(20);
		layout.setActions(buttonContainer);
		jfxDialog = new JFXDialog(stackPane,layout, JFXDialog.DialogTransition.CENTER);
		jfxDialog.show();
		restart.setOnAction(event -> {
			highlighted.clear();
			moveController = new MoveController();
			this.chessboard = moveController.getChessboard();
			SessionManager.getInstance().setChessboard(chessboard);
			updateChessboardView();
			System.gc();
			jfxDialog.close();
		});
		goBackHome.setOnAction(event ->	{
			//todo: implement all the stuff needed to go back home
			System.exit(0);
		});

	}

	private void updateTurn() {
		if(turn == null){
			turn  = moveController.getTurn();
			if (turn == PieceColor.WHITE){
				whiteUsername.getStyleClass().add("onTurn");
			}else{
				blackUsername.getStyleClass().add("onTurn");
			}
		}else if (turn == moveController.getTurn()){
			//already up to date
		}else{
			if (turn == PieceColor.WHITE){
				whiteUsername.getStyleClass().remove("onTurn");
			}else{
				blackUsername.getStyleClass().remove("onTurn");
			}
			turn = null;
			updateTurn();
		}
	}

	/**Update the view of the chessboard when it is changed in the model*/
	private void updateChessboardView() {

		Piece p;		//used this as a cursor

		for (int i = 0; i < 64; i++) {
			p = chessboard.getPiece(i);

			if (currentChessboard[i] == null && p == null) {
				continue;
			} else if (currentChessboard[i] != null && currentChessboard[i].equals(p)) {
				continue;
			} else {
				imageViewSquares.get(i).setImage(ImagePicker.get(p));
				currentChessboard[i] = p;
			}
		}
	}

	/** Add highlighting to the reachable cells	 */
	private void highlight() {
		for (Coordinate c : highlighted){
			int index = c.getAsIndex();
			squares.get(index).getStyleClass().add("highlighted");
		}
	}
	/** Remove highlighting from the previous reachable cells	 */
	private void removeHighlight() {

		for (Coordinate c : highlighted){
			int index = c.getAsIndex();

			squares.get(index).getStyleClass().remove("highlighted");
		}
		highlighted.clear();
	}

	/** Show for the first time the chessboard
	 * @param chessboard the chessboard to represent int the GUI
	 */
	public void setupChessboard(Chessboard chessboard) {

		for	(Node p : squares){
			Pane pane = (Pane) p;
			ImageView img = new ImageView();
			img.fitWidthProperty().bind(pane.widthProperty());
			img.fitHeightProperty().bind(pane.heightProperty());
			pane.getChildren().add(img);
			imageViewSquares.add(img);
		}

		// populate for the first time the current chessboard list
		Piece p;

		for (int i = 0 ; i < 64; i++){
			//add al the pieces to the list
			p = chessboard.getPiece(i);
			currentChessboard[i] = p;
			//setup the image
			imageViewSquares.get(i).setImage(ImagePicker.get(p));
		}
	}


	/**Prepare all the data structures used int this Controller	 */
	private void setupSquares(){

		for (Node i : board.getChildren()){
			if (i.getClass() == Pane.class){
				squares.add((Pane) i);
				i.setOnMouseClicked(event -> squarePressedHandler(squares.indexOf(event.getSource())));
			}
		}
	}
}