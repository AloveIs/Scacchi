package sample.model;



public class Game {

	private final Player p1;
	private final Chessboard chessboard;
	private final Player p2;
	private boolean P1turn;

	public Game(Player p1, Player p2){

		this.p1 = p1;
		this.p2 = p2;
		chessboard = new Chessboard();
		P1turn = true;
	}


	private void start(){
		
		
		
	}

}