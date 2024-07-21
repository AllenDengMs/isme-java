package org.base23.web.model;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 */
public class Result implements Serializable {

  private static final long serialVersionUID = -6149948941889889657L;
  private int code;
  private String message;
  private Object data;

  public static final int SUCCESS_CODE = 0;
  public static final String SUCCESS_MSG = "OK";
  public static final int FAIL_CODE = 400;

  public static Result success() {
    return ResultBuilder.aResult()
        .data(null)
        .code(SUCCESS_CODE)
        .msg(SUCCESS_MSG)
        .build();
  }

  public static Result success(String message) {
    return ResultBuilder.aResult()
        .data(null)
        .code(SUCCESS_CODE)
        .msg(message)
        .build();
  }

  public static Result row(int row) {
    return data(row > 0);
  }

  public static Result data(Object data) {
    return ResultBuilder.aResult()
        .data(data)
        .code(SUCCESS_CODE)
        .msg(SUCCESS_MSG)
        .build();
  }

  public static Result data(Object data, String responseMessage) {
    return ResultBuilder.aResult()
        .data(data)
        .code(SUCCESS_CODE)
        .msg(responseMessage)
        .build();
  }

  public static Result fail(Object data, String msg) {
    return ResultBuilder.aResult()
        .data(data)
        .code(FAIL_CODE)
        .msg(msg)
        .build();
  }

  public static Result fail(String msg) {
    return ResultBuilder.aResult()
        .data(null)
        .code(FAIL_CODE)
        .msg(msg)
        .build();
  }

  public static Result fail(Object data) {
    return ResultBuilder.aResult()
        .data(data)
        .code(FAIL_CODE)
        .msg(SUCCESS_MSG)
        .build();
  }

  public Result() {
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
