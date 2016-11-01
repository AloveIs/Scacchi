package sample.model.pieces;

import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.Move;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pietro on 25/10/2016.
 */
public class King extends Piece {
	/**True if the piece has already made a move,
	 * false otherwise
	 */
	private boolean yetMoved;

	public King(PieceColor p) throws Exception {
		super(p);
		this.type = PieceType.KING;
	}

	@Override
	public boolean canGo(Chessboard chessboard, Move move) {
		return false;
	}

	@Override
	public ArrayList<Coordinate> accessiblePositions(Chessboard chessboard, Coordinate position) {
		return null;
	}

	public boolean canMove(Chessboard chessboard, Move move){

		Coordinate currentPos = move.getOrigin();
		Coordinate finalPos = move.getDestination();

		//TODO: implementare le condizioni per non essere in scacco

		return true;
	}

}
