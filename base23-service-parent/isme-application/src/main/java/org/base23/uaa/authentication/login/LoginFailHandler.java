package org.base23.uaa.authentication.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.base23.commons.utils.WebJSON;
import org.base23.web.model.Result;
import org.base23.web.model.ResultBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * AbstractAuthenticationProcessingFilter抛出AuthenticationException异常后，会跑到这里来
 */
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    String errorMessage = exception.getMessage();
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    PrintWriter writer = response.getWriter();
    Result responseData = ResultBuilder.aResult()
        .data(null)
        .code(400)
        .msg(errorMessage)
        .build();
    writer.print(WebJSON.stringify(responseData));
    writer.flush();
    writer.close();
  }
}
