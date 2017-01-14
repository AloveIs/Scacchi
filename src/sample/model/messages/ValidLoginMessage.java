package sample.model.messages;

import sample.model.ActionType;

/**
 * Created by Pietro on 10/12/2016.
 */
public class ValidLoginMessage extends Message{
public ValidLoginMessage(){
	super(ActionType.SPECIAL);
}

	public ValidLoginMessage(String message){
		super(ActionType.SPECIAL, message);
	}

	@Override
	public void haveEffect() {
		super.haveEffect();
	}
}
