package org.base23.commons.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JSON {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    // 反序列时，遇到未知的字段，不报错。比如Json中有key1字段，Java的object中没有key1字段，如果不设置成false，反序列时会报错
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static <T> T convert(Object obj, Class<T> returnType) {
    if (obj == null) {
      return null;
    }
    return parse(stringify(obj), returnType);
  }

  /**
   * Object to json
   *
   * @param obj
   * @return
   */
  public static String stringify(Object obj) {
    try {
      if (obj == null) {
        return null;
      } else if (obj instanceof String) {
        return obj.toString();
      }
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new IllegalArgumentException("对象转化成json字符串出错", e);
    }
  }

  /**
   * json to Object
   *
   * @param json
   * @param targetType
   * @param <T>
   * @return
   */
  public static <T> T parse(String json, Type targetType) {
    try {
      return objectMapper.readValue(json, TypeFactory.defaultInstance().constructType(targetType));
    } catch (IOException e) {
      throw new IllegalArgumentException("将JSON转换为对象时发生错误:" + json, e);
    }
  }

  public static <T> T parse(String json, Class<T> targetType) {
    try {
      return objectMapper.readValue(json, TypeFactory.defaultInstance().constructType(targetType));
    } catch (IOException e) {
      throw new IllegalArgumentException("将JSON转换为对象时发生错误:" + json, e);
    }
  }

  /**
   * json to Object
   */
  public static <T> T parse(String json, TypeReference<T> typeReference) {
    if (json != null && !json.isEmpty()) {
      try {
        return objectMapper.readValue(json, typeReference);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      return null;
    }
  }

  public static Map<String, Object> parseToMap(String json) {
    return parse(json, HashMap.class);
  }
}
