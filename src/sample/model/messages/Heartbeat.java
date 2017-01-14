package sample.model.messages;

import sample.model.ActionType;

/**
 * Created by Pietro on 10/01/2017.
 */
public class Heartbeat extends Message{


	public Heartbeat(ActionType type, String message) {
		super(type, message);
	}

	@Override
	public void haveEffect(){
		System.out.println("Heartbeat " + message );
	}

}
