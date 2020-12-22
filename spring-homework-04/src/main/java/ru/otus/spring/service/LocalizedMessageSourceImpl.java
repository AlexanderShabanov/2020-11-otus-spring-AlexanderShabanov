package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.CommonPropertiesConfig;

/**
 * @author Александр Шабанов
 */
@Component
@RequiredArgsConstructor
public class LocalizedMessageSourceImpl implements LocalizedMessageSource {
  private final MessageSource messageSource;
  private final CommonPropertiesConfig commonPropertiesConfig;
  @Override
  public String getMessage(String message, Object ...objects) throws NoSuchMessageException {
    return messageSource.getMessage(message,objects,commonPropertiesConfig.getLocale());
  }
  @Override
  public String getMessage(String message) throws NoSuchMessageException {
    return messageSource.getMessage(message,null,commonPropertiesConfig.getLocale());
  }
}
