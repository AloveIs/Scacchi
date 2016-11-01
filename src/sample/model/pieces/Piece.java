package sample.model.pieces;


import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.Move;

import java.util.List;

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
public abstract class Piece {

	protected PieceType type;
	protected PieceColor color;
	protected Coordinate position;


	//TODO: manage exception

	/*
	public Piece(PieceColor p,Coordinate position ) throws Exception{

		this.color = p;
		this.position = position;
	}
	*/


	public Piece(PieceColor p) {
		this.color = p;
	}


	public PieceColor getSide(){
		return this.color;
	}
	//TODO: come impedire a un giocatore di fare mosse che comportano scacco? prima io farei
	// la mossa e poi verificherei sulla scacchiera se è scacco, in modo da poi fare
	//un UNDO mandando un messaggio di ILLEGALMOVE al giocatore
	abstract public boolean	 canGo(Chessboard chessboard, Move move);
	abstract public List<Coordinate> accessiblePositions(Chessboard chessboard, Coordinate finalcoo);

	protected boolean ownedByOpponent(Piece rival){
		if(rival == null){
			return false;
		}
		if (this.color == rival.getSide()) {
			return true;
		}
		return false;
	}

	public static void main(String[] args){

		Piece p = null;
		Piece p1 = null;

	}



}
