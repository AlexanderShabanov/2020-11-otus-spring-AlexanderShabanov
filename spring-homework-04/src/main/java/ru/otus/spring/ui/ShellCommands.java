package ru.otus.spring.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.QuestionService;

/**
 * @author Александр Шабанов
 */
@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
  private final CustomPromptProvider customPromptProvider;
  private final QuestionService service;
  private String userName;

  @ShellMethod(key = {"l", "login"}, value = "Login as user")
  public String login(@ShellOption({"username", "u"}) String userName) {
    this.userName = userName;
    customPromptProvider.setUserName(userName);
    String message = String.format("Hello, %s", userName);
    return message;
  }

  @ShellMethod(key = {"t", "test"}, value = "Begin test")
  public void test() {
    service.run();
  }

  @ShellMethodAvailability("test")
  public Availability testAvailability() {
    return (userName == null || userName.isEmpty()) ? Availability.unavailable("you are not authorized!") : Availability.available();
  }


}
