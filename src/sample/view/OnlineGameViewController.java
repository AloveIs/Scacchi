package sample.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import jdk.management.resource.internal.inst.NetRMHooks;
import sample.client.NetworkManager;
import sample.client.SessionManager;
import sample.model.*;
import sample.model.messages.Message;
import sample.model.messages.MessageMove;
import sample.model.pieces.Piece;
import sample.model.pieces.PieceColor;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class OnlineGameViewController implements Initializable {

	@FXML
	SplitPane root;

	@FXML
	BorderPane gameBorderPane;

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

	@FXML
	JFXButton abortGame;

	@FXML
	JFXButton tieGame;

	private Scene scene;
	private PieceColor side;

	private	ArrayList<Coordinate> highlighted = new ArrayList<>();

	//todo: shall i use array instead of arrylists? afterall they're all 64 long

	private	Piece[] currentChessboard = new Piece[64];

	private ArrayList<Pane> squares = new ArrayList<>();

	/**List of all the ImageView into the game board, ordered by index */
	private ArrayList<ImageView> imageViewSquares = new ArrayList<>();
	private Chessboard chessboard = NetworkManager.getInstance().getChessboard();
	private MoveController moveController = SessionManager.getInstance().getMoveController();

	public void setScene(Scene scene){
		this.scene = scene;
		board.prefWidthProperty().bind(Bindings.min(scene.widthProperty(),scene.heightProperty()).multiply(0.8));
		board.prefHeightProperty().bind(Bindings.min(scene.widthProperty(),scene.heightProperty()).multiply(0.8));
	}

	public void setUsernames(String whiteUser, String blackUser){
		whiteUsername.setText(whiteUser);
		blackUsername.setText(blackUser);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//todo: do this in scene builder
		board.setPrefWidth(600);
		board.setPrefHeight(600);

		NetworkManager.getInstance().turnProperty().addListener((observable, oldValue, newValue) -> {
			moveController.setTurn(newValue);
			Platform.runLater(()->{
				updateTurn(newValue);
			});
		});

		setupSquares();
		setupChessboard(chessboard);

		// tell the snackbar where to appear
		notifyer.registerSnackbarContainer(paneContainer);
	}

	public void setPlayers(){
		Player p = NetworkManager.getInstance().getPlayer();
		if (p.getSide() == PieceColor.WHITE){
			whiteUsername.setText(p.getName());
			blackUsername.setText(NetworkManager.getInstance().getOpponent().getName());
		}else{
			blackUsername.setText(p.getName());
			whiteUsername.setText(NetworkManager.getInstance().getOpponent().getName());
		}
	}


	private void squarePressedHandler(int index){

		Piece p;
		p = chessboard.getPiece(index);

		if (highlighted.isEmpty()){
			if (p == null || p.getSide() != side)
				return;

			highlighted.add(0,p.getPosition());
			highlighted.addAll(p.accessiblePositions());
			if (highlighted.size() == 1){
				removeHighlight();
				return;
			}
			highlight();

		}else if (highlighted.contains(new Coordinate(index))){

			Coordinate pointed = new Coordinate(index);

			System.out.println("il turno è : " + NetworkManager.getInstance().getTurn());
			System.out.println("My side è : " + side);

			if (pointed.equals(highlighted.get(0))){
			}else if (NetworkManager.getInstance().getTurn() == side){
				Message msg = null;
				//todo: send message whit the move
				try {
					NetworkManager.getInstance().send(new MessageMove(new Move(highlighted.get(0), pointed)));
					System.out.println("invio mossa");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				snackbarTurnMessage();
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
		//TODO: inserire qui il nome del vincitore
		layout.setHeading(new Label("Ha vinto " + "STRING TO INSERT" + "!"));
		JFXButton goBackHome = new JFXButton("Menù");
		layout.setBody(new Label("Cosa vui fare adesso?"));
		goBackHome.getStyleClass().add("popupButton");
		JFXButton restart = new JFXButton("Nuova partita");
		restart.getStyleClass().add("popupButton");
		HBox buttonContainer = new HBox(goBackHome , restart);
		buttonContainer.setSpacing(20);
		layout.setActions(buttonContainer);
		jfxDialog = new JFXDialog(stackPane,layout, JFXDialog.DialogTransition.CENTER);
		jfxDialog.show();
		restart.setOnAction(event -> {
			// restore all the stuff to begin a new game
			restart();

			//once everything is done close the box
			jfxDialog.close();
			// call garbage collector
			System.gc();
		});
		goBackHome.setOnAction(event ->	{
			//todo: implement all the stuff needed to go back home
			jfxDialog.close();
			SessionManager.getInstance().loadStartScreen();
		});

	}

	@FXML
	void tie(ActionEvent event) {
		final JFXDialog jfxDialog;
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Label("Pareggio!"));
		JFXButton goBackHome = new JFXButton("Menù");
		layout.setBody(new Label("Cosa vui fare adesso?"));
		goBackHome.getStyleClass().add("popupButton");
		JFXButton restart = new JFXButton("Nuova partita");
		restart.getStyleClass().add("popupButton");
		HBox buttonContainer = new HBox(goBackHome , restart);
		buttonContainer.setSpacing(20);
		layout.setActions(buttonContainer);
		jfxDialog = new JFXDialog(stackPane,layout, JFXDialog.DialogTransition.CENTER);
		jfxDialog.show();
		restart.setOnAction(e -> {
			// restore all the stuff to begin a new game
			restart();
			//once everything is done close the box
			jfxDialog.close();
			// call garbage collector
			System.gc();
		});
		goBackHome.setOnAction(e ->	{
			//todo: implement all the stuff needed to go back home
			jfxDialog.close();
			SessionManager.getInstance().loadStartScreen();
		});
	}

	@FXML
	void exit(ActionEvent event) {
		SessionManager.getInstance().loadStartScreen();
	}

	@FXML
	void reset(ActionEvent event) {
		restart();
	}

	private void restart(){
		removeHighlight();
		highlighted.clear();
		moveController = new MoveController();
		this.chessboard = moveController.getChessboard();
		SessionManager.getInstance().setChessboard(chessboard);
		//updateTurn();
		updateChessboardView();
	}

	private void updateTurn(PieceColor turn) {
		if (turn == PieceColor.WHITE){
			gameBorderPane.getStyleClass().remove("blackTurn");
			gameBorderPane.getStyleClass().add("whiteTurn");

		}else if (turn == PieceColor.BLACK){
			gameBorderPane.getStyleClass().remove("blackTurn");
			gameBorderPane.getStyleClass().add("blackTurn");
		}else {
			//IN THIS CASE IS NULL, DUNNO WHAT TO DO
		}
	}

	/**Update the view of the chessboard when it is changed in the model*/
	public void updateChessboardView() {
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

	public void snackbarMessage(String message){
		notifyer.fireEvent(new JFXSnackbar.SnackbarEvent(message));
	}


	public void snackbarTurnMessage(){
		//todo: do it better and complete it
		notifyer.fireEvent(new JFXSnackbar.SnackbarEvent("Non è il tuo turno"));
	}

	public void setSide(PieceColor side) {
		this.side = side;
	}
}