package sample.view;

import javafx.scene.image.Image;
import sample.model.pieces.Piece;
import sample.model.pieces.PieceColor;
import sample.model.pieces.PieceType;

import java.util.EnumMap;
import java.util.Map;

/** Class to associate a piece with its relative image,
 *
 * Created by Pietro on 21/12/2016.
 */
public class ImagePicker {

	private static Map<PieceType , Image> whiteMap = new EnumMap<>(PieceType.class);
	private static Map<PieceType , Image> blackMap = new EnumMap<>(PieceType.class);

	public static void initalize(){
		for (PieceType p : PieceType.values()){
			ImagePicker.whiteMap.put(p, new Image("/sample/view/whites/" + p.getImgURL()));
			ImagePicker.blackMap.put(p, new Image("/sample/view/blacks/" + p.getImgURL()));
		}
	}

	static Image get(Piece piece){

		if (piece == null){
			return null;
		}
		if (piece.getSide() == PieceColor.WHITE){
			return ImagePicker.whiteMap.get(piece.getType());
		}else {
			return ImagePicker.blackMap.get(piece.getType());
		}
	}
}
