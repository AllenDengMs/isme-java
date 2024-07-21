package org.base23;

import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableCaching // 开启缓存注解
@MapperScan({"org.base23.i18n.mapper", "org.base23.uaa.business.dao"}) // mybatis扫描
@SpringBootApplication(scanBasePackages = "org.base23.*")
public class IsmeApplication {

  public static void main(String[] args) {
    SpringApplication.run(IsmeApplication.class, args);
  }

  // 允许跨域请求
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(List.of("*")); // 使用 setAllowedOriginPatterns 来允许所有域名
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.setAllowCredentials(true); // 允许携带凭证

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}