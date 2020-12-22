package ru.otus.spring.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.utils.IOContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис пользовательского интерфейса должен")
class UserInterfaceServiceImplTest {
    private static final String OUTPUT_TEST_STRING = "тестовая строка";
    private static final String INPUT_TEST_LINES = "4\n6,2\n";
    private static final String INPUT_TEST_STRING = "4";

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(outputStream);
    private final InputStream in = new ByteArrayInputStream(INPUT_TEST_LINES.getBytes());

    private UserInterfaceService userInterfaceService;

    @BeforeEach
    void setUp()
    {
        userInterfaceService = new UserInterfaceServiceImpl(new IOContext(in, out));
    }
    @AfterEach
    @SneakyThrows
    void afterAll(){
        in.close();
        out.close();
        outputStream.close();
    }
    @DisplayName("корректно выводить данные в переданный поток")
    @Test
    void shouldCorrectReturnOtputText() {
        assertDoesNotThrow(()->{userInterfaceService.textOut(OUTPUT_TEST_STRING);});
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