package org.base23.i18n.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.base23.i18n.mapper.SystemI18nMapper;
import org.base23.web.client.I18nDatabaseSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class I18nDatabaseSourceImpl implements I18nDatabaseSource {

  @Autowired
  private SystemI18nMapper systemI18nMapper;

  // Map<language, Map<i18nKey, i18nValue>>
  private static final Map<String, Map<String, String>> cache = new HashMap<>();

  @Override
  public String getMessage(String i18nKey, Object[] args, Locale language) {
    // 直接查缓存
    String languageTag = language.toString().replace("_", "-");
    Map<String, String> languageMap = cache.get(languageTag);
    if (languageMap == null) {
      languageMap = new HashMap<>();
      cache.put(languageTag, languageMap);
    }
    String i18nValue = languageMap.get(i18nKey);
    if (i18nValue == null) {
      // 缓存查不到。查数据库
      i18nValue = systemI18nMapper.getI18nValue(i18nKey, languageTag);
      if (i18nValue == null) {
        i18nValue = "";
      }
      languageMap.put(i18nKey, i18nValue);
    }

    if (i18nValue.equals("")) {
      // 缓存和数据库都查不到
      return null;
    }

    // 替换掉占位符
    return args == null || args.length == 0 ? i18nValue : MessageFormat.format(i18nValue, args);
  }
}
