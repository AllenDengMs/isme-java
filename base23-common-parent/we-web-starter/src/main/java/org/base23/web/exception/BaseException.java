package org.base23.web.exception;

import java.io.Serial;
import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -7972131521045668011L;

  public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;
  public static final int DEFAULT_CODE = 400;

  private final HttpStatus httpStatus;
  private final Integer code; // 自定义一个全局唯一的code，

  public BaseException(String message, int code, HttpStatus httpStatus) {
    super(message);
    this.code = code;
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public Integer getCode() {
    return code;
  }
}