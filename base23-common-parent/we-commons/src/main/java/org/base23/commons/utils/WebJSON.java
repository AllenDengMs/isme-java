package org.base23.commons.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class WebJSON {

  public static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    // 数字相关的类型，全部格式化成字符串. todo 开源版，为了兼容前端项目，这里不转字符串，但long类型给前端会有丢失精度问题，特别是雪花id。请注意
//    SimpleModule simpleModule = new SimpleModule();
//    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//    objectMapper.registerModule(simpleModule);
    // 当json传来多余的字段过来，反序列化时不抛异常
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
