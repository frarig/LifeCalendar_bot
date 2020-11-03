package ru.bot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Работает с шаблоном "ответных сообщений" message.properties
 */

@Service
public class LocaleMessageService {
    private final Locale locale;
    private final MessageSource messageSource;

    public LocaleMessageService(@Value("${locale.ru}")String languageLocale, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(languageLocale);
    }

    public String getMessage(String message) {
        return messageSource.getMessage(message, null, locale);
    }
}
