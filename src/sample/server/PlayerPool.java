package sample.server;

import sample.model.Player;
import sample.model.pieces.PieceColor;

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

		System.out.println("White: " + whites.size() + "  #  Black: " + blacks.size());
		for (ServerPlayer q : whites){
			System.out.println(q);
		}
		for (ServerPlayer q : blacks){
			System.out.println(q);
		}
		if (whites.isEmpty() || blacks.isEmpty()){
			System.out.println("Inserito il giocatore "+ p + " ma non ci sono partite disponibili");
		}else{
			System.out.println("faccio partire la partita tra " + whites.get(0) + " e " + blacks.get(0));

			//TODO: FAR PARTIRE UN  DI GIOCO
			new GameServer(whites.get(0), blacks.get(0));
			whites.get(0).send("ciao");
			blacks.get(0).send("ciao");
			whites.remove(0);
			blacks.remove(0);
		}
	}

	private void clean(){

		while(whites.remove(null));
		while(blacks.remove(null));
	}

	@Override
	synchronized public String toString() {
		return "Whites : " + whites.size() + "Blacks : " + blacks.size();
	}
}
