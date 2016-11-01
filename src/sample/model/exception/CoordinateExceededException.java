package sample.model.exception;

/**
 * Created by Pietro on 31/10/2016.
 */
public class CoordinateExceededException extends Exception {

	public CoordinateExceededException() {
	}

	public CoordinateExceededException(String message) {
		super(message);
	}
}
