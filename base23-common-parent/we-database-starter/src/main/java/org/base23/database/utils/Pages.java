package org.base23.database.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.base23.commons.domain.dto.PageQueryParam;
import org.base23.commons.domain.dto.PageResult;

public class Pages {

  public static <T> Page<T> of(PageQueryParam<T> queryBuilder) {
    return new Page<>(queryBuilder.getPageNo(), queryBuilder.getPageSize());
  }


  public static <T> PageResult<T> result(Page<T> page) {
    return new PageResult<>((int)page.getTotal(), page.getRecords());
  }

  public static <T> PageResult<T> result(long total, List<T> list) {
    return new PageResult<>((int)total, list);
  }

}
