package sample.model.messages;

import sample.model.Chessboard;

/**
 * Created by Pietro on 08/12/2016.
 */
public abstract class Message {
	String message;

	public Message(String msg) {
		this.message = msg;
	}

	public Message() {
		message = null;
	}
}
