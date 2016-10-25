package sample.model;

import sample.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pietro on 22/10/2016.
 */
public class Chessboard {

	/** The chessboard
	 * The first index represents the horizontal dimension.
	 * The second index represents the vertical one.
	 */
	private Piece[][] chessboard;	//the board used in the game


	//FIXME: da vedere se sono strettamente necessarie

	private List<Piece> blackList = new ArrayList<>(16);
	private List<Piece> whiteList = new ArrayList<>(16);

	public Chessboard(){

		this.chessboard = new Piece[8][8];

		for (int i = 0; i < 8; i++) {

			//chessboard[1][i] = new Piece(PieceColor.BLACK);
		}


		//inizializzare tutti i pezzi


		//INIZIALIZZAZIONE BIANCHI

		for (int i = 0; i < 8; i++) {

			chessboard[6][i] = new Piece();
		}

		//INIZIALIZZAZIONE NERI



	}
	public Piece getPiece(int hor, int ver) throws Exception{

		if (hor > 7 || hor < 0 || ver > 7 || ver < 0){
			throw new Exception();
		}

		return chessboard[hor][ver];
	}

	public Piece getPiece(Coordinate coo){

		return chessboard[coo.getHorizontal()][coo.getVertical()];

	}


}
