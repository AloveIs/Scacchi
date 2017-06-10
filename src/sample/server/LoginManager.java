package sample.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sample.model.messages.JSONCodecManager;
import sample.model.messages.LoginMessage;
import sample.model.messages.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static sample.server.ServerMain.errLog;
import static sample.server.ServerMain.log;
import static sample.server.ServerMain.sucLog;


public class LoginManager extends Thread{

	public static boolean verbose;
	private Socket clientSocket;
	private static PlayerPool pPool = null;

	Gson messageHandler;

	public LoginManager (Socket clientSocket){
		if (verbose)
			log("creating new login manger...");
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
			//todo: qui lo stronzo mi lancia un'eccezzione se il client si disconnette
			read = inputReader.nextLine();
			if (verbose)
				sucLog("New login started" + read);

			while(true) {
				Message msg = (Message) messageHandler.fromJson(read, sample.model.messages.Message.class);
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
		} catch (IOException e){
			if (verbose)
				errLog("Error during the login handshake terminating : " + clientSocket);
			e.printStackTrace();
		}
	}
}
