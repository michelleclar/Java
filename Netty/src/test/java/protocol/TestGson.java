package protocol;

import com.google.gson.*;
import org.carl.chat.message.RpcResponseMessage;
import org.carl.chat.protocol.Serializer;

import java.lang.reflect.Type;

public class TestGson {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class,new ClassCodec()).create();
//        System.out.println(new Gson().toJson(String.class));
        RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();
        rpcResponseMessage.setExceptionValue(null);
        rpcResponseMessage.setReturnValue("你好");
        String json = gson.toJson(rpcResponseMessage);
        System.out.println(gson.toJson(String.class));
    }

    static class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

        @Override
        public Class<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String str = json.getAsString();
            try {
                return Class.forName(str);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        @Override
        public JsonElement serialize(Class<?> src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getName());
        }
    }
}
