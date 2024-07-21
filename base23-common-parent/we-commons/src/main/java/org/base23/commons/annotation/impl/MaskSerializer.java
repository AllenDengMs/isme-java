package org.base23.commons.annotation.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.base23.commons.annotation.Mask;
import org.base23.commons.annotation.service.MaskDataHandler;
import org.base23.commons.annotation.service.MaskPhoneNumberHandler;
import org.base23.commons.annotation.service.MaskWithStarHandler;

@JacksonStdImpl
public class MaskSerializer extends JsonSerializer<Object> implements
    ContextualSerializer {

  public static final String PHONE = "phone";
  public static final String STAR = "star";
  private static final Map<String, MaskDataHandler> handlers;

  static {
    handlers = new HashMap<>();
    addHandler(PHONE, new MaskPhoneNumberHandler());
    addHandler(STAR, new MaskWithStarHandler());
  }

  public static void addHandler(String handlerName, MaskDataHandler handler) {
    handlers.put(handlerName, handler);
  }

  private Mask maskAnnotation;

  @Override
  public void serialize(Object fieldValue, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (maskAnnotation.handler().equals("")) {
      gen.writeObject(null);
    } else {
      Object value = Optional.ofNullable(handlers.get(maskAnnotation.handler()))
          .map(handler -> handler.maskData(fieldValue))
          .orElse(null);
      gen.writeObject(value);
    }
  }

  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty beanProperty)
      throws JsonMappingException {
    maskAnnotation = beanProperty.getAnnotation(Mask.class);
    return this;
  }
}
