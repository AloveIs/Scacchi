package sample.model.pieces;

import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.exception.CoordinateExceededException;

import java.util.ArrayList;

/**
 * Created by Pietro on 25/10/2016.
 */
public class Rook extends Piece {

	/**{@inheritDoc}
	 */
	public Rook(PieceColor color, Coordinate position, Chessboard board) {
		super(color, position, board);
		this.type = PieceType.ROOK;
	}

	/**{@inheritDoc}
	 */
	@Override
	public ArrayList<Coordinate> accessiblePositions() {

		ArrayList<Coordinate> possibleCoordinate = new ArrayList<>(7);
		Coordinate cursor = this.getPosition();

		while (true){
			try {
				cursor.increaseVertical(1);
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
				cursor.increaseVertical(-1);
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
				cursor.increaseHorizontal(1);
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
				cursor.increaseHorizontal(-1);
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
