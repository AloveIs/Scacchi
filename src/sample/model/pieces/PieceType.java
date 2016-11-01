package sample.model.pieces;

import javafx.scene.image.Image;

/**
 * Created by Pietro on 22/10/2016.
 */
public enum PieceType {

	KING("king", "king.png"),
	QUEEN("queen", "king.png"),
	ROOK("rook", "king.png"),	//TORRE
	BISHOP("bishop", "king.png"),
	KNIGHT("knight", "king.png"),	//CAVALLO
	PAWN("pawn","king.png")	//PEDONE
	;

	private String symbol;
	private String imgURL;

	private PieceType(String symbol, String img){
		this.symbol = symbol;
		this.imgURL = img;
	}
	public String getSymbol(){
		return this.symbol;
	}


}
