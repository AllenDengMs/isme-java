package org.base23.web.utils;

import org.springframework.context.ApplicationContext;

public class SpringBeanTool {

  private static ApplicationContext context;

  public static ApplicationContext setApplicationContext(ApplicationContext context) {
    return context;
  }

  public static ApplicationContext getApplicationContext() {
    return context;
  }
}