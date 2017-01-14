package sample.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sample.model.ActionType;
import sample.model.Move;
import sample.model.MoveController;
import sample.model.messages.JSONCodecManager;
import sample.model.messages.Message;
import sample.model.messages.MessageMove;
import sample.model.messages.NewGameMessage;
import sample.model.pieces.PieceColor;

import java.io.PrintWriter;


/** Class to represent a game instance into the server
 * Created by Pietro on 03/12/2016.
 */
public class GameServer {

	ServerPlayer white;
	ServerPlayer black;
	MoveController controller;
	Gson gson;

	GameServer(ServerPlayer playerWhite , ServerPlayer playerBlack){
		white = playerWhite;
		white.setGame(this);
		black = playerBlack;
		black.setGame(this);
		gson = new GsonBuilder().registerTypeAdapter(Message.class, new JSONCodecManager<Message>()).create();
		controller = new MoveController();
		toAll(new NewGameMessage(white.getPlayerInfo(), black.getPlayerInfo()));
	}

	void action(ServerPlayer player, String message){

		if ((controller.getTurn() == PieceColor.WHITE && player == white) ||
				(controller.getTurn() == PieceColor.BLACK && player == black)	){

			gson.fromJson(message, Message.class).serverAction(this, player);

			//poi rispondi
		}else{
			//todo: fix this
			player.send("Non è il tuo turno!");
		}
	}
	public void move(Move move, ServerPlayer player){
		Message msg = controller.move(move);

		if (msg.getType() == ActionType.VALID) {
			System.out.println("Mossa valida");
			try	{
				toAll(msg);
			}catch (Exception e){
				System.err.println("Errore è qui");
			}
		}else {

			try	{
				player.send(gson.toJson(msg, Message.class));
			}catch (Exception e){
				System.err.println("Altro errore");
			}

		}
	}

	private void toAll(Message msg){
		white.send(gson.toJson(msg, Message.class));
		black.send(gson.toJson(msg, Message.class));
	}
}
