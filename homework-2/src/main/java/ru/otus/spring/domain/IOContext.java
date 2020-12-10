package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author Александр Шабанов
 */
@AllArgsConstructor
@Getter
public class IOContext {
  private final InputStream in;
  private final PrintStream out;
}
