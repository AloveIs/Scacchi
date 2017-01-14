package sample.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sample.model.messages.*;
import sample.model.Player;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static sample.server.ServerMain.errLog;
import static sample.server.ServerMain.sucLog;


public class LoginManager extends Thread{

	private Socket clientSocket;
	private static PlayerPool pPool = null;

	Gson messageHandler;

	public LoginManager (Socket clientSocket){
		System.out.println("creating new login manager");
		this.clientSocket = clientSocket;
		messageHandler = new GsonBuilder().registerTypeAdapter(Message.class, new JSONCodecManager<Message>()).create();

		if (pPool == null){
			pPool = PlayerPool.getInstance();
		}
		this.setDaemon(true);
		this.start();
	}

	@Override
	public void run() {

		BufferedInputStream in;
		BufferedOutputStream out;
		String read;

		try {
			out = new BufferedOutputStream(clientSocket.getOutputStream());
			in = new BufferedInputStream(clientSocket.getInputStream());


			PrintWriter outputWriter = new PrintWriter(out);
			Scanner inputReader = new Scanner(in);
			sucLog("Creo gli stream");
			//todo: qui lo stronzo mi lancia un'eccezzione se il client si disconnette
			read = inputReader.nextLine();
			sucLog("Recived :" + read);
			while(true) {
				Message msg = (Message) messageHandler.fromJson(read, Message.class);
				if (msg instanceof LoginMessage) {
					LoginMessage loginMsg = (LoginMessage) msg;
					PlayerPool.getInstance().add(new ServerPlayer(loginMsg.getPlayer(), clientSocket, inputReader , outputWriter));
//					sucLog("Send : " + messageHandler.toJson(new ValidLoginMessage(pPool.toString()), Message.class));
					break;
				} else {
/*					outputWriter.print(messageHandler.toJson(new InvalidLoginMessage("Some parameters are wrong!"), Message.class));
					outputWriter.flush();
*/
				}
			}
		} catch (Exception e){
			errLog("Error during the login handshake terminating : " + clientSocket);
			e.printStackTrace();
		}
	}
}
