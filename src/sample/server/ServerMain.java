package sample.server;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import static sample.ANSIColorCodes.*;

/** Main class for the server
 *  This class is responsable for the initialization of the server and the creation of
 *  its component. Also from this class is possible to monitor
 * Created by Pietro on 03/12/2016.
 */
public class ServerMain {



	private static final int MAX_ATTEMPTS = 6;

	private static final char VERBOSE_SERVER_LISTENER = '1';
	private static final char SHOW_GAME_LIST = '2';

	private static Date date = new Date();


	synchronized public static void log(String arg){
		System.out.println(ANSI_GREEN + "[" + date +"]\t# " + ANSI_RESET + arg );
	}
	synchronized public static void sucLog(String arg){
		System.out.println(ANSI_GREEN + "[" + date +"]\t# " + arg + ANSI_RESET );
	}
	synchronized public static void errLog(String arg){
		System.out.println(ANSI_GREEN + "[" + date +"]\t# " + ANSI_RED + arg + ANSI_RESET);
	}


	public static void main(String[] args) {

		int i = 0;
		ServerListener serverListener = null;
		Scanner keyboard = new Scanner(System.in);

		log("Server started");
		log("Creating the PlayerPool...");
		PlayerPool pPool = PlayerPool.getInstance();
		sucLog("PlayerPool Created at : " + pPool);

		log("Creating the ServerListner...");

		do {
			try {
				serverListener = new ServerListener();
				break;
			} catch (IOException e) {
				i++;
				errLog("Failed in creating server listener [attempt : " + i + "]" + e.getMessage());
			}
		} while (i < MAX_ATTEMPTS);

		if (i == MAX_ATTEMPTS) {
			errLog("Reached max number of attepts...");
			errLog("Shutting down");
			System.exit(0);
		}

		System.out.println(Thread.currentThread().toString() + "# CREATED LISTENER THREAD #");


		//main controller board for the server

		char answ;

		do {

			System.out.println(VERBOSE_SERVER_LISTENER +") " + (ServerListener.verbose ? "Turn off" : "") + "Verbose Server Listner [now " + (ServerListener.verbose ? ANSI_YELLOW : ANSI_RED) + ServerListener.verbose + ANSI_RESET + "]");
			System.out.println(SHOW_GAME_LIST + ") Show list of active games, and chose one");
			System.out.print("\n\t> Type 'q' to quit\n\n\t>");

			answ = keyboard.next().charAt(0);
			answ = Character.toLowerCase(answ);

			switch(answ){

				case VERBOSE_SERVER_LISTENER :
					ServerListener.verbose = ServerListener.verbose != true;
					break;
				case SHOW_GAME_LIST :

					break;
				default:
					System.out.println("Inserisci un nuovo carattere valido");
			}

		} while (answ != 'q');

	}

}
