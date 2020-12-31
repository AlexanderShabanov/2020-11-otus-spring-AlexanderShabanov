package ru.otus.spring.ui;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * @author Александр Шабанов
 */
@Component
public class CustomPromptProvider implements PromptProvider {

  private String userName;

  @Override
  public AttributedString getPrompt() {
    if (userName != null && !userName.isEmpty()) {
      return new AttributedString(userName + ":>",
          AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    } else {
      return new AttributedString("user-unknown:>",
          AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
    }
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}