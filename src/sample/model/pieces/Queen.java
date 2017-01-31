package sample.model.pieces;

import sample.model.Chessboard;
import sample.model.Coordinate;

import java.util.ArrayList;


/**
 * Created by Pietro on 25/10/2016.
 */
public class Queen extends Piece{

	/**{@inheritDoc}
	 */
	public Queen(PieceColor color, Coordinate position, Chessboard board) {
		super(color, position, board);
		this.type = PieceType.QUEEN;
	}

	/**{@inheritDoc}
	 */
	@Override
	public ArrayList<Coordinate> accessiblePositions() {
		ArrayList<Coordinate> possibleCoordinate = new ArrayList<>(14);
		possibleCoordinate.addAll((new Rook(this.color, this.position, this.chessboard)).accessiblePositions());
		possibleCoordinate.addAll((new Bishop(this.color, this.position, this.chessboard)).accessiblePositions());

		return possibleCoordinate;
	}


}
