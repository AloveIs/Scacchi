package sample.view;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import sample.client.NetworkManager;
import sample.client.SessionManager;
import sample.model.*;
import sample.model.messages.*;
import sample.model.pieces.Piece;
import sample.model.pieces.PieceColor;
import sample.view.popups.GiveUpPopup;
import sample.view.popups.OpponentGaveUpPopup;
import sample.view.popups.TieAcceptedPopup;

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
	public StackPane stackPane;

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
	JFXButton abortGame;

	@FXML
	JFXTextField submitMessageForm;

	@FXML
	ScrollPane chatContainer;

	@FXML
	JFXButton tieGame;

	private Scene scene;
	private PieceColor side;
	private VBox messageList = new VBox();

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

		chatContainer.setContent(messageList);
		submitMessageForm.setOnKeyPressed(event -> {
			if (event.getCode() != KeyCode.ENTER)
				return;
			String str = submitMessageForm.getText();
			NetworkManager.getInstance().send(new ChatMessage(str));
			writeChatMessage(str);
			submitMessageForm.clear();
		});

		setupSquares();
		setupChessboard(chessboard);

		// tell the snackbar where to appear
		notifyer.registerSnackbarContainer(paneContainer);
		messageList.setAlignment(Pos.BOTTOM_LEFT);
	}

	private void writeChatMessage(String msg) {
		messageList.getChildren().add(new Label(msg));

	}

	public void setPlayers(){
		Player p = NetworkManager.getInstance().getPlayer();
		System.out.println("my player is : " + p);
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

	public void endMatch() {
		final JFXDialog jfxDialog;
		JFXDialogLayout layout = new JFXDialogLayout();
		NetworkManager.getInstance().closeConnection();
		//TODO: chiudere le socket
		layout.setHeading(new Label("Ha vinto " + (NetworkManager.getInstance().getTurn() == PieceColor.BLACK ? blackUsername.getText() : whiteUsername.getText()) + "!"));
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
			//todo: rientrare nella lobby e tutto il resto
			System.exit(57);
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
		NetworkManager.getInstance().setMoveController(moveController);
		updateChessboardView();
		messageList.getChildren().clear();
		submitMessageForm.clear();
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

	@FXML
	public void giveUp(ActionEvent actionEvent) {
		NetworkManager.getInstance().send(new GiveUpMessage());
		new GiveUpPopup(stackPane);
		NetworkManager.getInstance().closeConnection();
		restart();
	}

	@FXML
	void tie(ActionEvent event) {
		tieGame.getStyleClass().remove("tieProposed");
		snackbarMessage("Hai proposto un pareggio");
		NetworkManager.getInstance().send(new TieProposalMessage());
	}

	public void tieAccepted(){
		new TieAcceptedPopup(stackPane);
		snackbarMessage("Pareggio!");
		NetworkManager.getInstance().closeConnection();
		restart();
		tieGame.getStyleClass().remove("tieProposed");
	}

	public void tieRefused(){
		snackbarMessage("Il pareggio è stato rifiutato!");
	}

	public void opponentGaveUp() {
		new OpponentGaveUpPopup(stackPane);
		NetworkManager.getInstance().closeConnection();
		restart();
	}

	public void chatMessage(Message msg) {
		Label lbl = new Label(msg.getMessage());
		lbl.setStyle("-fx-text-fill: #0d9fff");
		messageList.getChildren().add(lbl);
	}
}