package sample.model;

import sample.model.pieces.PieceColor;

/**
 * Created by Pietro on 25/10/2016.
 */
public class Player {

	private String name;
	private PieceColor side;

	public Player(String name, PieceColor side){
		this.name = name;
		this.side = side;
	}


	public String getName() {
		return name;
	}

	public PieceColor getSide() {
		return side;
	}

	@Override
	public String toString() {
		return name + "[" + side.toString() + "]";
	}
}
