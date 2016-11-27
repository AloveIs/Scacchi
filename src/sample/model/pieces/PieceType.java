package sample.model.pieces;

/**Enum Class to represent all the types of pieces in Chess, not distinguishing
 * between different color types
 * Created by Pietro on 22/10/2016.
 */
public enum PieceType {

	//TODO: sisteemare il fatto che non mi fa mettere oggetti di tipo immagine

	KING("king", 	"king.png"),
	QUEEN("queen", 	"queen.png"),
	ROOK("rook", 	"rook.png"),	
	BISHOP("bishop","bishop.png"),
	KNIGHT("knight","knight.png"),
	PAWN("pawn",	"pawn.png")	
	;

	private String symbol;
	private String imgURL;

	PieceType(String symbol, String img){
		this.symbol = symbol;
		this.imgURL = img;
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

}
