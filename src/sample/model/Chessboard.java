package sample.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pietro on 22/10/2016.
 */
public class Chessboard {

	private Piece [][] chessboard;	//the board used in the game


	//FIXME: da vedere se sono strettamente necessarie

	private List<Piece> blackList = new ArrayList<>(16);
	private List<Piece> whiteList = new ArrayList<>(16);

	public Chessboard(){

		this.chessboard = new Piece[8][8];

		for (int i = 0; i < 8; i++) {

			//chessboard[1][i] = new Piece(PieceColor.BLACK);
		}

		for (int i = 0; i < 8; i++) {

			chessboard[6][i] = new Piece();
		}

		//inizializzare tutti i pezzi


		//INIZIALIZZAZIONE BIANCHI


		//INIZIALIZZAZIONE NERI



	}

	public Piece getPiece(Coordinate coo){

		return chessboard[coo.getHorizontal()][coo.getVertical()];
	}


}
