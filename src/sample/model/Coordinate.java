package sample.model;

import sample.model.exception.CoordinateExceededException;

import java.util.ArrayList;

/** Class representic the coordinate in the chessboard.
 * Created by Pietro on 22/10/2016.
 */

public class Coordinate {

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

	public void setCoordinate(int row, int column)throws CoordinateExceededException{
		if (row > 7 || row < 0 || column > 7 || column < 0 ) {
			throw new CoordinateExceededException();
		}

		this.row = row;
		this.column = column;
	}

	public void setAsIndex(int index) throws CoordinateExceededException{
		if (index > 63 || index < 0){
			throw new CoordinateExceededException();
		}
		row = index/8;
		column = index%8;
	}

	public int getRow(){
		return this.row;
	}

	public int getColumn(){
		return this.column;
	}

	public int getAsIndex(){
		return row*8 + column;
	}

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

	/**
	 *
	 * @param row how many rows i want to move (with sign <b>+</b> -> UP , <b>-</b> -> DOWN)
	 * @throws CoordinateExceededException
	 */
	public void increaseVertical(int row) throws CoordinateExceededException {
		this.increase(row,0);
	}

	public void increaseHorizontal(int column) throws CoordinateExceededException {

		this.increase(0,column);
	}
	

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

	@Override
	public String toString(){
		return "(" + row + ";" + column + ")";
	}


	public static void main(String[] args){

		Chessboard scacchiera = new Chessboard();
		scacchiera.printChessboard();


	}

}
