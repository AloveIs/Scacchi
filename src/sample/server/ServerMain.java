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
	private static final int LOOP = 10;
	private static final char VERBOSE_SERVER_LISTENER = '1';
	private static final char VERBOSE_GAME = '2';
	private static final char VERBOSE_LOGIN_MANAGER = '3';
	private static final char SHOW_PLAYER_POOL = '5';
	private static final char VERBOSE_PLAYER_POOL = '4';
	private static int counter = 0;
	private static Date date = new Date();

	synchronized public static void log(String arg){
		System.out.println(ANSI_GREEN + "[" + date +"]\t# " + ANSI_RESET + arg );
		counter++;
		if (counter >= LOOP)
			printMenu();
	}
	synchronized public static void sucLog(String arg){
		System.out.println(ANSI_GREEN + "[" + date +"]\t# " + arg + ANSI_RESET );
		counter++;
		if (counter >= LOOP)
			printMenu();
	}
	synchronized public static void errLog(String arg){
		System.out.println(ANSI_GREEN + "[" + date +"]\t# " + ANSI_RED + arg + ANSI_RESET);
		counter++;
		if (counter >= LOOP)
			printMenu();
	}

	private static void printMenu() {

		counter = 0;
		System.out.println("");
		System.out.println(VERBOSE_SERVER_LISTENER +") " + (ServerListener.verbose ? "Turn off " : "") + "verbose Server Listner [now " + (ServerListener.verbose ? ANSI_YELLOW : ANSI_RED) + ServerListener.verbose + ANSI_RESET + "]");
		System.out.println(VERBOSE_GAME +") " + (GameServer.verbose ? "Turn off " : "") + "verbose all games [now " + (GameServer.verbose ? ANSI_YELLOW : ANSI_RED) + GameServer.verbose + ANSI_RESET + "]");
		System.out.println(VERBOSE_LOGIN_MANAGER + ") " + (LoginManager.verbose ? "Turn off " : "") + "verbose login manager [now " + (LoginManager.verbose ? ANSI_YELLOW : ANSI_RED) + LoginManager.verbose + ANSI_RESET + "]");
		System.out.println(VERBOSE_PLAYER_POOL + ") " + (PlayerPool.verbose ? "Turn off " : "") + "verbose playerPool [now " + (PlayerPool.verbose ? ANSI_YELLOW : ANSI_RED) + PlayerPool.verbose + ANSI_RESET + "]");
		System.out.println(SHOW_PLAYER_POOL + ") Show player pool status");
		System.out.print("\n\t> Type 'q' to quit\n\n\t>");
	}

	public static void main(String[] args) throws IOException {

		int i = 0;
		ServerListener serverListener = null;
		Scanner keyboard = new Scanner(System.in);

		log("Server started");
		log("Creating the PlayerPool...");
		PlayerPool pPool = PlayerPool.getInstance();
		sucLog("PlayerPool Created at : " + pPool);

		log("Creating the ServerListener...");

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

		sucLog("Successfully created the ServerListener");
		sucLog("End initialization");

		//main controller board for the server

		char answ;

		do {
			printMenu();

			answ = keyboard.next().charAt(0);
			answ = Character.toLowerCase(answ);

			switch(answ){
				case VERBOSE_SERVER_LISTENER :
					ServerListener.verbose = !ServerListener.verbose;
					break;
				case VERBOSE_GAME :
					GameServer.verbose = !GameServer.verbose;
					break;
				case VERBOSE_LOGIN_MANAGER:
					LoginManager.verbose = !LoginManager.verbose;
					break;
				case SHOW_PLAYER_POOL:
					PlayerPool.pprint();
					break;
				case VERBOSE_PLAYER_POOL:
					PlayerPool.verbose = !PlayerPool.verbose;
					break;
				default:
					errLog("Inserire un carattere valido");
			}

		} while (answ != 'q');
	}
}
