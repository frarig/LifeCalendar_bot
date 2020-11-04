package ru.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

/**
 * Работает с шаблоном "ответных сообщений" message.properties
 */

@Service
public class LocaleMessageService {
    private Locale locale;
    private final MessageSource messageSource;
    @Autowired
    private Environment environment;

    public LocaleMessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String message) {
        return messageSource.getMessage(message, null, locale);
    }

    public String getMessage(String message, String language) {
        if (language.equals("ru")) {
            locale = Locale.forLanguageTag(Objects.requireNonNull(environment.getProperty("locale.ru")));
        } else {
            locale = Locale.forLanguageTag(Objects.requireNonNull(environment.getProperty("locale.en")));
        }
        return messageSource.getMessage(message, null, locale);
    }
}
