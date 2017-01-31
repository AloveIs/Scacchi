package sample.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sample.model.Move;
import sample.model.MoveController;
import sample.model.messages.*;

import static sample.server.ServerMain.sucLog;


/** Class to represent a game instance into the server
 * Created by Pietro on 03/12/2016.
 */
public class GameServer {

	public static boolean verbose = false;
	private volatile static int id_counter = 0;

	private ServerPlayer white;
	private ServerPlayer black;
	private MoveController controller;
	private Gson gson;
	private int id;

	GameServer(ServerPlayer playerWhite , ServerPlayer playerBlack){
		white = playerWhite;
		white.setGame(this);
		black = playerBlack;
		black.setGame(this);
		gson = new GsonBuilder().registerTypeAdapter(Message.class, new JSONCodecManager<Message>()).create();
		controller = new MoveController();
		id = id_counter;
		toAll(new NewGameMessage(white.getPlayerInfo(), black.getPlayerInfo()));
		//output some if created
		if (verbose)
			sucLog("[GAME" + id + "]Created game between" + white + black);
	}

	void action(ServerPlayer player, String message){

		gson.fromJson(message, Message.class).serverAction(this, player);
		/*
		if ((controller.getTurn() == PieceColor.WHITE && player == white) ||
				(controller.getTurn() == PieceColor.BLACK && player == black)	){
			//poi rispondi
		}else{
			//todo: fix this
			player.send("Non è il tuo turno!");
		}
		*/
	}

	public void move(Move move, ServerPlayer player){
		Message msg = controller.move(move);

		if (msg.getType() == ActionType.VALID) {
			toAll(msg);
		}else if ((msg.getType() == ActionType.SPECIAL)) {
			toAll(msg);
			msg.serverAction(this,player);
		}else{
			player.send(gson.toJson(msg, Message.class));
		}
	}

	public void toAll(Message msg){
		white.send(gson.toJson(msg, Message.class));
		black.send(gson.toJson(msg, Message.class));
	}

	public void toOpponent(ServerPlayer myPlayer, Message msg){
		if (myPlayer == white){
			black.send(gson.toJson(msg, Message.class));
		}else{
			white.send(gson.toJson(msg, Message.class));
		}
	}

	public void giveUp(ServerPlayer player) {
		System.out.println(player);
		ServerPlayer p = (player == white) ? black : white;
		p.send(gson.toJson(new GiveUpMessage(), Message.class));
		interruptAll();
	}

	public void interruptAll() {
		white.interrupt();
		black.interrupt();
	}

	public void closeAll() {
		white.closePlayer();
		black.closePlayer();
		if (verbose){
			sucLog("[GAME" + id + "]Succesfully ended");
		}
	}
}
