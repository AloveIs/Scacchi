package sample.model;

import com.sun.org.apache.xpath.internal.SourceTree;
import sample.model.exception.CoordinateExceededException;
import sample.model.messages.Message;
import sample.model.pieces.Piece;
import sample.model.pieces.PieceColor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static sample.model.ActionType.*;
import static sample.model.ActionType.NONVALID;

/**Class managing the moves made by a player
 * To work this class is referenced to a chessboard
 * and all the moves are tested if legal and to determine if
 * is check or checkmate or other situations take place
 *
 * Created by Pietro on 03/12/2016.
 */
public class MoveController {

	private Chessboard chessboard;
	private PieceColor turn;

	/** Create a controller with unknown chessboard
	 * In this case a new object Chessboard is created
	 * and it can be changed afterwards with {@link MoveController#setChessboard}
	 */
	public MoveController() {
		turn = PieceColor.WHITE;
		this.chessboard = new Chessboard();
	}

	/** Create a controller and bind it to a chessbpard
	 * Create a noew MoveController managing the moves on the chessboad passed as an argument
	 * @param chessboard the chessboard you want to control
	 */
	public MoveController(Chessboard chessboard) {
		turn = PieceColor.WHITE;
		this.chessboard = chessboard;
	}


	/**Make the move on the chessboard controlled by the MoveController
	 * @param move the move you want to make
	 */
	public Message move (Move move){
		return move(move.getOrigin() , move.getDestination());
	}

	public Message move (Coordinate origin, Coordinate destination){
		try {
			return move(origin.getRow()+1, origin.getColumn(), destination.getRow()+1, destination.getColumn());
		} catch (Exception e) {
			//this catch should never be reached
			e.printStackTrace();
		}
		return new Message(NONVALID, "Coordinate fuori dalla scacchiera!");
	}

	public Message move (int originRow, char originCol, int destRow, char destCol ) throws Exception{
		return move(originRow, (int)(originCol -'a'), destRow, (int)(destCol -'a'));
	}
	//todo: sistemare il "-1" nelle coordinate, mi fa un po cacare
	public Message move (int originRow, int originCol, int destRow, int destCol ) throws Exception {

		Move move = new Move(new Coordinate(originRow-1,originCol), new Coordinate(destRow-1, destCol) );

		/*##### debugging
		System.out.println("Provo a fare la mossa : " + move);
		chessboard.printChessboard();*/

		Piece originPiece = chessboard.getPiece(move.getOrigin());
		Piece temPiece = null;

		//if the origin cell is empty return a message ...
		if (originPiece == null){
			// the origin cell is empty, no piece to move
			//System.err.println("Mossa non consentita, pezzo non presente");
			//TODO: lanciare eccezzione: "non è una mossa"
			return new Message(NONVALID, "La casella di origine è vuota!");
		}

		//check if the piece is compatible with the turn
		if (originPiece.getSide() != turn){
			//TODO: caso turno sbagliato
			return new Message(NONVALID, "Non è il tuto turno!");
		}

		// ... else see if the move is valid :

		if (originPiece.canGo(move.getDestination())){

			//the move is allowed by the piece

			//make the move, i'll see if it is valid
			temPiece = chessboard.getPiece(move.getDestination());
			chessboard.placePiece(move.getDestination(),originPiece);
			chessboard.placePiece(move.getOrigin(), null);

			//check if the move implies check for the player's king
			ArrayList<Piece> listOpp = (ArrayList<Piece>) chessboard.getOpponentList(originPiece.getSide());
			listOpp.remove(temPiece);

			if (chessboard.isCheck(originPiece.getSide())){

				//if it's check on the moving player then restore everything
				//System.out.println("[" + move.toString() + "]" + "La mossa comporta uno scacco per il tuo re!");

				chessboard.placePiece(move.getOrigin(), originPiece);
				chessboard.placePiece(move.getDestination(),temPiece);
				if (temPiece != null) {
					listOpp.add(temPiece);
				}
				//move non valid, the player's king is under check
				return new Message(NONVALID, "La mossa mette in scacco il tuo re!");
				//TODO: send an error message saying : "scacco, mossa non valida"

			}else{

				//System.out.println("[" + move.toString() + "]" + originPiece.toString() + "La mossa va bene, la faccio.");
				//System.err.print("CONSENTITA ");
				originPiece.setYetMoved();
				//controllo se ho fatto scacco matto , cioè se ho vinto

				if (chessboard.hasMadeCheck(originPiece.getSide())){
					// the move implies check, now see if it is checkmate...

					if (chessboard.hasMadeCheckmate(originPiece.getSide())){
						// ... it is checkmate
						//System.err.println("Scacco matto, partita finita, ha vinto : " + turn);
						return new Message(SPECIAL, "Scacco matto! Ha vinto " + turn.toString());
						//TODO: ho vinto, devo mandare un messaggio al gioco
					}else{
						// ... no checkmate, it's only check
						swapTurn();
						return new Message(VALID, "Scacco al re " + turn.toString() + "!");
						//TODO:return the correct message, send : "ok" la mossa va bene , c'è uno scacco ma non matto
					}

				}else{
					//The move is valid, no check involved, i'll ma it
					swapTurn();
					return new Message(VALID, null);
					//TODO:return the correct message, send : "ok" la mossa va bene nessuno scacco
				}
			}
		}else{
			// ... the move is not meant to be made by this piece

			//System.out.println("[" + move.toString() + "]" + "La mossa non è consentita dal pezzo" + originPiece + ".");
			//System.err.println("La mossa non è consentita dal pezzo");

			return new Message(NONVALID, "La mossa non è consentida dal pezzo!");
			// /TODO: mandare messaggio : "non puoi andare qua"
		}

	}

	private void swapTurn(){
		turn = turn == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
	}

	public PieceColor getTurn() {
		return turn;
	}

	private void removeFromMove(Piece p){


	}

	private void restoreFromMove(){

	}

	public void printState(){
		this.chessboard.printChessboard();
	}

	public void setChessboard(Chessboard chessboard){
		this.chessboard = chessboard;
	}

	/**Return the chessboard controlled by this class
	 *
	 * @return the chessboard onto the moves are tested
	 */
	public Chessboard getChessboard() {
		return chessboard;
	}


	/* main to test the model functioning properly */


	/*
	public static void main(String[] args) {

		Scanner tastiera = new Scanner(System.in);
		int a, c;
		char b, d;

		MoveController controller = new MoveController();
		controller.printState();

		while (true) {


			try {
				System.out.print("Insert init 1: ");
				a = Integer.parseInt(String.valueOf(tastiera.next().charAt(0)));
				System.out.print("Insert init 2: ");
				b = tastiera.next().charAt(0);

				System.out.print("Insert final 1: ");
				c = Integer.parseInt(String.valueOf(tastiera.next().charAt(0)));
				System.out.print("Insert final 2: ");
				d = tastiera.next().charAt(0);
			}catch (Exception e){
				System.out.println("Dati immessi male, rifare");
				try {
					System.in.reset();
				} catch (IOException e1) {
				}
				continue;
			}

			try {
				controller.move(a, b, c, d);
			} catch (Exception e) {
				System.err.println("Errore durante la mossa :" + a +" "+ b +" "+ c +" "+ d);
				e.printStackTrace();
			}

			controller.printState();

		}
	}
	*/
}
