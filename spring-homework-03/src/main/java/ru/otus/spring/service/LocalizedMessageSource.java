package ru.otus.spring.service;

import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;

/**
 * @author Александр Шабанов
 */
public interface LocalizedMessageSource {
   String getMessage(String message, Object ... objects) throws NoSuchMessageException;
   String getMessage(String message) throws NoSuchMessageException;
}
