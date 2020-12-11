package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Александр Шабанов
 */
@Service
@RequiredArgsConstructor
public class LocalizedUserInterfaceServiceServiceImpl implements LocalizedUserInterfaceService {
  private final UserInterfaceService userInterfaceService;
  private final LocalizedMessageSource messageSource;
  @Override
  public void messageOut(String messageKey) {
    userInterfaceService.textOut(messageSource.getMessage(messageKey));
  }

  @Override
  public void messageOutLn(String textFmt) {
    userInterfaceService.textOutLn(textFmt);
  }

  @Override
  public void textOut(String text) {
    userInterfaceService.textOut(text);
  }

  @Override
  public void textOut(String textFmt, Object... args) {
    userInterfaceService.textOut(textFmt, args);
  }

  @Override
  public void textOutLn(String textFmt, Object... args) {
    userInterfaceService.textOutLn(textFmt, args);
  }

  @Override
  public String textIn() {
    return userInterfaceService.textIn();
  }
}
