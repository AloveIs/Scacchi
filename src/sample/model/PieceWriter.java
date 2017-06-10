package sample.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import sample.model.exception.CoordinateExceededException;
import sample.model.messages.JSONCodecManager;
import sample.model.pieces.Pawn;
import sample.model.pieces.Piece;
import sample.model.pieces.PieceColor;
import sample.model.pieces.Queen;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


class PieceWriter {
	public static void main(String[] args) throws FileNotFoundException, CoordinateExceededException {

		Gson encoder = new GsonBuilder().registerTypeAdapter(Piece.class, new JSONCodecManager<>()).create();
		PrintWriter writer;
		// write all the cards into the file

		writer = new PrintWriter(new File("src/sample/prova.txt"));

		Piece c = new Pawn(PieceColor.WHITE, new Coordinate(1,1), new Chessboard());
		writer.println(encoder.toJson(c));
		c = new Queen(PieceColor.BLACK, new Coordinate(1,1), new Chessboard());
		writer.println(encoder.toJson(c));
		writer.close();

	}
}
