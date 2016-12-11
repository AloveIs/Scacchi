package sample.server;

import sample.model.Chessboard;
import sample.model.Game;
import sample.model.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Pietro on 03/12/2016.
 */
public class GameServer extends Thread {

	Socket socketclient;
	Chessboard chessboard;

	public GameServer (Socket client){
		this.socketclient = client;
		chessboard = new Chessboard();
		System.out.println("game server created");
		this.start();

	}

	@Override
	public void run() {

		ObjectInputStream in = null;
		ObjectOutputStream out = null;

		try {
			out = new ObjectOutputStream(socketclient.getOutputStream());
			System.out.println("Created outputStream");
			in = new ObjectInputStream(socketclient.getInputStream());
			System.out.println("Created inputStream");

		} catch (IOException e) {
			System.err.println("Error during the creation of in/out stream towards the client");
			this.interrupt();
			e.printStackTrace();
		}

		while(true){

			try {
				System.out.println("Writing the chessboard");
				chessboard.printChessboard();
				out.reset();
				out.writeObject(chessboard);
				out.flush();
			} catch (IOException e) {
				System.err.println("error writing onto the socket");
				Thread.currentThread().stop();
				e.printStackTrace();
			}


			try {
				Move m = (Move) in.readObject();
				//chessboard.move(m);
				System.out.println("recived move : " + m);
			} catch (IOException e) {
				System.err.println("error reading into the socket");
				this.interrupt();
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.err.println("error in casting the object recived, class not found");
				e.printStackTrace();
			}


		}
	}
}
