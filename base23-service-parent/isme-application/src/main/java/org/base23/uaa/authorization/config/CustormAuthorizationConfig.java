package org.base23.uaa.authorization.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity // 使用@PreAuthorize注解鉴权
public class CustormAuthorizationConfig {

}
