package sample.model.pieces;

import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.Move;

import java.util.List;

/**
 * Created by Pietro on 25/10/2016.
 */
public class Queen extends Piece {

	@Override
	public boolean canGo(Chessboard chessboard, Move move) {
		return false;
	}

	@Override
	public List<Coordinate> accessiblePositions(Chessboard chessboard, Coordinate finalcoo) {
		return null;
	}

	public Queen(PieceColor p, Coordinate position) throws Exception {
		super(p, position);
		this.type = PieceType.QUEEN;
	}
}
