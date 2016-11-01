package sample.model.pieces;

import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.Move;

import java.util.List;

/**
 * Created by Pietro on 25/10/2016.
 */
public class King extends Piece {

	private boolean yetMoved;

	public King(PieceColor p, Coordinate position) throws Exception {
		super(p, position);
		this.type = PieceType.KING;
	}

	@Override
	public boolean canGo(Chessboard chessboard, Move move) {
		return false;
	}

	@Override
	public List<Coordinate> accessiblePositions(Chessboard chessboard, Coordinate finalcoo) {
		return null;
	}

	public boolean canMove(Chessboard chessboard, Move move){

		Coordinate currentPos = move.getOrigin();
		Coordinate finalPos = move.getDestination();

		//TODO: implementare le condizioni per non essere in scacco

		return true;
	}

}
