package sample.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.IllegalBlockingModeException;

import static sample.server.ServerMain.*;

/** Class listening for incoming connection
 *  This class is listening to a port for incoming connection. If a connection is detected
 *  then it is passed to the {@link LoginManager}, who will take care of the greeting
 *  with the new user.
 *
 *  This class runs on a daemon thread, so if the main ends also this class does. In the event
 *  of a problem with a socket it just goes on listening.
 *
 * Created by Pietro on 03/12/2016.
 */
public class ServerListener extends Thread{


	/**Count of the recieved connection*/
	public static volatile int count = 0;

	/**Determinate if the class may write to the main server log*/
	public static boolean verbose = false;

	/**The default port for listening for new connections */
	private static final int DEFAULT_PORT = 57575;
	private ServerSocket socket;

	/** Create the ServerListener on a specif port
	 * @param port port different from the default one
	 * @throws IOException if there are problems in the creating of the {@link java.net.ServerSocket}
	 */
	public ServerListener(int port) throws IOException{
		this.socket = new ServerSocket(port);
		this.setDaemon(true);
		this.start();
		sucLog("Successfully created the server listener");
	}

	/** Create the ServerListener on the default port
	 * @throws IOException if there are problems in the creating of the {@link java.net.ServerSocket}
	 */
	public ServerListener() throws IOException{
		this(DEFAULT_PORT);
	}

	@Override
	public void run() {

		while (true){
			try {
				Socket received = socket.accept();

				//show information if someone connects and verbose is true
				if (verbose){
					log(count + "|Received a connection on socket info :" + received);
					log(count + "|Creating the login manager...");
				}
				//creating the loginManger for that socket to handle the
				//user information

				new LoginManager(received);

			} catch (IOException e) {

				errLog(e.getMessage() + "Error during the handling of te newly received socket.");
				//e.printStackTrace();
			}catch (IllegalBlockingModeException | SecurityException e){
				errLog(e.getMessage());
			}
		}
	}

}
