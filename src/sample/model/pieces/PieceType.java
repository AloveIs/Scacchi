package sample.model.pieces;

import java.io.Serializable;

/**Enum Class to represent all the types of pieces in Chess, not distinguishing
 * between different color types
 * Created by Pietro on 22/10/2016.
 */
public enum PieceType implements Serializable{

	//TODO: sistemare il fatto che non mi fa mettere oggetti di tipo immagine

	KING("King", 	"king.png","\u2654"),
	QUEEN("queen", 	"queen.png","\u2655"),
	ROOK("rook", 	"rook.png", "\u2656"),
	BISHOP("bishop","bishop.png", "\u2657"),
	KNIGHT("knight","knight.png", "\u2658"),
	PAWN("pawn",	"pawn.png", "\u2659")
	;

	private String symbol;
	private String imgURL;
	private String unicode;

	PieceType(String symbol, String img, String unicode){
		this.symbol = symbol;
		this.imgURL = img;
		this.unicode = unicode;
	}

	/**Method to represent values by their name as strings.
	 * @return returns the string representation of the enum value
	 */
	public String getSymbol(){
		return this.symbol;
	}
	/**Method to represnt values by their name as strings.
	 * @return returns the URL of the image associated with the piece
	 */
	public String getImgURL(){
		return this.imgURL;
	}

	/** Get the piece as an Unicode string
	 * @return the unicode representation of the piece as a string
	 */
	public String getUnicode(){return this.unicode;}

}
