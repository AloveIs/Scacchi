package sample.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import sample.model.Chessboard;
import sample.model.MoveController;
import sample.model.Player;
import sample.model.messages.JSONCodecManager;
import sample.model.messages.LoginMessage;
import sample.model.messages.Message;
import sample.model.pieces.PieceColor;
import sample.view.OnlineGameViewController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/** Class to manage all the network stuff
 * Created by Pietro on 09/01/2017.
 */
public class NetworkManager {

	private static final int DEFAULT_PORT = 5757;
	private static final String SERVER_ADD = "127.0.0.1";

	private static NetworkManager instance = null;

	public static Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new JSONCodecManager<Message>()).create();

	private Socket socket;
	private PrintWriter output;
	private Player player;
	private Player opponent;
	private SimpleBooleanProperty updatedView;
	private SimpleBooleanProperty hasGame;
	private MoveController moveController;
	private SimpleObjectProperty<PieceColor> turn;
	//object for the view of the game
	private Scene scene;
	private OnlineGameViewController controller;

	private NetworkManager(){
		updatedView = new SimpleBooleanProperty(true);
		hasGame = new SimpleBooleanProperty(false);

		moveController = new MoveController();
		turn = new SimpleObjectProperty<>(null);
	}

	public static NetworkManager getInstance(){
		if (instance == null)
			instance = new NetworkManager();

		return instance;
	}

	public Gson getGson() {
		return gson;
	}

	public void setPlayer(String name, PieceColor side) {
		player = new Player(name, side);
		controller.setSide(side);
		controller.updateChessboardView();

		System.out.println(player);
	}

	public boolean isUpdatedView() {
		return updatedView.get();
	}

	public SimpleBooleanProperty updatedViewProperty() {
		return updatedView;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean connect(){
		try {
			socket = new Socket(SERVER_ADD,DEFAULT_PORT);
			new ClientListener(new BufferedInputStream(socket.getInputStream()));
			output = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
			send(new LoginMessage(player));
			return true;
		} catch (IOException e) {
			System.err.println("Errore durante la connessione");
			return false;
		}

	}

	public void send (Message message){
		System.out.println("sending : " + gson.toJson(message, Message.class));
		output.println(gson.toJson(message, Message.class));
		output.flush();
	}

	public synchronized void recieveMessage(Message msg){
		msg.haveEffect();
	}

	public boolean isHasGame() {
		return hasGame.get();
	}

	public SimpleBooleanProperty hasGameProperty() {
		return hasGame;
	}

	public Scene getScene() {
		return scene;
	}

	public OnlineGameViewController getController() {
		return controller;
	}

	private void resetChessboard(){
		moveController = new MoveController();
	}

	public void setOpponent(Player opponent){
		this.opponent = opponent;
	}


	/**Preload all the stuff necessary for the game
	*/
	public void loadScene(){

		if (scene == null)
			Platform.runLater(()->{
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/chessboardOnline.fxml"));
					Parent root = loader.load();
					scene = new Scene(root);
					controller = loader.getController();
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
			}
		});
	}

	public Player getOpponent() {
		return opponent;
	}

	public Chessboard getChessboard() {
		return moveController.getChessboard();
	}


	public PieceColor getTurn() {
		return turn.get();
	}

	public SimpleObjectProperty<PieceColor> turnProperty() {
		return turn;
	}

	public void closeConnection(){
		//todo: vedere perchè lancia questa eccezione
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.hasGameProperty().set(false);
		opponent = null;
		resetChessboard();
		hasGameProperty().set(false);
	}

	public void setMoveController(MoveController moveController) {
		this.moveController = moveController;
	}
}
