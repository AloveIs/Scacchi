package sample.model.messages;



import com.google.gson.*;

import java.lang.reflect.Type;
/**
 * Created by Pietro on 08/12/2016.
 */
public class JSONCodecManager<T> implements JsonSerializer<T>, JsonDeserializer<T> {

	@Override
	public JsonElement serialize(final T o, final Type typeOfSrc, final JsonSerializationContext context) {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("type", o.getClass().getName());
		jsonObject.add("data", context.serialize(o, o.getClass()));
		return jsonObject;
	}

	@Override
	public T deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		JsonElement j = jsonObject.get("type");
		String type = j.getAsString();

		try {
			Type casting = Class.forName(type);
			return context.deserialize(jsonObject.get("data"), casting);
		} catch (ClassNotFoundException e) {
			throw new JsonParseException("Cannot find class " + type);
		}
	}
}