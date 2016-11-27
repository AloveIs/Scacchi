package sample.model.pieces;

import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.Move;

import java.util.ArrayList;
import java.util.List;

private class NotAccessibleException extends Exception{}

/**Class representig the Knight piece
 * Created by Pietro on 25/10/2016.
 */
public class Knight extends Piece{

	/**{@inheritDoc}
	 */
	public Knight(PieceColor color, Coordinate position, Chessboard board) {
		super(color, position, board);
		this.type = PieceType.KNIGHT;
	}

	/**{@inheritDoc}
	 */
	@Override
	public ArrayList<Coordinate> accessiblePositions(){

		ArrayList <Coordinate> possibleCoordinate = new ArrayList<>(4);
		Coordinate cursor;
		Piece piece = null;


		for (int vinc = -1; vinc < 3 ; vinc+=2 ) {
			for (int hinc =  -1 ; hinc < 3 ; hinc+=2 ) {
				try{
					cursor = l2By3(hinc,vinc);
					possibleCoordinate.add(cursor);
				}catch(Exception e){
					System.err.println("Errore durante L23 " + hinc	+ "" + vinc);
				}

				try{
					cursor = l3By2(hinc,vinc);
					possibleCoordinate.add(cursor);
				}catch(Exception e){
					System.err.println("Errore durante L32 " + hinc	+ "" + vinc);	
				}
			}
		}

		return possibleCoordinate;
	}
	/** Method for checking if a L-shaped move is legal or not (2 in horizontal, 3 vertical)
	 *
	 * @param hor represents the direction in wich the side long 2 is directed to. +1 to the right, -1 left.
	 * @param ver represents the direction in wich the side long 3 is directed to. +1 for up, -1 down.
	 */
	private Coordinate l2By3(int hor, int ver)throws CoordinateExceededException , NotAccessibleException{
		
		Piece p = chessboard.getPiece(getPosition().increment(2*hor, 3*ver));
		Coordinate cursor = getPosition();

		if (p != null || !ownedByOpponent(p)) {
			throw new NotAccessibleException();
		}

		for (i = 0; i < 2 ; i++ ) {
			p = chessboard.getPiece(cursor.incrementHorizontal(hor));
			if (ownedByOpponent(p)) {
				throw new NotAccessibleException();
			}
		}

		for (i = 0; i < 2 ; i++ ) {
			p = chessboard.getPiece(cursor.incrementVertical(ver));
			if (ownedByOpponent(p)) {
				throw new NotAccessibleException();
			}		
		}
		return cursor.incrementVertical(ver); 
	}


	/** Method for checking if a L-shaped move is legal or not (3 in horizontal, 2 vertical)
	 *
	 * @param hor represents the direction in wich the side long 3 is directed to. +1 to the right, -1 left.
	 * @param ver represents the direction in wich the side long 2 is directed to. +1 for up, -1 down.
	 */
	private Coordinate l3By2(int hor, int ver) throws CoordinateExceededException , NotAccessibleException{
		
		Piece p = chessboard.getPiece(getPosition().increment(3*hor, 2*ver));
		Coordinate cursor = getPosition();

		if (p != null || !ownedByOpponent(p)) {
			throw new NotAccessibleException();
		}

		for (i = 0; i < 3 ; i++ ) {
			p = chessboard.getPiece(cursor.incrementHorizontal(hor));
			if (ownedByOpponent(p)) {
				throw new NotAccessibleException();
			}
		}

		for (i = 0; i < 1 ; i++ ) {
			p = chessboard.getPiece(cursor.incrementVertical(ver));
			if (ownedByOpponent(p)) {
				throw new NotAccessibleException();
			}		
		}
		return cursor.incrementVertical(ver); 
	}
}