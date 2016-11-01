package sample.model.pieces;

import sample.model.Chessboard;
import sample.model.Coordinate;
import sample.model.Move;
import sample.model.exception.CoordinateExceededException;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Pietro on 25/10/2016.
 */
public class Pawn extends Piece{

	private boolean yetMoved;

	public Pawn(PieceColor color, Coordinate position, Chessboard board) {
		super(color, position, board);
		this.type = PieceType.PAWN;
		this.yetMoved = false;
	}

	@Override
	public ArrayList<Coordinate> accessiblePositions(){

		ArrayList <Coordinate> possible = new ArrayList<>(4);
		int vIncrement;
		Coordinate cursor  = this.getPosition();
		Piece piece = null;

		String ss = "\t\t\t";

		if (this.color == PieceColor.WHITE ){
			vIncrement = 1;
		}else {
			vIncrement = -1;
		}

		try {
			cursor.increaseVertical(vIncrement);
			piece = chessboard.getPiece(cursor);
			//verifico i possibili movimenti in verticale

			if (piece == null) {
				possible.add(cursor.clone());

				if (!yetMoved){
					cursor.increaseVertical(vIncrement);
					piece = chessboard.getPiece(cursor);
					if( piece == null) {
						possible.add(cursor.clone());
					}
				}
			}
		} catch (CoordinateExceededException e) {
			//e.printStackTrace();
			System.err.println(ss + position + "non puoi muoveri completamente in verticale");
		}
		//need to reset the cursor because of the exception
		//see if it can be possible to eat an opponent's piece
		cursor = this.getPosition();

		try {
			cursor.increase(vIncrement,1);
			piece = chessboard.getPiece(cursor);
			if (ownedByOpponent(piece)) {
				possible.add(cursor.clone());
			}
		} catch (CoordinateExceededException e){
			//e.printStackTrace();
			System.out.println(ss + cursor + "Non pui andare a dx e in verticale di " +vIncrement);
		}

		cursor = this.getPosition();

		try {
			cursor.increase(vIncrement, -1);
			piece = chessboard.getPiece(cursor);
			if (ownedByOpponent(piece)) {
				possible.add(cursor.clone());
			}
		}catch (CoordinateExceededException e){
			//e.printStackTrace();
			System.out.println(ss + cursor + "Non pui andare a sx e in verticale di " +vIncrement);
		}
		//TODO: scegliere se ritornare una lista vuoto o addirittura null
		// preferirei una lista vuota
		return possible;
	}

}
