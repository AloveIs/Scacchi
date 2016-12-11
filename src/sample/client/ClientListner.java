package sample.client;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import sample.model.Chessboard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;

/**
 * Created by Pietro on 03/12/2016.
 */
public class ClientListner extends Thread {

	ObjectInputStream in;

	public ClientListner(ObjectInputStream in){
		System.out.println(Thread.currentThread().toString() + "sto creando il client listner");
		this.in = in;
		this.start();
	}

	@Override
	public void run() {
		Chessboard c;
		System.out.println("Created client listner");
		while(true){

			try {
				c = (Chessboard) in.readObject();
				System.out.println("recieved msg");
				c.printChessboard();
			}catch (SocketException e){
				System.err.println("Il server si è disconnesso");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
