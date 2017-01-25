package sample.model.messages;


import com.jfoenix.controls.JFXSnackbar;
import sample.server.GameServer;
import sample.server.ServerPlayer;

/** Superclass for all the message sent by the server
 * Created by Pietro on 08/12/2016.
 */
public class Message {
	protected ActionType type;
	protected String message;

	public Message(ActionType type, String message) {
		this.type = type;
		this.message = message;
	}

	public Message(ActionType type) {
		this.type = type;
		message = "";
	}

	public Message(String message) {
		this.type = null;
		this.message = message;
	}

	public Message() {
	}

	public String getMessage() {
		return message;
	}

	public ActionType getType() {
		return type;
	}

	public void haveEffect(){}

	public void serverAction(GameServer game, ServerPlayer player){}


	public void triggerNote(JFXSnackbar snackbar){
		if (message != null){
			snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(message));
		}
	}
}
