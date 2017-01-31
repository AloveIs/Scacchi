package sample.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sample.model.messages.JSONCodecManager;
import sample.model.messages.Message;

import java.io.BufferedInputStream;
import java.util.Scanner;


public class MessageListener extends Thread {

	private BufferedInputStream in;
	private Scanner reader;
	private Gson gson;

	public MessageListener(BufferedInputStream in) {
		this.in = in;
		reader = new Scanner(in);
		this.setName("MessageListener");
		gson = new GsonBuilder().registerTypeAdapter(Message.class, new JSONCodecManager<Message>()).create();
		this.setDaemon(true);
		this.start();
	}

	@Override
	public void run() {

		Message message;
		while (true){
			String msg = reader.nextLine();
			message = gson.fromJson(msg, Message.class);
		}
	}
}
