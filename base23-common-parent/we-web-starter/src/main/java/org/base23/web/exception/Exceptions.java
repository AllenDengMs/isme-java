package org.base23.web.exception;

import org.springframework.http.HttpStatus;

/**
 * 抛异常的工具类。为了少些两句代码
 */
public class Exceptions {

  private Exceptions() {}

  public static void throwException(String message) {
    throw new BaseException(message, BaseException.DEFAULT_CODE, BaseException.DEFAULT_HTTP_STATUS);
  }

  public static void throwException(String message, Integer code) {
    throw new BaseException(message, code, BaseException.DEFAULT_HTTP_STATUS);
  }

  public static void throwException(String message, HttpStatus httpStatus) {
    throw new BaseException(message, BaseException.DEFAULT_CODE, httpStatus);
  }

  public static void throwException(String message, Integer code, HttpStatus httpStatus) {
    throw new BaseException(message, code,  httpStatus);
  }
}
