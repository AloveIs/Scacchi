package sample.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sample.model.Move;
import sample.model.Player;
import sample.model.messages.JSONCodecManager;
import sample.model.messages.LoginMessage;
import sample.model.messages.Message;
import sample.model.messages.ValidLoginMessage;
import sample.model.pieces.PieceColor;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Pietro on 03/12/2016.
 */
public class Main {

	static private final int DEFAUL_PORT = 5757;
	static private PrintWriter out = null;
	static private Scanner in = null;
	static private Socket  s = null;
	static private Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new JSONCodecManager<Message>()).create();
	static Scanner keyboard = new Scanner(System.in , "UTF-8");

	public static void main(String[] args){


		char answ = '?';
		Move m = null;

		setup();

		do {

			System.out.print("\n\t> Type 'q' to quit\n\n\t>");
			try{
				if (keyboard.hasNextLine())
					System.out.println("frosh ha next line");
				if (keyboard.hasNext()) {
					System.out.println("frosh ha next");

					answ = keyboard.nextLine().charAt(0);
				}
				answ = Character.toLowerCase(answ);
			}catch (Exception e){
				System.err.println(e.getMessage());
				e.printStackTrace();
			}

		} while (answ != 'q');

		try {
			s.close();
		} catch (IOException e) {
			System.err.println("Error closing the socket on the client side: ");
			e.printStackTrace();
		}
/*
		while (true) {
			System.out.print("Insert init 1: ");
			a = keyboard.nextInt();
			System.out.print("Insert init 2: ");
			b = keyboard.next().charAt(0);

			System.out.print("Insert final 1: ");
			c = keyboard.nextInt();
			System.out.print("Insert final 2: ");
			d = keyboard.next().charAt(0);


			try {
				m = new Move(new Coordinate(a,(char) b),new Coordinate(c,(char) d));
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
*/

	}

	static void setup(){

	/*
		int a,c;
		char b,d;
	*/

		try {
			s = new Socket("127.0.0.1", 5757);
			System.out.println("ha fatto la socket");
			out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
			System.out.println("Created the output stream");
			in = new Scanner(s.getInputStream());
			login();

		} catch (IOException e) {

			System.err.println("Il client ha fatto sesso");
			System.exit(2);
			e.printStackTrace();
		}
	}

	static void login() throws IOException {

		String answ;
		Player me = null;

		while (true){

			System.out.println("Inserisci il nome : ");
			String name = keyboard.next();
			System.out.println("Inserisci la fazione (b/n) : ");
			char side = keyboard.next().charAt(0);
			side = Character.toLowerCase(side);
			if (side == 'b'){
				me = new Player(name, PieceColor.WHITE);
			}else if (side == 'n'){
				me = new Player(name, PieceColor.BLACK);
			}else {
				System.err.println("Il client ha fatto sesso");
				s.close();
				System.exit(2);
			}
			out.println(gson.toJson(new LoginMessage(me) , Message.class));
			out.flush();
			answ = in.nextLine();
			System.out.println(answ);
			Message m = gson.fromJson(answ , Message.class);
			if (m instanceof ValidLoginMessage){
				System.out.println("Valid login");

				return;
			}
		}
	}
}
