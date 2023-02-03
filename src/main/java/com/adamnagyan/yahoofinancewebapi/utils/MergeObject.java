package com.adamnagyan.yahoofinancewebapi.utils;

import java.lang.reflect.Field;

public class MergeObject {
  public static <T> T mergeObjects(T first, T second) {
    Class<?> clas = first.getClass();
    Field[] fields = clas.getDeclaredFields();
    Object result = null;
    try {
      result = clas.getDeclaredConstructor().newInstance();
      for (Field field : fields) {
        field.setAccessible(true);
        Object value1 = field.get(first);
        Object value2 = field.get(second);
        Object value = (value1 != null) ? value1 : value2;
        field.set(result, value);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (T) result;
  }
}
