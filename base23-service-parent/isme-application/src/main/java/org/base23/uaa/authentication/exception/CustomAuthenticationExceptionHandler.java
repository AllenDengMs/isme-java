package org.base23.uaa.authentication.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.base23.commons.utils.JSON;
import org.base23.web.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationExceptionHandler implements
    AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    PrintWriter writer = response.getWriter();
    writer.print(JSON.stringify(Result.fail("${authentication.fail:认证失败}")));
    writer.flush();
    writer.close();
  }
}
