package org.base23.web.annotation.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import org.base23.web.annotation.I18nConstant;
import org.base23.web.utils.LanguageTool;
import org.base23.web.utils.SpringBeanTool;
import org.springframework.context.MessageSource;

@JacksonStdImpl
public class I18nConstantSerializer extends JsonSerializer<Object> implements
    ContextualSerializer {

  private I18nConstant asConstantLabel;

  public static final Object[] EMPTY_ARGS = new Object[0];

  @Override
  public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeObject(value);
    try {
      // 翻译后的结果放的字段
      String fileName = asConstantLabel.targetFieldName().equals("") ?
          gen.getOutputContext().getCurrentName().concat("Label")
          : asConstantLabel.targetFieldName();

      // 读取 src/resources/i18n/messages.properties文件获取翻译
      String i18nKey = "constant.".concat(asConstantLabel.value()).concat(".")
          .concat(value.toString());
      MessageSource messageSource = SpringBeanTool.getApplicationContext()
          .getBean(MessageSource.class);
      String i18nValue = messageSource.getMessage(i18nKey, EMPTY_ARGS,
          LanguageTool.getRequestLanguage());
      gen.writeStringField(fileName, i18nValue);
    } catch (Exception e) {
    }
  }

  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty beanProperty)
      throws JsonMappingException {
    asConstantLabel = beanProperty.getAnnotation(I18nConstant.class);
    return this;
  }
}
