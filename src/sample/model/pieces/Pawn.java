package sample.model.pieces;

/**
 * Created by Pietro on 25/10/2016.
 */
public class Pawn extends Piece{

	private boolean yetMoved;

	public Pawn (PieceColor p, Coordinate position) throws Exception {
		super(p, position);
		this.type = PieceType.PAWN;
	}

	@override
	public List <Coordinate> canMove(Chessboard chessboard, Coordinate position){

		List <Coordinate> possible = new ArrayList(4);
		Coordinate cursor;
		Piece piece = null;
		//TODO: gestire le coordinate out of bounds, lo faccio con le eccezione
		cursor = position.increaseVertical(1);
		piece = chessboard.getPiece(cursor);

		//verifico i possibili movimenti in verticale

		if (piece == null) {
			possible.add(cursor);

			if (!yetMoved){
				cursor = cursor.increaseVertical(1);
				piece = chessboard.getPiece(cursor);
				if( piece == null) {
					possible.add(cursor);
				}
			}	
		}
		
		//need to reset the cursor because of the exception
		
		//see if it can be possible to eat an opponent's piece
		cursor = position;
		cursor = position.increase(1,1);
		piece = chessboard.getPiece();
		if (ownedByOpponent(piece)) {
			possible.add(cursor);
		}

		cursor = position;
		cursor = position.increase(-1,1);
		piece = chessboard.getPiece();
		if (ownedByOpponent(piece)) {
			possible.add(cursor);
		}

		//TODO: scegliere se ritornare una lista vuoto o addirittura null
		// preferirei una lista vuota
		return possible;
	}


	@override
	public boolean canMove(Chessboard chessboard, Move move){

		

	}

}
