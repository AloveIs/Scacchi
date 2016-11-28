package sample.model.pieces;

import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.Move;
import sample.model.exception.CoordinateExceededException;

import java.util.ArrayList;
import java.util.List;

class NotAccessibleException extends Exception{}

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
					possibleCoordinate.add(cursor.clone());
				}catch(Exception e){
					//System.err.println("Errore durante L23 " + hinc	+ " " + vinc);
				}

				try{
					cursor = l3By2(hinc,vinc);
					possibleCoordinate.add(cursor.clone());
				}catch(Exception e){
					//System.err.println("Errore durante L32 " + hinc	+ " " + vinc);
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
	private Coordinate l2By3(int hor, int ver)throws CoordinateExceededException, NotAccessibleException{

		Coordinate fpos = this.getPosition();
		fpos.increase(1*hor, 2*ver);

		Piece p = chessboard.getPiece(fpos);

		//System.err.println("Test in " + fpos);

		if (!(p == null || ownedByOpponent(p))) {
			throw new NotAccessibleException();
		}

		return fpos;
	}


	/** Method for checking if a L-shaped move is legal or not (3 in horizontal, 2 vertical)
	 *
	 * @param hor represents the direction in wich the side long 3 is directed to. +1 to the right, -1 left.
	 * @param ver represents the direction in wich the side long 2 is directed to. +1 for up, -1 down.
	 */
	private Coordinate l3By2(int hor, int ver) throws CoordinateExceededException , NotAccessibleException{

		Coordinate fpos = this.getPosition();
		//System.err.println("from _" + fpos);
		fpos.increase(2*hor, 1*ver);
		Piece p = chessboard.getPiece(fpos);

		//System.err.println("Test in " + fpos + p);

		if (!(p == null || ownedByOpponent(p))) {
			throw new NotAccessibleException();
		}

		return fpos;
	}
}