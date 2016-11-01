package sample.model.pieces;


import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.exception.CoordinateExceededException;

import java.util.ArrayList;

/**
 * Created by Pietro on 25/10/2016.
 */
public class Bishop extends Piece{

	/**{@inheritDoc}
	 */
	public Bishop(PieceColor color, Coordinate position, Chessboard board) {
		super(color, position, board);
		this.type = PieceType.BISHOP;
	}

	/**{@inheritDoc}
	 */
	@Override
	public ArrayList<Coordinate> accessiblePositions() {

		ArrayList<Coordinate> possibleCoordinate = new ArrayList<>(7);
		Coordinate cursor;
		cursor = this.getPosition();

		while (true){
			try {
				cursor.increase(1,1);
				Piece p = chessboard.getPiece(cursor.clone());
				if (p == null){
					possibleCoordinate.add(cursor.clone());
				}else if (ownedByOpponent(p)){
					possibleCoordinate.add(cursor.clone());
					break;
				}else{
					break;
				}
			} catch (CoordinateExceededException e) {
				break;
			}
		}
		cursor = this.getPosition();
		while (true){
			try {
				cursor.increase(1,-1);
				Piece p = chessboard.getPiece(cursor.clone());
				if (p == null){
					possibleCoordinate.add(cursor.clone());
				}else if (ownedByOpponent(p)){
					possibleCoordinate.add(cursor.clone());
					break;
				}else{
					break;
				}
			} catch (CoordinateExceededException e) {
				break;
			}
		}
		cursor = this.getPosition();
		while (true){
			try {
				cursor.increase(-1,1);
				Piece p = chessboard.getPiece(cursor.clone());
				if (p == null){
					possibleCoordinate.add(cursor.clone());
				}else if (ownedByOpponent(p)){
					possibleCoordinate.add(cursor.clone());
					break;
				}else{
					break;
				}
			} catch (CoordinateExceededException e) {
				break;
			}
		}
		cursor = this.getPosition();
		while (true){
			try {
				cursor.increase(-1,-1);
				Piece p = chessboard.getPiece(cursor.clone());
				if (p == null){
					possibleCoordinate.add(cursor.clone());
				}else if (ownedByOpponent(p)){
					possibleCoordinate.add(cursor.clone());
					break;
				}else{
					break;
				}
			} catch (CoordinateExceededException e) {
				break;
			}
		}
		return possibleCoordinate;
	}
}
