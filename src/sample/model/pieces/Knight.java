package sample.model.pieces;

import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pietro on 25/10/2016.
 */
public class Knight extends Piece{

	@Override
	public ArrayList<Coordinate> accessiblePositions(Chessboard chessboard, Coordinate position) {
		return null;
	}

	public Knight(PieceColor p) throws Exception {
		super(p);
		type = PieceType.KNIGHT;
	}

}
