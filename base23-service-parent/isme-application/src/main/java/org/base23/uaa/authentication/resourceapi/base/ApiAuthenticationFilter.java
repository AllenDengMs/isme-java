package org.base23.uaa.authentication.resourceapi.base;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.base23.uaa.business.service.JwtService;
import org.base23.uaa.core.domain.dto.CurrentUser;
import org.base23.uaa.core.exception.UaaExceptions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


public class ApiAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  public ApiAuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String jwtToken = request.getHeader("Authorization");
    if (StringUtils.isEmpty(jwtToken)) {
      throw UaaExceptions.MISSING_TOKEN.exception();
    }
    if (jwtToken.startsWith("Bearer ")) {
      jwtToken = jwtToken.substring(7);
    } else {
      throw UaaExceptions.NOT_BEARER_TOKEN.exception();
    }

    // 认证开始前，按SpringSecurity设计，要将Authentication设置到SecurityContext里面去。
    ApiAuthentication authentication = new ApiAuthentication();
    authentication.setJwtToken(jwtToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    CurrentUser currentUser = null;
    try {
      currentUser = jwtService.verifyJwt(jwtToken, CurrentUser.class);
    } catch (ExpiredJwtException e) {
      throw UaaExceptions.TOKEN_EXPIRED.exception();
    } catch (Exception e) {
      throw UaaExceptions.TOKEN_INVALID.exception();
    }

    authentication.setAuthenticated(true); // 设置true，认证通过。
    authentication.setCurrentUser(currentUser);
    // 放行
    filterChain.doFilter(request, response);
  }
}
