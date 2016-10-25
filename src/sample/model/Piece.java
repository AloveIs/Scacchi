package sample.model;


/**
 * Created by Pietro on 22/10/2016.
 */
public class Piece {

	private PieceType type;
	private PieceColor color;
	private Coordinate position;

	public Piece(PieceColor p,Coordinate position ){

		this.color = p;
		this.position = position;
	}

	public Piece() {
	}

	public PieceColor getSide(){
		return this.color;
	}

	public boolean canGo(Coordinate finalcoo){
		//TODO: IMPLEMENTARE
		return true;
	}

	public static void main(String[] args){

		Piece p = null;
		Piece p1 = null;
		try {
			p = new Piece( PieceColor.BLACK, new Coordinate(2,2));
			p1 = new Piece(PieceColor.BLACK,new Coordinate(1,1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println((p.getSide() == p1.getSide()));
		System.out.println("on the " + p.getSide().toString() + " side");

	}

}
