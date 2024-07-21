package org.base23.commons.domain.dto;

public class PageQueryParam<T> {

  private Integer pageNo; // 当前页
  private Integer pageSize; // 每页数据条数

  public Integer getPageNo() {
    if (pageNo == null || pageNo <= 0){
      return 1;
    }
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getPageSize() {
    if (pageSize == null || pageSize <= 0){
      return 10;
    }

    if (pageSize > 5000) {
      return 5000; // 防止一次查太多，搞跨数据库
    }
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
