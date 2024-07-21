package org.base23.web.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.base23.web.annotation.impl.I18nConstantSerializer;

/**
 * 国际化翻译常量
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = I18nConstantSerializer.class)
@JacksonAnnotationsInside
public @interface I18nConstant {

  /**
   * 常量名
   */
  String value();

  /**
   * 翻译后的结果放哪个字段
   */
  String targetFieldName() default "";
}
