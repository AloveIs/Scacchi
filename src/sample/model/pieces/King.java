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
public class King extends Piece{

	@Override

	private boolean yetMoved;

	/**{@inheritDoc}
	 */
	public King(PieceColor color, Coordinate position, Chessboard board) {
		super(color, position, board);
		this.type = PieceType.KING;
		this.yetMoved = false;
	}

	/**{@inheritDoc}
	 */
	@Override
	public ArrayList<Coordinate> accessiblePositions(){

		ArrayList <Coordinate> possibleCoordinate = new ArrayList<>(4);
		Coordinate cursor  = this.getPosition();
		Piece piece = null;


		for ( int i = -1 ; i < 2; i++) {
			for ( int j = -1 ; j < 2; j++) {
				//TODO : portare questo schema anche negli altri pezzi
				cursor = this.setCoordinate(this.position);
				try{
					piece = chessboard.getPiece(cursor.increase(i,j));
					if (piece == null || this.ownedByOpponent(piece)) {
						possibleCoordinate.add(cursor);
					}
				}catch(CoordinateExceededException e) {}
			}
		}

		return possibleCoordinate;
	}

}