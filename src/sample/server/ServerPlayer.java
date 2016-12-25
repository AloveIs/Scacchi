package sample.server;

import sample.model.MoveController;
import sample.model.Player;
import sample.model.pieces.PieceColor;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/** Class to handle the player in the server
 * Created by Pietro on 11/12/2016.
 */
public class ServerPlayer {

	private Player playerInfo;
	private Socket playerSocket;
	private PrintWriter output;
	private boolean isPlayerSet;
	private	boolean isPlaying;
	private GameServer game;
	private Scanner input;

	ServerPlayer(Player player, Socket playerSocket, Scanner input, PrintWriter output){
		this.playerInfo = player;
		isPlayerSet = false;
		isPlaying = false;
		this.playerSocket = playerSocket;
		this.output = output;
		this.input = input;
	}

	public synchronized void send(String s){
		try{
			output.println(s);
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
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

	private void setPlayerSet(){
		isPlayerSet = true;
	}

	public boolean isPlayerSet(){
		return isPlayerSet;
	}

	@Override
	public String toString() {
		return playerInfo.toString();
	}
}
