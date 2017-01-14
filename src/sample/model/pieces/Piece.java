package sample.model.pieces;


import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.Move;
import sample.model.exception.CoordinateExceededException;

import java.io.Serializable;
import java.util.ArrayList;

/** This is the superclass representing
 * the generic chess piece. From this one all the other
 * types of pieces will be derived.
 * See the classes
 * <b>{@link sample.model.pieces.Pawn}</b>,
 * <b>{@link sample.model.pieces.King}</b>,
 * <b>{@link sample.model.pieces.Knight}</b>,
 * <b>{@link sample.model.pieces.Queen}</b>,
 * <b>{@link sample.model.pieces.Bishop}</b>,
 * <b>{@link sample.model.pieces.Rook}</b>,
 * @author Pietro Alovisi
 * @since 25-10-2016
 */
public abstract class Piece implements Serializable{

	protected PieceType type;
	protected PieceColor color;
	protected Coordinate position;
	protected Chessboard chessboard;
	protected boolean yetMoved;

	/*TODO: come impedire a un giocatore di fare mosse che comportano scacco? prima io farei
	 la mossa e poi verificherei sulla scacchiera se è scacco, in modo da poi fare
	 un UNDO mandando un messaggio di ILLEGALMOVE al giocatore*/

	//TODO:controllare che non siano nulli

	/**
	 *
	 * @param color the color of the piece, therefore defining the team the piece is on
	 * @param position the position in which the piece is
	 * @param board
	 */
	public Piece(PieceColor color, Coordinate position, Chessboard board) {
		this.color = color;
		this.position = position;
		this.chessboard = board;
		this.yetMoved = false;
	}


	//TODO; commentare questa classe, finendo anche questo metodo
	/**
	 *
	 * @return returns all possibile position accessible by the piece
	 */
	abstract public ArrayList<Coordinate> accessiblePositions();

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public void setPosition(int horizontal, int vertical) throws CoordinateExceededException {
		this.position = new Coordinate(horizontal, vertical);
	}

	public PieceColor getSide(){
		return this.color;
	}

	public PieceType getType() {
		return type;
	}

	public void setYetMoved(){
		this.yetMoved = true;
	}

	public Coordinate getPosition(){
		return position.clone();
	}

	/**Method used for determing which move are legal or not
	 * @param finalDestination the finald destination of the move that the player wants to move
	 * @return retuns true if the move is legal, false otherwise
	 */
	public boolean canGo(Coordinate finalDestination){
		ArrayList<Coordinate> list = accessiblePositions();

		if (list.contains(finalDestination)){
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
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.color + " " + this.type + " in " + position.toString();
	}

}
