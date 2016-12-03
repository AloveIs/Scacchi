package sample.server;

import java.io.IOException;

import static sample.ANSIColorCodes.*;

/** Main class for the server
 *  This class is responsable for the initalization of the server and the creation of
 *  its component.
 * Created by Pietro on 03/12/2016.
 */
public class ServerMain {

	private static final int MAX_ATTEMPTS = 6;

	public static void main(String[] args){

		int i = 0;
		ServerListener serverlistner = null;

		System.out.println(ANSI_BG_GREEN + ANSI_RED + "# SERVER STARTED #" + ANSI_RESET + ANSI_BG_RESET);

		System.out.println("Information :");
		System.out.println("\tthread  : " + Thread.currentThread().getId() + " - " + Thread.currentThread().toString());

		//System.out.println(System.currentTimeMillis());
		System.out.println(ANSI_BG_GREEN + ANSI_RED + "# CREATING LISTENER THREAD #" + ANSI_RESET + ANSI_BG_RESET);

		do {
			try {
				new ServerListener();
				//serverlistner.start();
				break;
			} catch (IOException e) {
				i++;
				System.err.println("Attept : " + i + "|  Error trying opening the socket");
				e.printStackTrace();
			}
		} while (i < MAX_ATTEMPTS);

		if (i == MAX_ATTEMPTS)
			System.exit(1);

		System.out.println(Thread.currentThread().toString() + "# CREATED LISTENER THREAD #");

	}

}
