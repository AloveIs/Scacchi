package sample.model.pieces;

import javafx.scene.image.Image;

/**
 * Created by Pietro on 22/10/2016.
 */
public enum PieceType {
	KING("king", new Image("king.png")),
	QUEEN("queen", new Image("king.png")),
	ROOK("rook", new Image("king.png")),	//TORRE
	BISHOP("bishop", new Image("king.png")),
	KNIGHT("knight", new Image("king.png")),	//CAVALLO
	PAWN("pawn", new Image("king.png"))	//PEDONE
	;

	private String symbol;
	private Image img;

	private PieceType(String symbol, Image img){
		this.symbol = symbol;
		this.img = img;
	}
	public String getSymbol(){
		return this.symbol;
	}


}
