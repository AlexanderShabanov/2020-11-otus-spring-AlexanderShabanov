package ru.otus.spring.service;

public interface UserInterfaceService {
    void textOut(String text);
    void textOut(String textFmt, Object... args);
    String textIn();
}
