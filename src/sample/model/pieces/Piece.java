package sample.model.pieces;


import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.Move;
import java.util.ArrayList;

/** This is the superclass representing
 * the generic chess piece. From this one all the other
 * types of pieces will be derived.
 * See the classes
 * <b>{@link sample.model.pieces.Pawn}</b>,
 * <b>{@link sample.model}</b>,
 * <b>{@link sample.model}</b>,
 * <b>{@link sample.model}</b>,
 * @author Pietro Alovisi
 * @since 25-10-2016
 */
public abstract class Piece {

	protected PieceType type;
	protected PieceColor color;
	protected Coordinate position;
	protected Chessboard chessboard;

		/*TODO: come impedire a un giocatore di fare mosse che comportano scacco? prima io farei
	 la mossa e poi verificherei sulla scacchiera se è scacco, in modo da poi fare
	 un UNDO mandando un messaggio di ILLEGALMOVE al giocatore*/
	//TODO: this constructor is utterly useless
	/*
	public Piece(PieceColor p,Coordinate position ) throws Exception{

		this.color = p;
		this.position = position;
	}
	*/

	public Piece(PieceColor p) {
		this.color = p;
	}


	//TODO; commentare questa classe, finendo anche questo metodo
	/**
	 *
	 * @param chessboard
	 * @param position
	 * @return
	 */
	abstract public ArrayList<Coordinate> accessiblePositions(Chessboard chessboard, Coordinate position);



	public PieceColor getSide(){
		return this.color;
	}

	public PieceType getType() {
		return type;
	}

	/**Method used for determing which move are legal or not
	 * @param chessboard the chessboard on wich we are playing
	 * @param move the move we are tryng to make
	 * @return retuns true if the move is legal, false otherwise
	 */
	public boolean canGo(Chessboard chessboard, Move move){
		ArrayList<Coordinate>list = accessiblePositions(chessboard, move.getOrigin());

		if (list.contains(move.getDestination())){
			return  true;
		}else{
			return false;
		}
	}


	/**Compares the team between the calling object and the parameter on
	 * which they are on.
	 * @param rival the other Piece Object
	 * @return returns true of the two pieces are from the same side, false otherwise
	 */
	protected boolean ownedByOpponent(Piece rival){
		if(rival == null){
			return false;
		}
		if (this.color == rival.getSide()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return this.color + " " + this.type;
	}

}
