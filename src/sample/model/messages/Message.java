package sample.model.messages;


import com.jfoenix.controls.JFXSnackbar;
import sample.model.ActionType;

/**
 * Created by Pietro on 08/12/2016.
 */
public class Message {
	ActionType type;
	String message;

	public Message(ActionType type, String message) {
		this.type = type;
		this.message = message;
	}

	public Message(ActionType type) {
		this.type = type;
		message = "";
	}

	public String getMessage() {
		return message;
	}

	public ActionType getType() {
		return type;
	}

	public void triggerNote(JFXSnackbar snackbar){
		if (message != null){
			snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(message));
		}
	}
}
