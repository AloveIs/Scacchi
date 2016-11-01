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

	public Pawn(PieceColor p) {
		super(p);
		this.type = PieceType.PAWN;
		this.yetMoved = false;
	}

	/*
	public Pawn (PieceColor p, Coordinate position) throws Exception {
		super(p, position);
		this.type = PieceType.PAWN;
	}
	*/

	@Override
	public ArrayList<Coordinate> accessiblePositions(Chessboard chessboard, Coordinate position){

		ArrayList <Coordinate> possible = new ArrayList<>(4);
		Coordinate cursor  = position.clone();
		Piece piece = null;


		int vIncrement = 0;
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
			System.out.println("non puoi muoveri in veriticale di "+ vIncrement + " o di" + (vIncrement*2));
		}
		//need to reset the cursor because of the exception
		
		//see if it can be possible to eat an opponent's piece
		cursor = position;

		try {
			cursor.increase(1,vIncrement);
			piece = chessboard.getPiece(cursor);
			if (ownedByOpponent(piece)) {
				possible.add(cursor.clone());
			}
		} catch (CoordinateExceededException e){
			//e.printStackTrace();
			System.out.println("Non pui andare a dx e in verticale di " +vIncrement);
		}

		cursor = position;
		try {
			cursor.increase(-1, vIncrement);
			piece = chessboard.getPiece(cursor);
			if (ownedByOpponent(piece)) {
				possible.add(cursor.clone());
			}
		}catch (CoordinateExceededException e){
			//e.printStackTrace();
			System.out.println("Non pui andare a sx e in verticale di " +vIncrement);
		}
		//TODO: scegliere se ritornare una lista vuoto o addirittura null
		// preferirei una lista vuota
		return possible;
	}

}
