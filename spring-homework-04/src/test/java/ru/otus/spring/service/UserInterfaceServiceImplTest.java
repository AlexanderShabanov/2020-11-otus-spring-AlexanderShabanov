package ru.otus.spring.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.utils.IOContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Сервис пользовательского интерфейса должен")

public class UserInterfaceServiceImplTest {
  @Configuration
  static class TestConfiguration {
    @Bean UserInterfaceService userInterfaceService(){
      return new UserInterfaceServiceImpl(new IOContext(in, out));
    }
  }
  private static final String OUTPUT_TEST_STRING = "тестовая строка";
  private static final String INPUT_TEST_LINES = "4\n6,2\n";
  private static final String INPUT_TEST_STRING = "4";

  private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private static final PrintStream out = new PrintStream(outputStream);
  private static final InputStream in = new ByteArrayInputStream(INPUT_TEST_LINES.getBytes());

  @Autowired
  private UserInterfaceService userInterfaceService;

  @AfterEach
  @SneakyThrows
  void afterAll() {
    in.close();
    out.close();
    outputStream.close();
  }

  @DisplayName("корректно выводить данные в переданный поток")
  @Test
  void shouldCorrectReturnOtputText() {
    assertDoesNotThrow(() -> {
      userInterfaceService.textOut(OUTPUT_TEST_STRING);
    });
    String outString = outputStream.toString().trim();
    assertNotNull(outString);
    assertEquals(OUTPUT_TEST_STRING, outString);
  }

  @DisplayName("корректно возвращать введенную строку")
  @Test
  void shouldCorrectReturnInputText() {
    String stringReturned = userInterfaceService.textIn();
    assertNotNull(stringReturned);
    assertEquals(INPUT_TEST_STRING, stringReturned);
  }

}