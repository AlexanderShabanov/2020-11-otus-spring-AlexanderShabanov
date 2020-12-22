package ru.otus.spring.service;

/**
 * @author Александр Шабанов
 */
public interface LocalizedUserInterfaceService {
  void messageOut(String messageKey);
  void messageOutLn(String messageKey);

  void textOut(String text);
  void textOut(String textFmt, Object... args);

  void textOutLn(String textFmt, Object... args);
  String textIn();
}
