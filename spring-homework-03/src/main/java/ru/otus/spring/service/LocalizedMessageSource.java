package ru.otus.spring.service;

import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;

/**
 * @author Александр Шабанов
 */
public interface LocalizedMessageSource {
   String getMessage(String var1, @Nullable Object[] var2) throws NoSuchMessageException;
   String getMessage(String var1) throws NoSuchMessageException;
}
