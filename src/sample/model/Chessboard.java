package sample.model;

import com.sun.deploy.util.BlackList;
import sample.model.exception.CoordinateExceededException;
import sample.model.pieces.Pawn;
import sample.model.pieces.Piece;
import sample.model.pieces.PieceColor;
import sample.model.pieces.PieceType;

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

		chessboard[0][0] = new Pawn(PieceColor.WHITE);
	/*
		for (int i = 0; i < 8; i++) {

			//chessboard[1][i] = new Piece(PieceColor.BLACK);
		}


		//inizializzare tutti i pezzi


		//INIZIALIZZAZIONE BIANCHI

		for (int i = 0; i < 8; i++) {

			chessboard[6][i] = null;
		}

		//INIZIALIZZAZIONE NERI

	*/

	}

	public Coordinate locateKing(PieceColor color){

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if (chessboard[i][j].getSide() == color && chessboard[i][j].getType() == PieceType.KING){
					try {
						return new Coordinate(i,j);
					} catch (CoordinateExceededException e) {
						e.printStackTrace();
					}
				}

			}
		}

		//TODO: gestire nel caso in cui non trovi il re (non ho idea di come fare, mi sembra sprecato tirare fuori ulteriori eccezioni)
		return null;
	}

	private boolean isCheck(PieceColor color){

		Coordinate myKing = locateKing(color);
		boolean result = false;

		if (color == PieceColor.WHITE){
			for (Piece p : this.blackList){
				if(p.a)

			}
		}else{
			for (Piece p : this.whiteList){


			}
		}

		return result;
	}


	//TODO: quewsto deve ritornare un messaggio per il giocatore per sapere se l'azione è andata a buon fine, o altrimenti perchè non è andata
	public void move(Move move){

		Piece origin = getPiece(move.getOrigin());

		if (origin == null){
			//TODO: lanciare eccezzione: "non è una mossa"
		}

		if (origin.canGo(this, move)){

			Piece temPice = this.getPiece(move.getDestination());
			//make the move
			placePiece(move.getDestination(),origin);
			placePiece(move.getOrigin(), null);

			//check if is check

			if (isCheck(origin.getSide())){
				//if it's check then restore everythin
				placePiece(move.getOrigin(), origin);
				placePiece(move.getDestination(),temPice);

				//TODO: send an error message saying : "scacco, mossa non valida"

			}else{
				//keep everything as it is
				//TODO:return the correct message, send : "ok
			}


		}else{
			//TODO: mandare messaggio : "non puoi andare qua"
		}
	}



	private boolean placePiece(Coordinate position, Piece piece){

		this.chessboard[position.getHorizontal()][position.getVertical()] = piece;

		return true;

	}


	public Piece getPiece(int hor, int ver) throws CoordinateExceededException{

		if (hor > 7 || hor < 0 || ver > 7 || ver < 0){
			throw new CoordinateExceededException();
		}

		return chessboard[hor][ver];
	}

	public Piece getPiece(Coordinate coo){

		return chessboard[coo.getHorizontal()][coo.getVertical()];

	}


	public void printChessboard() {
		String representation = "";

		Coordinate thisC = null;

		for (int i = 0; i < 8 ; i++) {

			System.out.println("Riga : " + (i+1));
			for (int j = 0; j < 8; j++) {
				if (chessboard[i][j] != null){
					System.out.println("\t> in (" + i + "," + j + ") " + chessboard[i][j].toString());
					try {
						thisC  = new Coordinate(i,j);
					} catch (CoordinateExceededException e) {
						e.printStackTrace();
					}
					for(Coordinate c : chessboard[i][j].accessiblePositions(this, thisC)){
						System.out.println("\t\t> " + c.toString());
					}
				}
			}
		}
	}
}
