package sample.model;

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
	 * The first index represents the row dimension.
	 * The second index represents the column one.
	 */
	private Piece[][] chessboard;	//the board used in the game


	//FIXME: da vedere se sono strettamente necessarie

	private List<Piece> blackList = new ArrayList<>(16);
	private List<Piece> whiteList = new ArrayList<>(16);

	public Chessboard(){

		this.chessboard = new Piece[8][8];

		try {
			placePiece(new Pawn(PieceColor.WHITE, new Coordinate(7,7) , this));
			placePiece(new Pawn(PieceColor.BLACK, new Coordinate(3,0) , this));
			placePiece(new Pawn(PieceColor.BLACK, new Coordinate(1,1), this));
		} catch (CoordinateExceededException e) {
			e.printStackTrace();
		}
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

		if (color == PieceColor.WHITE){
			for (Piece p : this.whiteList){
				if (p.getType() == PieceType.KING){
					return p.getPosition();
				}
			}
		}else{
			for (Piece p : this.blackList){
				if (p.getType() == PieceType.KING){
					return p.getPosition();
				}
			}
		}
		//TODO: gestire nel caso in cui non trovi il re (non ho idea di come fare, mi sembra sprecato tirare fuori ulteriori eccezioni)
		return null;
	}

	private boolean isCheck(PieceColor color){

		Coordinate myKing = locateKing(color);
		//TODO: come in {@link:Chessboard.isCheck()} quando non trova il re è un problema perchè teoricamente il gioco è finito
		if (myKing == null)
			return false;

		boolean result = false;

		if (color == PieceColor.WHITE){
			for (Piece p : this.blackList){
				if (p.canGo(myKing)){
					return true;
				}
			}
		}else{
			for (Piece p : this.whiteList){
				if (p.canGo(myKing)){
					return true;
				}
			}
		}

		return result;
	}


	//TODO: quewsto deve ritornare un messaggio per il giocatore per sapere se l'azione è andata a buon fine, o altrimenti perchè non è andata
	public void move(Move move){

		Piece originPiece = getPiece(move.getOrigin());


		if (originPiece == null){
			//TODO: lanciare eccezzione: "non è una mossa"
		}

		if (originPiece.canGo(move.getDestination())){

			Piece temPiece = this.getPiece(move.getDestination());
			//make the move
			placePiece(move.getDestination(),originPiece);
			placePiece(move.getOrigin(), null);

			//check if is check

			if (isCheck(originPiece.getSide())){
				//if it's check then restore everythin
				placePiece(move.getOrigin(), originPiece);
				placePiece(move.getDestination(),temPiece);

				//TODO: send an error message saying : "scacco, mossa non valida"

			}else{
				//keep everything as it is
				//TODO:return the correct message, send : "ok
			}


		}else{
			//TODO: mandare messaggio : "non puoi andare qua"
		}
	}

	private void placePiece(Piece piece){
		this.chessboard[piece.getPosition().getRow()][piece.getPosition().getColumn()] = piece;
	}

	private void placePiece(Coordinate position, Piece piece){
		piece.setPosition(position);
		placePiece(piece);
	}

	private boolean placePiece(int row, int column, Piece piece) throws CoordinateExceededException {

		piece.setPosition(row,column);
		placePiece(piece);

		return true;
	}


	public Piece getPiece(int row, int col) throws CoordinateExceededException{

		if (row > 7 || row < 0 || col > 7 || col < 0){
			throw new CoordinateExceededException();
		}

		return chessboard[row][col];
	}

	public Piece getPiece(Coordinate coo){
		return chessboard[coo.getRow()][coo.getColumn()];
	}


	public void printChessboard() {
		String representation = "";

		Coordinate thisC = null;

		for (int i = 0; i < 8 ; i++) {

			System.out.println("Riga : " + i);
			for (int j = 0; j < 8; j++) {
				if (chessboard[i][j] != null){
					System.out.println("\t> in (" + i + "," + j + ") " + chessboard[i][j].toString());
					try {
						thisC  = new Coordinate(i,j);
					} catch (CoordinateExceededException e) {
						e.printStackTrace();
					}
					for(Coordinate c : chessboard[i][j].accessiblePositions()){
						System.out.println("\t\t> " + c.toString());
					}
				}
			}
		}
	}
}
