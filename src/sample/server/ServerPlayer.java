package sample.server;

import sample.model.Player;
import sample.model.pieces.PieceColor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Class to handle the player in the server
 * Created by Pietro on 11/12/2016.
 */
public class ServerPlayer extends Thread{

	private Player playerInfo;
	private Socket playerSocket;
	private volatile PrintWriter output;
	private boolean isPlayerSet;
	private	boolean isPlaying;
	private GameServer game;
	private Scanner input;

	ServerPlayer(Player player, Socket playerSocket, Scanner input, PrintWriter Output){
		this.playerInfo = player;
		isPlayerSet = false;
		isPlaying = false;
		this.playerSocket = playerSocket;
		this.output = Output;
		this.input = input;
		game = null;
		this.setDaemon(true);
		this.start();
	}


	@Override
	public void run() {
		String message;

		while (true){
			try {
				message = input.nextLine();
				game.action(this, message);
			}catch (IllegalStateException e){
				System.err.println("La socket è stata chiusa");
				if (this.isInterrupted()){
					break;
				}else {
					game.giveUp(this);
					break;
				}
			}catch (NoSuchElementException e1){
				System.err.println("La socket è stata chiusa");
				if (this.isInterrupted()){
					break;
				}else {
					game.giveUp(this);
					break;
				}
			}
		}
		System.err.println("End player : " + playerInfo);
	}

	public void send(String message){
		try{
			output.println(message);
			output.flush();
		}catch (Exception e){
			System.err.println("Errore durante il send a " + this);
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

	public PrintWriter getOutput() {
		return output;
	}

	@Override
	public String toString() {
		return playerInfo.toString();
	}

	public void setGame(GameServer game) {
		this.game = game;
	}

	public void closePlayer(){
		try {
			input.close();
			output.close();
			playerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
