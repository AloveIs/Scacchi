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

			Piece p;
			/*
			placePiece(new Rook(PieceColor.WHITE, new Coordinate(1,4) , this));
			placePiece(new Pawn(PieceColor.WHITE, new Coordinate(1,2), this));
			placePiece(new King(PieceColor.WHITE, new Coordinate(1,3) , this));
			placePiece(new Knight(PieceColor.WHITE, new Coordinate(0,0) , this));
			placePiece(new Pawn(PieceColor.BLACK, new Coordinate(2,1) , this));
			placePiece(new Bishop(PieceColor.BLACK, new Coordinate(3,2), this));
			placePiece(new Queen(PieceColor.BLACK, new Coordinate(3,6), this));
			placePiece(new Pawn(PieceColor.BLACK, new Coordinate(1,1), this));

			*/

		//torri bianche
			p = new Rook(PieceColor.WHITE,new Coordinate(0,0),this);
			placePiece(p);
			whiteList.add(p);
			p = new Rook(PieceColor.WHITE,new Coordinate(0,7),this);
			placePiece(p);
			whiteList.add(p);
		//torri nere
			p = new Rook(PieceColor.BLACK,new Coordinate(7,0),this);
			placePiece(p);
			blackList.add(p);
			p = new Rook(PieceColor.BLACK,new Coordinate(7,7),this);
			placePiece(p);
			blackList.add(p);

		//cavalli bianchi
			p = new Knight(PieceColor.WHITE,new Coordinate(0,1),this);
			placePiece(p);
			whiteList.add(p);
			p = new Knight(PieceColor.WHITE,new Coordinate(0,6),this);
			placePiece(p);
			whiteList.add(p);
		//cavalli neri
			p = new Knight(PieceColor.BLACK,new Coordinate(7,1),this);
			placePiece(p);
			blackList.add(p);
			p = new Knight(PieceColor.BLACK,new Coordinate(7,6),this);
			placePiece(p);
			blackList.add(p);

		//alfieri bianchi
			p = new Bishop(PieceColor.WHITE,new Coordinate(0,2),this);
			placePiece(p);
			whiteList.add(p);
			p = new Bishop(PieceColor.WHITE,new Coordinate(0,5),this);
			placePiece(p);
			whiteList.add(p);
		//alfieri neri
			p = new Bishop(PieceColor.BLACK,new Coordinate(7,2),this);
			placePiece(p);
			blackList.add(p);
			p = new Bishop(PieceColor.BLACK,new Coordinate(7,5),this);
			placePiece(p);
			blackList.add(p);

		//re
			p = new King(PieceColor.WHITE, new Coordinate(0,4),this);
			placePiece(p);
			whiteList.add(p);
			p = new King(PieceColor.BLACK, new Coordinate(7,4),this);
			placePiece(p);
			blackList.add(p);
		//regine
			p = new Queen(PieceColor.WHITE, new Coordinate(0,3),this);
			placePiece(p);
			whiteList.add(p);
			p = new Queen(PieceColor.BLACK, new Coordinate(7,3),this);
			placePiece(p);
			blackList.add(p);

		//pedoni bianchi
			for (int i = 0; i < 8; i++) {
				Piece q = new Pawn(PieceColor.WHITE, new Coordinate(1,i), this);
				placePiece(q);
				whiteList.add(q);
			}

		//pedoni neri
			for (int i = 0; i < 8; i++) {
				Piece q = new Pawn(PieceColor.BLACK, new Coordinate(6,i), this);
				placePiece(q);
				blackList.add(q);
			}

		} catch (CoordinateExceededException e) {
			System.err.println("Errore nell'inizializzazione della scacchiera.");
			e.printStackTrace();
		}

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
		if (myKing == null) {
			return false;
		}


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

		return false;
	}

	private boolean inCheckmate(King king){


		return false;
	}


	//TODO: quewsto deve ritornare un messaggio per il giocatore per sapere se l'azione è andata a buon fine, o altrimenti perchè non è andata
	//TODO: implementare lo yetMoved per i pezzi
	//TODO: quando mangia togliere i pezzi dalla lista
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
				System.out.println("[" + move.toString() + "]" + "La mossa comporta uno scacco per il tuo re!");
				placePiece(move.getOrigin(), originPiece);
				placePiece(move.getDestination(),temPiece);

				//TODO: send an error message saying : "scacco, mossa non valida"

			}else{

				System.out.println("[" + move.toString() + "]" + originPiece.toString() + "La mossa va bene, la faccio.");
				originPiece.setYetMoved();
				//keep everything as it is
				//TODO:return the correct message, send : "ok
			}


		}else{
			System.out.println("[" + move.toString() + "]" + "La mossa non è consentita dal pezzo" + originPiece + ".");
			//TODO: mandare messaggio : "non puoi andare qua"
		}
	}

	private void placePiece(Piece piece){
		this.chessboard[piece.getPosition().getRow()][piece.getPosition().getColumn()] = piece;
	}

	private void placePiece(Coordinate position, Piece piece){
		if (piece == null){

			this.chessboard[position.getRow()][position.getColumn()] = null;

			return;
		}
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


	private List<Piece> getOpponentList(PieceColor color){
		if ( color == PieceColor.WHITE) {
			return blackList;
		}else{
			return whiteList;
		}
	}

	/** Prints a list of the active
		The list represents all the pieces and their possible moves
	*/
	public void printChessboardsMoves() {

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

	/**Prints the chessboard status in th comand line interface
	*/
	public void printChessboard() {

		Piece thisP = null;
		// TODO: inserire i veri codici ansi per i colori
		String colorW = "\u001B[32m"; //which is actually green
		String colorB = "\u001B[36m";  //which is actually cyan
		String color;
		String endColor = "\u001B[0m";

		//beginning of the function
		System.out.print("\n     a    b    c    d   e   f    g    h");
		System.out.print("\n   --------------------------------------\n");

		for (int i = 0; i < 8 ; i++) {
			System.out.print(" " + (i+1) + " |");
			for (int j = 0; j < 8; j++) {

				if (chessboard[i][j] != null){
					//non sono sicuto chessboarhe queesto comando funzioni
					thisP = chessboard[i][j];

					if (thisP.getSide() == PieceColor.WHITE) {
						color = colorW;	
					}else{
						color = colorB;
					}

					System.out.print(" "+ color + thisP.getType().getUnicode() + endColor +" |");
				}else{
					System.out.print(" " + "\u001B[37m" + "\u2659" + "\u001B[0m" + " |");
				}
			}
			System.out.print("\n   --------------------------------------\n");
		}
	}

}//endclass
