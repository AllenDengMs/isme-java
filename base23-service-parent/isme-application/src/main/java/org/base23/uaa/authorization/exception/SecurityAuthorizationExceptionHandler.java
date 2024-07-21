package org.base23.uaa.authorization.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.base23.web.model.Result;
import org.base23.web.model.ResultBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityAuthorizationExceptionHandler {

  @ExceptionHandler(value = AccessDeniedException.class)
  public Result exceptionHandler(HttpServletResponse response, Exception e) {
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResultBuilder.aResult()
        .code(400)
        .msg("${low.power:无权限！}")
        .build();
  }

}
