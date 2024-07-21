package org.base23.commons.annotation.service;

public class MaskWithStarHandler implements MaskDataHandler {

  @Override
  public Object maskData(Object data) {
    return "******";
  }
}
