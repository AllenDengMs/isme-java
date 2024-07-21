package org.base23.commons.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.base23.commons.annotation.impl.MaskSerializer;
import org.base23.commons.annotation.service.MaskDataHandler;
import org.base23.commons.annotation.service.MaskPhoneNumberHandler;
import org.base23.commons.annotation.service.MaskWithStarHandler;

/**
 * 屏蔽手机号
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = MaskSerializer.class)
@JacksonAnnotationsInside
public @interface Mask {

  /**
   * 指定屏蔽数据的处理器。默认支持：phone、star
   * @see MaskDataHandler
   * @see MaskPhoneNumberHandler
   * @see MaskWithStarHandler
   */
  String handler() default "";
}
