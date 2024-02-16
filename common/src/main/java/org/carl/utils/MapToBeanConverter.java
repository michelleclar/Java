package org.carl.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class MapToBeanConverter {
  public static <T> T convert(Map<String, Object> map, Class<T> beanClass){
      T bean = null;
      try {
          bean = beanClass.getDeclaredConstructor().newInstance();
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          throw new RuntimeException(e);
      }

      for (Map.Entry<String, Object> entry : map.entrySet()) {
      String fieldName = entry.getKey();
      Object fieldValue = entry.getValue();

      Field field = getField(beanClass, fieldName);
      if (field != null) {
        field.setAccessible(true);
          try {
              field.set(bean, fieldValue);
          } catch (IllegalAccessException e) {
              throw new RuntimeException(e);
          }
      }
    }

    return bean;
  }

  //
  // public static <T> T convert(Map<String, String> map, Class<T> beanClass) throws Exception {
  // T bean = beanClass.getDeclaredConstructor().newInstance();
  //
  // for (Map.Entry<String, String> entry : map.entrySet()) {
  // String fieldName = entry.getKey();
  // String fieldValue = entry.getValue();
  //
  // Field field = getField(beanClass, fieldName);
  // if (field != null) {
  // field.setAccessible(true);
  // setFieldValue(field, bean, fieldValue);
  // }
  // }
  //
  // return bean;
  // }

  private static Field getField(Class<?> clazz, String fieldName) {
    try {
      return clazz.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      // If field not found in this class, try superclass recursively
      if (clazz.getSuperclass() != null) {
        return getField(clazz.getSuperclass(), fieldName);
      }
      return null; // Field not found
    }
  }

  private static <T> void setFieldValue(Field field, T bean, String value) throws Exception {
    Class<?> fieldType = field.getType();
    if (fieldType == String.class) {
      field.set(bean, value);
    } else if (fieldType == int.class || fieldType == Integer.class) {
      field.set(bean, Integer.parseInt(value));
    } else if (fieldType == long.class || fieldType == Long.class) {
      field.set(bean, Long.parseLong(value));
    } else if (fieldType == double.class || fieldType == Double.class) {
      field.set(bean, Double.parseDouble(value));
    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
      field.set(bean, Boolean.parseBoolean(value));
    } // Add more types as needed
  }
}
