package sample.client;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import sample.model.Chessboard;
import sample.model.messages.Message;
import sample.model.messages.NewGameMessage;

import java.io.*;
import java.net.SocketException;
import java.util.Scanner;

/** Class used to listen to the socket
 * Created by Pietro on 03/12/2016.
 */
public class ClientListener extends Thread {

	BufferedInputStream in;

	public ClientListener(BufferedInputStream in){
		System.out.println(Thread.currentThread().toString() + "sto creando il client listner");
		this.in = in;
		this.setDaemon(true);
		this.start();
	}

	@Override
	public void run() {

		Scanner input = new Scanner(in);
		String msg;
		do {
			try {
				msg = input.nextLine();
				System.out.println(msg);
				Message message = NetworkManager.getInstance().getGson().fromJson(msg, Message.class);
				message.haveEffect();
			}catch (Exception e){continue;}

			//todo: decode message

		}while (true);
	}
}
