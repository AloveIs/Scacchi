package sample.model.pieces;


import sample.model.Coordinate;

/** This is the superclass representing
 * the generic chess piece. From this one all the other
 * types of pieces will be derived.
 * See the classes
 * <b>{@link sample.model}</b>,
 * <b>{@link sample.model}</b>,
 * <b>{@link sample.model}</b>,
 * <b>{@link sample.model}</b>,
 * @author Pietro Alovisi
 * @since 25-10-2016
 */
public class Piece {

	protected PieceType type;
	protected PieceColor color;
	protected Coordinate position;


	//TODO: manage exception
	public Piece(PieceColor p,Coordinate position ) throws Exception{

		this.color = p;
		this.position = position;
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
