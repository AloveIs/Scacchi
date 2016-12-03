package sample.model;

import sample.model.pieces.Piece;

import java.io.Serializable;

/**
 * Created by Pietro on 22/10/2016.
 */
public class Move implements Serializable{


private Coordinate startPos;
private Coordinate finalPos;

//TODO: sistemare l'eccezione
	public Move (Coordinate startPos , Coordinate finalPos)throws Exception{

		if (startPos == null || finalPos == null){
			throw new Exception();
		}

		this.startPos = startPos;
		this.finalPos = finalPos;
		}

	public Coordinate getOrigin(){

		return startPos;
	}

public Coordinate getDestination(){

		return finalPos;
	}


	@Override
	public String toString(){

		return startPos.toString() + "->" + finalPos.toString();

	}

}
