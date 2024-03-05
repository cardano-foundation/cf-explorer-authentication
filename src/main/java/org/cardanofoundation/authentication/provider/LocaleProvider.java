package org.cardanofoundation.authentication.provider;

import java.util.Locale;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class LocaleProvider {

  private final ResourceBundleMessageSource messageSource;

  public String getValue(String code, Locale locale) {
    return messageSource.getMessage(code, null, locale);
  }
}
