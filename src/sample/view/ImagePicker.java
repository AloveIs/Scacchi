package sample.view;

import javafx.scene.image.Image;
import sample.model.pieces.Piece;
import sample.model.pieces.PieceColor;

/** Class to associate a piece with its relative image,
 *
 * Created by Pietro on 21/12/2016.
 */
public class ImagePicker {


	static Image get(Piece piece){
		String colorPKG;

		if (piece == null){
			return null;
		}
		if (piece.getSide() == PieceColor.WHITE){
			colorPKG = "sample/view/whites/";
		}else {
			colorPKG = "sample/view/blacks/";
		}

		return new Image(colorPKG + piece.getType().getImgURL());
	}
}
