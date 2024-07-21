package org.base23.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.base23.commons.utils.IdGenerator;
import org.base23.commons.utils.WebJSON;
import org.base23.web.client.I18nDatabaseSource;
import org.base23.web.utils.I18nMessageTool;
import org.base23.web.utils.SpringBeanTool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CommonWebConfig implements WebMvcConfigurer, InitializingBean {

  @Value("${project.machine-id:1}")
  private Integer machineId;
  @Value("${project.datacenter-id:1}")
  private Integer datacenterId;

  private final ApplicationContext applicationContext;

  public CommonWebConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  /**
   * 全局配置Spring Controller序列化
   */
  @Bean
  public ObjectMapper objectMapper() {
    return WebJSON.objectMapper;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    // 更改雪花算法的machineId、datacenterId避免分布式环境下产生相同的id
    IdGenerator.register(machineId.longValue(), datacenterId.longValue());
    // 某些场景，需要通过静态方法获取到Bean，所以要用一个静态变量，指向 applicationContext
    SpringBeanTool.setApplicationContext(applicationContext);

    // 使得 I18nMessageTool 支持通过读取数据库的方式获取翻译
    try {
      I18nDatabaseSource messageSource = applicationContext.getBean(
          I18nDatabaseSource.class);
      I18nMessageTool.setDatabaseSource(messageSource);
    } catch (BeansException e) {
      // 可选，允许不设置DatabaseI18nMessageSource
    }
  }
}
