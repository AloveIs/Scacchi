package sample.client;

import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import sample.model.Coordinate;
import sample.model.Move;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Pietro on 03/12/2016.
 */
public class Main {

	static private final int DEFAUL_PORT = 5757;

	public static void main(String[] args){

		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		Move m = null;
		Socket s;


		int a,c;
		char b,d;

		Scanner keyboard = new Scanner(System.in);


		try {
			s = new Socket("127.0.0.1", 5757, true);
			System.out.println("ha fatto la socket");
			out = new ObjectOutputStream(s.getOutputStream());
			System.out.println("Created the output stream");
			InputStream inpS = s.getInputStream();
			ObjectInputStream o = new ObjectInputStream(inpS);
			new ClientListner(o);
			System.out.println("ha fatto il client listner");

			System.out.println("Ho fatto la socket");

		} catch (IOException e) {

			System.err.println("Il client ha fatto sesso");
			System.exit(2);
			e.printStackTrace();
		}

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


	}
}
