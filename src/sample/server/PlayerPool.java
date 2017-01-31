package sample.server;

import sample.model.pieces.PieceColor;
import static sample.server.ServerMain.*;
import java.util.ArrayList;

/** Class for managing the player connected but still not playing
 * Created by Pietro on 07/12/2016.
 */


public class PlayerPool {

	public volatile static boolean verbose = false;
	private static PlayerPool playerPool = null;

	private volatile ArrayList<ServerPlayer> whites;
	private volatile ArrayList<ServerPlayer> blacks;

	private PlayerPool(){

		whites = new ArrayList<>();
		blacks = new ArrayList<>();
	}

	public static PlayerPool getInstance() {
		if(playerPool == null) {
			playerPool = new PlayerPool();
		}
		return playerPool;
	}

	synchronized void add(ServerPlayer p){

		if (p.getSide() == PieceColor.WHITE){
			whites.add(p);
		}else{
			blacks.add(p);
		}

		clean();

		if (whites.isEmpty() || blacks.isEmpty()){
			if (verbose)
				sucLog("New Player connected " + p + " but no avaliable games");
		}else{
			if (verbose){
				sucLog("New Player connected " + p);
				log("Trying to start a game between " + whites.get(0) + " " + blacks.get(0));
			}

			if (whites.get(0).sendHeartbeat() && blacks.get(0).sendHeartbeat()){
				new GameServer(whites.get(0), blacks.get(0));
				if (verbose)
					sucLog("Sucessfully started a game[" +whites.get(0) + " " + blacks.get(0) + "]");
				whites.remove(0);
				blacks.remove(0);
			}else{
				if (verbose)
					errLog("Game did not start, one of the player disconnected");
				if (!whites.get(0).sendHeartbeat())
					whites.remove(0);
				if (!blacks.get(0).sendHeartbeat())
					blacks.remove(0);
			}
		}
		clean();
	}

	private void clean(){

		while(whites.remove(null));
		while(blacks.remove(null));
	}

	@Override
	synchronized public String toString() {
		return "Whites : " + whites.size() + "Blacks : " + blacks.size();
	}

	public static void pprint() {
		System.out.println(PlayerPool.getInstance().toString());
		PlayerPool.getInstance().printQueue();
	}

	private void printQueue() {
		for (ServerPlayer p : whites)
			System.out.println("\t" + p);
		for (ServerPlayer p : blacks)
			System.out.println("\t" + p);
	}

	public void remove(ServerPlayer player) {
		if (player.getSide() == PieceColor.WHITE)
			whites.remove(player);
		else
			blacks.remove(player);
	}
}
