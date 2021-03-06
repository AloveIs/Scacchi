package sample.model;

import sample.model.exception.CoordinateExceededException;

/** Class representic the coordinate in the chessboard.
 * Created by Pietro on 22/10/2016.
 */

public class Coordinate{

private int horizontal;
private int vertical;

	/**The values that the constructor accept are integers
	 * int the range from 0 to 8, as the width and the height
	 * of the chessboard
	 * @param horizontal the orizontal component of the coordinate
	 * @param vertical the vertical component of the coordinate
	 * @throws CoordinateExceededException if at least one of the parameters exceed its domain
	 */
	public Coordinate(int horizontal, int vertical)throws CoordinateExceededException{
		if (horizontal > 7 || horizontal < 0 || vertical > 7 || vertical < 0 ) {
			throw new CoordinateExceededException();
		}
	//TODO: controllare che non ci sia già una pedina in quelle coordinate
		this.horizontal = horizontal;
		this.vertical = vertical;
	}

	@Override
	public String toString(){
		return "(" + horizontal + ";" + vertical + ")";
	}

	public int getHorizontal(){
		return this.horizontal;
	}

	public int getVertical(){
		return this.vertical;
	}

	public int getAsIndex(){
		return vertical*8 + horizontal;
	}

	public Coordinate increase(int horizontal, int vertical) throws CoordinateExceededException {
		return new Coordinate(this.horizontal + horizontal , this.vertical + vertical);
	}

	public Coordinate increaseVertical(int vertical) throws CoordinateExceededException {

		return new Coordinate(this.horizontal, this.vertical + vertical);
	}

	public Coordinate increaseHorizontal(int horizontal) throws CoordinateExceededException {

		return new Coordinate(this.horizontal + horizontal , this.vertical);
	}


	public void setAsIndex(int index) throws CoordinateExceededException{
		if (index > 63 || index < 0){
			throw new CoordinateExceededException();
		}
		horizontal = index%8;
		vertical = index/8;
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
	public Coordinate clone() throws CloneNotSupportedException {
		try {
			return new Coordinate(this.horizontal, this.vertical);
		} catch (CoordinateExceededException e) {
			e.printStackTrace();
			System.out.println("Errore durante la clonazione di Coordiante	");
		}
		return null;
	}


	public static void main(String[] args){

		Chessboard scacchiera = new Chessboard();

		Coordinate c1 = null, c2 = null, c3;


		try {
			c1 = new Coordinate(3,4);
			System.out.println(c1.toString());
		} catch (CoordinateExceededException e) {
			e.printStackTrace();
			System.out.println("Errore prima coordinata");
		}

		try {
			c2 = c1.increase(2,1);
			System.out.println(c2.toString());
		} catch (CoordinateExceededException e) {
			//e.printStackTrace();
			System.out.println("Errore seconda coordinata");
		}

		try {
			c2 = c1.increase(-4,-1);
			System.out.println(c2.toString());
			System.out.println(c2.equals(new Coordinate(0,3)));
		} catch (CoordinateExceededException e) {
			//e.printStackTrace();
			System.out.println("Errore seconda coordinata");
		}



	}

}
