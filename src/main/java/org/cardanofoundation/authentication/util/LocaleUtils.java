package org.cardanofoundation.authentication.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public class LocaleUtils {

  public static Locale resolveLocale(HttpServletRequest request) {
    List<Locale> localeList = Arrays.asList(new Locale("en"), new Locale("fr"));
    String lang = request.getHeader("Accept-Language");
    return lang == null || lang.isEmpty()
        ? Locale.getDefault()
        : Locale.lookup(Locale.LanguageRange.parse(lang), localeList);
  }
}
