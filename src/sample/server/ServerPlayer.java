package sample.server;

import sample.model.Player;
import sample.model.pieces.PieceColor;
import sample.model.pieces.PieceType;

import java.net.Socket;

/** Class to handle the player in the server
 * Created by Pietro on 11/12/2016.
 */
public class ServerPlayer {

	private Player playerInfo;
	private Socket playerSocket;

	ServerPlayer(Player player , Socket playerSocket){
		this.playerInfo = player;
		this.playerSocket = playerSocket;
	}

	public Player getPlayerInfo() {
		return playerInfo;
	}

	public Socket getPlayerSocket() {
		return playerSocket;
	}

	public PieceColor getSide(){
		return playerInfo.getSide();
	}

	public boolean isClosed(){
		return playerSocket.isClosed();
	}
}
