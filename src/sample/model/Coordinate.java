package sample.model;

import sample.model.exception.CoordinateExceededException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/** Class representic the coordinate in the chessboard.
 * Created by Pietro on 22/10/2016.
 */

public class Coordinate implements Serializable{

private int row;
private int column;

	/**The values that the constructor accept are integers
	 * int the range from 0 to 8, as the width and the height
	 * of the chessboard
	 * @param row the orizontal component of the coordinate
	 * @param column the column component of the coordinate
	 * @throws CoordinateExceededException if at least one of the parameters exceed its domain
	 */
	public Coordinate (int row, int column)throws CoordinateExceededException{
		setCoordinate(row,column);
	}

	public Coordinate (int row, char col)throws CoordinateExceededException{
		setCoordinate(row-1, ((int) col - 'a'));
	}

	public void setCoordinate(int row, int column)throws CoordinateExceededException{
		if (row > 7 || row < 0 || column > 7 || column < 0 ) {
			throw new CoordinateExceededException();
		}
		this.row = row;
		this.column = column;
	}

	public void setCoordinate(Coordinate coordinate){
		this.row = coordinate.getRow();
		this.column = coordinate.getColumn();
	}

	public void setAsIndex(int index) throws CoordinateExceededException{
		if (index > 63 || index < 0){
			throw new CoordinateExceededException();
		}
		row = index/8;
		column = index%8;
	}
	
	/** Get the row index represent by the coordinate.
	 */
	public int getRow(){
		return this.row;
	}

	/** Get the column index represent by the coordinate.
	 */
	public int getColumn(){
		return this.column;
	}
	/** Get the index represented by the coordinate.
	 */
	public int getAsIndex(){
		return row*8 + column;
	}

	/**Increase both the indexes of the coordinate.
	 * 
	 * @param row how many rows i want to move (with sign <b>+</b> -> UP , <b>-</b> -> DOWN)
	 * @param column how many rows i want to move (with sign <b>+</b> -> UP , <b>-</b> -> DOWN)
	 * @throws CoordinateExceededException
	 */
	public void increase(int row, int column) throws CoordinateExceededException {

		int finalRow = this.row + row;
		int finalColumn = this.column + column;

		if (finalRow > 7 || finalRow < 0 || finalColumn > 7 || finalColumn < 0 ) {
			throw new CoordinateExceededException();
		}else{
			this.row = finalRow;
			this.column = finalColumn;
		}
	}

	/**Increase the vertical index of the coordinate.
	 *
	 * @param row how many rows i want to move (with sign <b>+</b> -> UP , <b>-</b> -> DOWN)
	 * @throws CoordinateExceededException
	 */
	public void increaseVertical(int row) throws CoordinateExceededException {
		this.increase(row,0);
	}
	/**Increase the horizontal index of the coordinate.
	 * 
	 * @param column how many rows i want to move (with sign <b>+</b> -> UP , <b>-</b> -> DOWN)
	 * @throws CoordinateExceededException
	 */
	public void increaseHorizontal(int column) throws CoordinateExceededException {

		this.increase(0,column);
	}
	
	/**{@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Coordinate)){
			return false;
		}

		if (this.getAsIndex() == ((Coordinate)obj).getAsIndex()){
			return true;
		}else{
			return false;
		}
	}

	/**{@inheritDoc}
	 */
	@Override
	public Coordinate clone(){
		try {
			return new Coordinate(this.row, this.column);
		} catch (CoordinateExceededException e) {
			e.printStackTrace();
			System.out.println("Errore durante la clonazione di Coordiante	");
		}
		return null;
	}

	/**{@inheritDoc}
	 */
	@Override
	public String toString(){
		char c = (char) (column + 'a');
		return "(" + (row+1) + ";" + c + ")";
	}

	public String toString(boolean numeric){
		if (numeric) {
			return "(" + this.row + "," + this.column + ")";
		}else{
			return this.toString();
		}
	}

	public static void main(String[] args){

		Scanner tastiera = new Scanner(System.in);
		int a,c;
		char b,d;
		Chessboard scacchiera = new Chessboard();
		scacchiera.printChessboard();
	  	//scacchiera.printChessboardsMoves();

		while (true){
			System.out.print("Insert init 1: ");
			a = tastiera.nextInt();
			System.out.print("Insert init 2: ");
			b = tastiera.next().charAt(0);

			System.out.print("Insert final 1: ");
			c = tastiera.nextInt();
			System.out.print("Insert final 2: ");
			d = tastiera.next().charAt(0);

			try {
				scacchiera.move(new Move(new Coordinate(a,(char) b),new Coordinate(c,(char) d)));
			} catch (Exception e) {
				System.err.println("Errore nella mossa" + a + b + c + d);
				e.printStackTrace();
			}
			scacchiera.printChessboard();
		}
	}

}
