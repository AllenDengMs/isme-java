package org.base23.uaa.business.service;

public interface JwtService {

  /**
   * 生成Jwt
   * @param expiredAt 过期时间，时间戳，单位：毫秒
   */
  String createJwt(Object jwtPayload, long expiredAt);

  /**
   * 校验Jwt签名，并返回Jwt的payload
   * @param jwt jwt字符串
   * @param jwtPayloadClass 返回结果的类型
   */
  <T> T verifyJwt(String jwt, Class<T> jwtPayloadClass);
}
