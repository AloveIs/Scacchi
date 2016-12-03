package sample.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static sample.ANSIColorCodes.*;

/**
 * Created by Pietro on 03/12/2016.
 */
public class ServerListener extends Thread{


	private static final int DEFAULT_PORT = 5757;
	private ServerSocket socket;

	//constructors

	public ServerListener() throws IOException{
		this.socket = new ServerSocket(DEFAULT_PORT);
		this.start();
	}

	public ServerListener(int port) throws IOException{
		this.socket = new ServerSocket(port);
		this.start();
	}

	//main method for the thread



	@Override
	public void run() {
		System.out.println(Thread.currentThread().toString() + "# STARTED LISTENING SERVICE #\n\tThreadID : " + currentThread().getId());

		while (true){

			try {
				Socket recived = socket.accept();
				System.out.println("recived a connection");
				//TODO: NOW I'LL SEND THIS TO THE GAME CLASS, BUT I SHOULD INSTEAD PUT IT IN A QUEUE OR LOBBY
				new GameServer(recived);
			} catch (IOException e) {
				System.err.println("Error during the accepting of a connection");
				e.printStackTrace();
			}

		}

	}
}
