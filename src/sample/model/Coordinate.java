package sample.model;

/**
 * Created by Pietro on 22/10/2016.
 */

public class Coordinate{

private int horizontal;
private int vertical;


	//TODO: migliorare il nome dell'eccezione
	public Coordinate(int horizontal, int vertical)throws Exception{
		if (horizontal > 7 || horizontal < 0 || vertical > 7 || vertical < 0 ) {
			throw new Exception();
		}
	//TODO: controllare che no ci sia già una pedina in quelle coordinate
		this.horizontal = horizontal;
		this.vertical = vertical;
	}

	@Override
	public String toString(){
		return "" + horizontal + ";" + vertical;
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

	//TODO: migliorare l'eccezione
	public void setAsIndex(int index) throws Exception{
		if (index > 63 || index < 0){
			throw new Exception();
		}
		horizontal = index%8;
		vertical = index/8;
	}
}
