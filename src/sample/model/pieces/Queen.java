package sample.model.pieces;

import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.Move;

import java.util.ArrayList;


/**
 * Created by Pietro on 25/10/2016.
 */
public class Queen extends Piece {

	@Override
	public boolean canGo(Chessboard chessboard, Move move) {
		return false;
	}

	@Override
	public ArrayList<Coordinate> accessiblePositions(Chessboard chessboard, Coordinate position) {
		return null;
	}

	public Queen(PieceColor p) throws Exception {
		super(p);
		this.type = PieceType.QUEEN;
	}
}
