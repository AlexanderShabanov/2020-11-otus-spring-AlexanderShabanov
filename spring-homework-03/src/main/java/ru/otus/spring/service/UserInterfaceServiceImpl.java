package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.utils.IOContext;

import java.util.Scanner;

@Service

public class UserInterfaceServiceImpl implements UserInterfaceService {
    private final IOContext ioContext;
    private final Scanner scanner;

    public UserInterfaceServiceImpl(IOContext ioContext) {
        this.ioContext = ioContext;
        this.scanner = new Scanner(ioContext.getIn());
    }

    @Override
    public void textOut(String text) {
        ioContext.getOut().println(text);
    }

    @Override
    public void textOut(String textFmt, Object... args) {
        ioContext.getOut().printf(textFmt, args);
    }

    @Override
    public void textOutLn(String textFmt, Object... args) {
        ioContext.getOut().printf(String.join(textFmt,"\n"), args);
    }

    @Override
    public String textIn() {
        return scanner.nextLine();
    }
}
