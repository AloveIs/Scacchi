package sample.model.messages;

/**
 * Created by Pietro on 10/12/2016.
 */
public class InvalidLoginMessage extends Message{

	public InvalidLoginMessage(){
		super();
	}

	public InvalidLoginMessage(String msg){
		this.message = msg;
	}
}
