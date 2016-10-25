package sample.model;

import sample.model.pieces.Piece;

/**
 * Created by Pietro on 22/10/2016.
 */
public class Move{


private Chessboard chessBoard;
private Coordinate startPos;
private Coordinate finalPos;

//TODO: sistemare l'eccezione
public Move (Coordinate startPos , Coordinate finalPos)throws Exception{

		if (startPos == null || finalPos == null){
		throw new Exception();
		}

		this.startPos = startPos;
		this.finalPos = finalPos;
		}

	public boolean islegal(){

		Piece startingPiece = chessBoard.getPiece(startPos);

		startingPiece.canGo(finalPos);
		return true;
	}


}
