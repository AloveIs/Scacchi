package sample.model;

import sample.model.exception.CoordinateExceededException;
import sample.model.pieces.*;

import java.util.ArrayList;
import java.util.List;

/**This class models the chessboard used in the game. It is seen as a grid 8 by 8-
 * The indexing of this table is from 0 to 7 in either the vertical and the horizontal
 * direction. These index starts from the lower left corner.
 * <style>
 *     #reptable,.reptd{
 *         border: 3px solid;
 *         border-collapse: collapse;
 *     }
 * </style>
 * <table id="reptable">
 *     <tr>
 *         <td class="reptd">(3,0)</td>
 *         <td class="reptd">(3,1)</td>
 *         <td class="reptd">(3,2)</td>
 *         <td class="reptd">(3,3)</td>
 *     </tr>
 *     <tr>
 *         <td class="reptd">(2,0)</td>
 *         <td class="reptd">(2,1)</td>
 *         <td class="reptd">(2,2)</td>
 *         <td class="reptd">(2,3)</td>
 *     </tr>
 *     <tr>
 *         <td class="reptd">(1,0)</td>
 *         <td class="reptd">(1,1)</td>
 *         <td class="reptd">(1,2)</td>
 *         <td class="reptd">(1,3)</td>
 *     </tr>
 *     <tr>
 *         <td class="reptd">(0,0)</td>
 *         <td class="reptd">(0,1)</td>
 *         <td class="reptd">(0,2)</td>
 *         <td class="reptd">(0,3)</td>
 *     </tr>
 * </table>
 *
 * Created by Pietro Alovisi on 22/10/2016.
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
			placePiece(new Rook(PieceColor.WHITE, new Coordinate(1,4) , this));
			placePiece(new Pawn(PieceColor.BLACK, new Coordinate(5,4) , this));
			placePiece(new Pawn(PieceColor.WHITE, new Coordinate(1,2), this));
			placePiece(new Bishop(PieceColor.BLACK, new Coordinate(3,2), this));
			placePiece(new Queen(PieceColor.BLACK, new Coordinate(3,6), this));
			//placePiece(new Pawn(PieceColor.BLACK, new Coordinate(1,1), this));
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

	/** Method for locating the king of a particular team.
	 * This method is usefull if you want to locate a king piece
	 * of a team, either black or white.
	 * @param color the color of the king we are looking for
	 * @return returns the coordinate of the board in which the king is
	 */
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
	//TODO: implementare lo yetMoved per i pezzi
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
