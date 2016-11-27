package sample.model;

import sample.model.pieces.PieceColor;

/**
 * Created by Pietro on 25/10/2016.
 */
public class Player {

	private final String name;
	//TODO: final potrebbe non essere necessario in questo caso perchè potrei fare più giochi consecutivi
	private final PieceColor side;
	private final Chessboard chessboard;

	public Player (String name, PieceColor side){
		this.name = name;
		this.side = side;
		this.chessboard = null;
	}

	public Player(String name, PieceColor side, Chessboard chessboard) {
		this.name = name;
		this.side = side;
		this.chessboard = chessboard;
	}

	public String getName() {
		return name;
	}

	public PieceColor getSide() {
		return side;
	}

}
