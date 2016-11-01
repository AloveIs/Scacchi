package sample.model.pieces;

import javafx.scene.image.Image;

/**Enum Class to represent all the types of pieces in Chess, not distinguishing
 * between different color types
 * Created by Pietro on 22/10/2016.
 */
public enum PieceType {

	//TODO: sisteemare il fatto che non mi fa mettere oggetti di tipo immagine

	KING("king", "king.png"),
	QUEEN("queen", "king.png"),
	ROOK("rook", "king.png"),	//TORRE
	BISHOP("bishop", "king.png"),
	KNIGHT("knight", "king.png"),	//CAVALLO
	PAWN("pawn","king.png")	//PEDONE
	;

	private String symbol;
	private String imgURL;

	PieceType(String symbol, String img){
		this.symbol = symbol;
		this.imgURL = img;
	}

	/**Method to represnt values by their name as strings.
	 * @return returns the string representation of the enum value
	 */
	public String getSymbol(){
		return this.symbol;
	}


}
