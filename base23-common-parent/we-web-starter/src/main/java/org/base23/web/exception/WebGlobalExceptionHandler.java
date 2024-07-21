package org.base23.web.exception;

import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import org.base23.commons.utils.JSON;
import org.base23.web.model.Result;
import org.base23.web.model.ResultBuilder;
import org.base23.web.utils.I18nMessageTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class WebGlobalExceptionHandler {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(value = Exception.class)
  public Result exceptionHandler(HttpServletResponse response, Exception e) {
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    logger.info("服务器异常", e);
    return Result.fail("服务器异常");
  }

  @ExceptionHandler(value = NoResourceFoundException.class)
  public Result exceptionHandler(HttpServletResponse response, NoResourceFoundException e) {
    response.setStatus(HttpStatus.NOT_FOUND.value());
    return ResultBuilder.aResult()
        .msg("api not found")
        .code(404)
        .build();
  }


  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public Result exceptionHandler(HttpServletResponse response, MethodArgumentNotValidException e) {
    response.setStatus(HttpStatus.BAD_REQUEST.value());

    // 国际化翻译 数据校验异常信息
    BindingResult result = e.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    HashMap<String, String> errorFields = new HashMap<>();
    for (FieldError error : fieldErrors) {
      String fieldName = error.getField();
      errorFields.put(fieldName, I18nMessageTool.translate(error.getDefaultMessage()));
    }

    return Result.fail(JSON.stringify(errorFields));
  }

  @ExceptionHandler(value = BaseException.class)
  public Result exceptionHandler(HttpServletResponse response, BaseException e) {
    response.setStatus(e.getHttpStatus().value());
    return createResult(e);
  }

  private Result createResult(BaseException e) {
    return ResultBuilder.aResult()
        .msg(e.getMessage())
        .code(e.getCode() == null ? Result.FAIL_CODE : e.getCode())
        .build();
  }
}
