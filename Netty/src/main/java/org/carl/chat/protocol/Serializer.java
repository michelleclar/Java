package org.carl.chat.protocol;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public interface Serializer {

    // 反序列化方法
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    // 序列化方法
    <T> byte[] serialize(T object);

    enum Algorithm implements Serializer {

        Java {
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("反序列化失败", e);
                }
            }

            @Override
            public <T> byte[] serialize(T object) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);
                    return bos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败", e);
                }
            }
        },

        Json {
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new Serializer.ClassCodec())
//                        .registerTypeAdapter(Exception.class,new ExceptionSerializer())
                        .create();
                String json = new String(bytes, StandardCharsets.UTF_8);
                return gson.fromJson(json, clazz);
            }

            @Override
            public <T> byte[] serialize(T object) {
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new Serializer.ClassCodec())
//                        .registerTypeAdapter(Exception.class,new ExceptionSerializer())
                        .create();
                String json = gson.toJson(object);
                return json.getBytes(StandardCharsets.UTF_8);
            }
        }
    }

    class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

        @Override
        public Class<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                String str = json.getAsString();
                return Class.forName(str);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        @Override             //   String.class
        public JsonElement serialize(Class<?> src, Type typeOfSrc, JsonSerializationContext context) {
            // class -> json
            return new JsonPrimitive(src.getName());
        }
    }
    class ExceptionSerializer implements JsonSerializer<Exception>
    {
        @Override
        public JsonElement serialize(Exception src, Type typeOfSrc, JsonSerializationContext context)
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("cause", new JsonPrimitive(String.valueOf(src.getCause())));
            jsonObject.add("message", new JsonPrimitive(src.getMessage()));
            jsonObject.add("detailMessage",new JsonPrimitive(src.getMessage()));
            return jsonObject;
        }
    }

    class ThrowableAdapterFactory implements TypeAdapterFactory {
        private ThrowableAdapterFactory() {
        }

        public static final ThrowableAdapterFactory INSTANCE = new ThrowableAdapterFactory();

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            // Only handles Throwable and subclasses; let other factories handle any other type
            if (!Throwable.class.isAssignableFrom(type.getRawType())) {
                return null;
            }

            @SuppressWarnings("unchecked")
            TypeAdapter<T> adapter = (TypeAdapter<T>) new TypeAdapter<Throwable>() {
                @Override
                public Throwable read(JsonReader in) throws IOException {
                    System.out.println(in.toString());
                    return null;
//                    throw new UnsupportedOperationException();
                }

                @Override
                public void write(JsonWriter out, Throwable value) throws IOException {
                    if (value == null) {
                        out.nullValue();
                        return;
                    }

                    out.beginObject();
                    // Include exception type name to give more context; for example NullPointerException might
                    // not have a message
                    out.name("type");
                    out.value(value.getClass().getSimpleName());

                    out.name("message");
                    out.value(value.getMessage());

                    Throwable cause = value.getCause();
                    if (cause != null) {
                        out.name("cause");
                        write(out, cause);
                    }

                    Throwable[] suppressedArray = value.getSuppressed();
                    if (suppressedArray.length > 0) {
                        out.name("suppressed");
                        out.beginArray();

                        for (Throwable suppressed : suppressedArray) {
                            write(out, suppressed);
                        }

                        out.endArray();
                    }
                    out.endObject();
                }
            };
            return adapter;
        }


    }
}