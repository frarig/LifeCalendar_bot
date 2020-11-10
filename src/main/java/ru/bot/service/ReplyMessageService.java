package ru.bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Формирует готовые ответные сообщения в чат.
 */

@Service
public class ReplyMessageService {
    private final LocaleMessageService localeMessageService;

    public ReplyMessageService(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }

    public SendMessage getReplyMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(localeMessageService.getMessage(message));

        sendMessage.setReplyMarkup(new MainMenuService().getMainMenuKeyboard());

        return sendMessage;
    }

    public SendMessage getReplyMessage(long chatId, String message, String languageLocale) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(localeMessageService.getMessage(message, languageLocale));

        sendMessage.setReplyMarkup(new MainMenuService().getMainMenuKeyboard());

        return sendMessage;
    }

    public SendMessage getReplyMessage(long chatId, String firstMessage, String secondMessage,
                                       String thirdMessage, String fourthMessage, String languageLocale) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(firstMessage + localeMessageService.getMessage(secondMessage, languageLocale)
                + thirdMessage + localeMessageService.getMessage(fourthMessage, languageLocale));

        sendMessage.setReplyMarkup(new MainMenuService().getMainMenuKeyboard());

        return sendMessage;
    }
}
