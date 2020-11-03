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
        return new SendMessage(chatId, localeMessageService.getMessage(message));
    }
}
