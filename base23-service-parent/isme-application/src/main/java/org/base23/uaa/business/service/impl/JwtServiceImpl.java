package org.base23.uaa.business.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.base23.commons.utils.JSON;
import org.base23.uaa.business.service.JwtService;
import org.base23.web.exception.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService, InitializingBean {

  private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

  private PrivateKey privateKey;

  private JwtParser jwtParser;

  @Value("${base23.login.jwt.private-key:MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDvCDjm5SdSA8Hb+4T+Gm2jqlKi+JeLsnNL2KnonX4JsH9tFjJ0X7pedUnl13DkABdI2jnVt4wpQRaC4qDlBXN+tg/DTreiNPL/fqmUWZOk2zu77Z93h+/LtKtLrg1Eh9qGnuxyno0wxduAOsUsVezookSwbtOGUDhdLiHkFL6gnqdKeNjPqILw9e3jBdKjJ/rW5ZKWiKFAv187RG4AdC0cS7C5PygcbbSyD/KVLBXX/zMXgDQx2U61tKrlS+8NbJWg4p+YRe3xXyHtksUAUAFba9Wt5/mW1jRpNOq/+wzmeIJrkksHTDRt4J3wW2JaWOTsAXSPVbbZiq1IPLbE/nwjAgMBAAECggEAeddZGejo2BduM7HLorLZ/DkPkl7g8KZvutOgGCBfZJUA/xv3b/Zzyz5CAtSEiNO7CrmiDVxYJ5cz4Feg59yVeJtZAZcYZ6hRzQZFbocSiU/u7OY9CPLTuqRHRHZd8PbG3yQXJn3HPns8XeqXIvhRoGtGVCDJ1YcClAy13crtOHVqZKqkZSdvQFA3deORXXNfY7wDfvvc4So7jGi+2atbWvb//B8J5GuRUkWNMFkBXjrJuWBlwzU9/PrM7tczvPixIdN5cO8XWqeY4/oqYB9DhFODyU1aE2M6kIEy+yuqMlVIRHCp7FDALZ8beddYFZYj5FMQlXa5fy2PKyZCbi/HUQKBgQD3/+xIgRH9a9Bmb0lJYsR/BKPLIkislkViqnRVLooAlcwDMIr4/JT/9o8O+LNgpNfp7FAkDkvmXkbERGNxa3gJW3jntAxohmIL3YgIZdXqatkFezO+PJM1nxi41PbS5eizNXzsDsvtD5iiWa4x1tAxmZ/REZaEH901zttZvM8qJwKBgQD2vj3WlLlHdvVQOsZlmBTpxAnXzMf3BLITkOMjZGOs3iYWMNA3X875v9GiOpBUCP0jV2dt5QcLqPIEScpyOnOW169t5EBS7yCOLi5mJNkeAikW8HgYV0suFuIA/iOeyjPMn4nhg9xPqDjcwnFmq7vhRHE0F/AJX/pzEeo3vSzHpQKBgGwds0nMkyYzCXCO1ZlbqKRjRnD5aktrW6Zu/zZfiqREqeM+F2gC3YZVW/q/65uXYdXGQw3k+avdr+ZClkPNAVC7AxOoR7yN0VKw6mwW0VJX8HLWSjGGQPsgd+ukVFKPDoqKKALVVIvtv7IPfMSXjL4C5kyD6WWCarLZkoElsf8DAoGAbXwMxGJJtEQ8pdTuo7XP0cqC85aSRDF5MuVfZBzvfY01KTOPsIJ6vKc4xdtmn2M9r6jg5Ap0DeBxQyXbBsSY9Z3O4dweDq68q1oijIBdNsuOn/cj0ukpGtJchkQ+Wf8u7OT9sWtpHo9ua8Z7uysIuvQ7pvnYMNC9uMGCRClU7WECgYEA14/V4NM7pcbWVhVLzQEXIfPbpSxlEgjzAb91Of91yMyMBJL93hikjaGmeIn1Xvl64ij148c4w7GEvZtvpCODLTa2t3Exc6WPG2u0nQ+ZSU+AOwh//aiNIitcfLmtWaoavF89FY3TW3WqEXQTnw4zx0OA71U+H0ijbdpvx+0OK9k=}")
  private String privateKeyBase64;
  //获取私钥，用于生成Jwt
  public PrivateKey getPrivateKey() {
    try {
      // 利用JDK自带的工具生成私钥
      KeyFactory kf = KeyFactory.getInstance("RSA");
      PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(Decoders.BASE64.decode(privateKeyBase64));
      return kf.generatePrivate(ks);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      Exceptions.throwException("获取Jwt私钥失败");
      return null;
    }
  }

  @Value("${base23.login.jwt.public-Key:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7wg45uUnUgPB2/uE/hpto6pSoviXi7JzS9ip6J1+CbB/bRYydF+6XnVJ5ddw5AAXSNo51beMKUEWguKg5QVzfrYPw063ojTy/36plFmTpNs7u+2fd4fvy7SrS64NRIfahp7scp6NMMXbgDrFLFXs6KJEsG7ThlA4XS4h5BS+oJ6nSnjYz6iC8PXt4wXSoyf61uWSloihQL9fO0RuAHQtHEuwuT8oHG20sg/ylSwV1/8zF4A0MdlOtbSq5UvvDWyVoOKfmEXt8V8h7ZLFAFABW2vVref5ltY0aTTqv/sM5niCa5JLB0w0beCd8FtiWljk7AF0j1W22YqtSDy2xP58IwIDAQAB}")
  private String publicKeyBase64;
  // 公钥，用于解析Jwt
  private JwtParser getJwtParser() {
    try {
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Decoders.BASE64.decode(publicKeyBase64));
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PublicKey pk = keyFactory.generatePublic(keySpec);
      return Jwts.parserBuilder().setSigningKey(pk).build();
    } catch (Exception e) {
      // 获取公钥失败
      Exceptions.throwException("获取Jwt公钥失败");
      return null;
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    privateKey = getPrivateKey();
    jwtParser = getJwtParser();
  }

  @Override
  public String createJwt(Object jwtPayload, long expiredAt) {
    //添加构成JWT的参数
    Map<String, Object> headMap = new HashMap();
    headMap.put("alg", SignatureAlgorithm.RS256.getValue());//使用RS256签名算法
    headMap.put("typ", "JWT");
    Map body = JSON.parse(JSON.stringify(jwtPayload), HashMap.class);
    String jwt = Jwts.builder()
        .setHeader(headMap)
        .setClaims(body)
        .setExpiration(new Date(expiredAt))
        .signWith(privateKey)
        .compact();
    return jwt;
  }

  @Override
  public <T> T verifyJwt(String jwt, Class<T> jwtPayloadClass) {
    if (jwt == null || jwt.equals("")) {
      return null;
    }
    Jws<Claims> jws = this.jwtParser.parseClaimsJws(jwt); // 会校验签名，校验过期时间
    Claims jwtPayload = jws.getBody();
    if (jwtPayload == null) {
      return null;
    }
    return JSON.convert(jwtPayload, jwtPayloadClass);
  }
}
