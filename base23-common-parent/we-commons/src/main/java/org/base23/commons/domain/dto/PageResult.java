package org.base23.commons.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T> {

  private int total;
  private List<T> pageData = new ArrayList();

  public PageResult() {
  }

  public PageResult(int total, List<T> pageData) {
    this.total = total;
    this.pageData = pageData;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<T> getPageData() {
    return pageData;
  }

  public void setPageData(List<T> pageData) {
    this.pageData = pageData;
  }
}
