package sample.model;

import com.sun.org.apache.xpath.internal.SourceTree;
import sample.model.exception.CoordinateExceededException;
import sample.model.pieces.Piece;
import sample.model.pieces.PieceColor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**Class managing the moves made by a player
 * To work this class is referenced to a chessboard
 * and all the moves are tested if legal and to determine if
 * is check or checkmate or other situations take place
 *
 * Created by Pietro on 03/12/2016.
 */
public class MoveController {

	Chessboard chessboard;

	/** Create a controller with unknown chessboard
	 * In this case a new object Chessboard is created
	 * and it can be changed afterwards with {@link MoveController#setChessboard}
	 */
	public MoveController() {

		this.chessboard = new Chessboard();
	}

	public MoveController(Chessboard chessboard) {
		this.chessboard = chessboard;
	}

	public void move (Move move){
		move(move.getOrigin() , move.getDestination());
	}

	public void move (Coordinate origin, Coordinate destination){
		try {
			move(origin.getRow(), origin.getColumn(), destination.getRow(), destination.getColumn());
		} catch (Exception e) {
			//this catch should never be reached
			e.printStackTrace();
		}
	}

	public void move (int originRow, char originCol, int destRow, char destCol ) throws Exception{
		move(originRow, (int)(originCol -'a'), destRow, (int)(destCol -'a'));
	}

	public void move (int originRow, int originCol, int destRow, int destCol ) throws Exception {

		Move move = new Move(new Coordinate(originRow-1,originCol), new Coordinate(destRow-1, destCol) );
		System.out.println("Provo a fare la mossa : " + move);

		Piece originPiece = chessboard.getPiece(move.getOrigin());
		Piece temPiece = null;
		//se la casella d'inizio è vuota, non fare nulla, mandare un messaggio indietro

		if (originPiece == null){
			//--> MSG1
			System.err.println("Mossa non consentita, pezzo non presente");
			//TODO: lanciare eccezzione: "non è una mossa"
			return;
		}

		//il pezzo c'è, provo a fare la mossa, testerò poi se è valida nel senso di scacco

		if (originPiece.canGo(move.getDestination())){

			temPiece = chessboard.getPiece(move.getDestination());
			//make the move
			chessboard.placePiece(move.getDestination(),originPiece);
			chessboard.placePiece(move.getOrigin(), null);

			//check if is check
			//the color of the player must be a parameter
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

				System.err.println("La mossa mette in scacco il tuo re");
				//TODO: send an error message saying : "scacco, mossa non valida"

			}else{

				//System.out.println("[" + move.toString() + "]" + originPiece.toString() + "La mossa va bene, la faccio.");
				System.err.print("CONSENTITA : ");
				originPiece.setYetMoved();
				//controllo se ho fatto scacco matto , cioè se ho vinto

				if (chessboard.hasMadeCheck(originPiece.getSide())){

					System.err.println("Ho compiuto scacco");

					if (chessboard.hasMadeCheckmate(originPiece.getSide())){

						System.err.println("Scacco matto, partita finita, ha vinto : " + originPiece.getSide());

						//TODO: ho vinto, devo mandare un messaggio al gioco
					}else{

						System.err.println("hai compiuto solo scacco");
					}

					//TODO:return the correct message, send : "ok" la mossa va bene , c'è uno scacco ma non matto

				}else{
					System.err.println("La mossa è consentita, la faccio");
					//TODO:return the correct message, send : "ok" la mossa va bene nessuno scacco
				}

			}

		}else{
			//-->MSG2
			//System.out.println("[" + move.toString() + "]" + "La mossa non è consentita dal pezzo" + originPiece + ".");
			System.err.println("La mossa non è consentita dal pezzo");
			// /TODO: mandare messaggio : "non puoi andare qua"
		}
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


}
