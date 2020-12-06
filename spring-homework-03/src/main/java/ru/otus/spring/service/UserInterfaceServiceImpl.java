package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class UserInterfaceServiceImpl implements UserInterfaceService {
    private final PrintStream out;
    private final InputStream in;

    @Override
    public void textOut(String text) {
        out.println(text);
    }

    @Override
    public String textIn() {
        Scanner scanner = new Scanner(in);
        return scanner.nextLine();
    }
}
