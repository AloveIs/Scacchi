package sample.model.pieces;

import sample.model.Coordinate;

/**
 * Created by Pietro on 25/10/2016.
 */
public class Queen extends Piece {

	public Queen(PieceColor p, Coordinate position) throws Exception {
		super(p, position);
		this.type = PieceType.QUEEN;
	}
}
