package sample.model.pieces;

/**
 * Created by Pietro on 22/10/2016.
 */
public enum PieceColor {
	BLACK("Nero"),
	WHITE("Bianco");

	private String symbol;

	private PieceColor(String symbol){
		this.symbol = symbol;
	}

	public String toString(){
		return this.symbol;
	}
}
