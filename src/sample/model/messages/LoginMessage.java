package sample.model.messages;

import sample.model.Player;

/**
 * Created by Pietro on 08/12/2016.
 */
public class LoginMessage extends Message{

	private Player player;

	public LoginMessage(Player player){
		super(ActionType.SPECIAL);
		this.player = player;
	}

	public LoginMessage(String message, Player player){
		super(ActionType.SPECIAL, message);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
