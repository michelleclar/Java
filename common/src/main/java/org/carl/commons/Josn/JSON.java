package org.carl.commons.Josn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.carl.commons.Josn.parse.adapter.LocalDateAdapter;
import org.carl.commons.Josn.parse.adapter.LocalDateTimeAdapter;
import org.carl.commons.Josn.parse.adapter.ZonedDateTimeTypeAdapter;

public class JSON {
  static Gson gson;

  static {
    gson =
        new GsonBuilder()
            .setPrettyPrinting()
//            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(ZonedDateTimeTypeAdapter.class, new ZonedDateTimeTypeAdapter())
            .create();
  }

  public static String toJson(Object obj) {
    return gson.toJson(obj);
  }

  public static <T> T fromJson(String json, Class<T> clazz) {
    return gson.fromJson(json, clazz);
  }
}
