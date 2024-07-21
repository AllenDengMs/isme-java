package org.base23.commons.utils;

public class IdGenerator {

  private IdGenerator() {}

  private static SnowflakeUtil snowflake = new SnowflakeUtil(1, 1);

  /**
   * 注意在分布式环境，同的微服务，如果使用相同的 machineId和datacenterId，可能会出现重复的id
   */
  public static void register(long machineId, long datacenterId) {
    snowflake = new SnowflakeUtil(machineId, datacenterId);
  }

  /**
   * 雪花id
   */
  public static long snowflakeId() {
    return snowflake.getNextId();
  }
}
