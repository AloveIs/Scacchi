package sample.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sample.model.messages.JSONCodecManager;
import sample.model.messages.LoginMessage;
import sample.model.messages.Message;

import java.util.ArrayList;
import java.util.List;


public class JSONTest {

	public static void main(String[] args) {

		List<Message> list = new ArrayList<>();

		LoginMessage r = null;
		Message k = null;


			r = new LoginMessage(null);
			k = new LoginMessage(null);
			list.add(r);
			list.add(k);

		Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new JSONCodecManager<Message>()).create();

		String msg = gson.toJson(r, Message.class);
		String msg2 = gson.toJson(k, Message.class);
		System.out.println(msg);
		System.out.println(msg2);
		Message p = gson.fromJson(msg, Message.class);
		if (p == null){
			System.exit(57);
		}
		System.out.println(p);
		System.out.println(p instanceof LoginMessage);

	}
}
