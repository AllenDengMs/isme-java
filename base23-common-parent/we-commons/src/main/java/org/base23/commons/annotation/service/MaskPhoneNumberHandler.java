package org.base23.commons.annotation.service;

import java.util.Objects;

public class MaskPhoneNumberHandler implements MaskDataHandler {

  @Override
  public Object maskData(Object data) {
    if (data == null) {
      return null;
    }

    String phoneValue = data.toString();
    phoneValue = phoneValue.replace(" ", ""); // 去掉所有空格
    String region = null; // 区号
    String phone = null; // 手机号
    if (phoneValue.contains("-")) {
      // 带有区号，或者横杆
      String[] split = phoneValue.split("-");
      region = split[0];
      phone = split[1];
    } else {
      // 原来的值就不带区号
      phone = phoneValue;
    }

    int phoneLength = phone.length();
    if (phoneLength > 4) {
      // 手机号取前两位和后两位
      StringBuilder maskedPhone = new StringBuilder();
      // 区号
      if (Objects.nonNull(region)) {
        maskedPhone.append(region).append("-");
      }

      maskedPhone.append(phone.substring(0, 2)) // 前两位
          .append("***")
          .append(phone.substring(phoneLength - 2)); // 后两位
      return maskedPhone.toString();
    }
    return phoneValue;
  }

}
