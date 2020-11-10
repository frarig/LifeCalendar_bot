package ru.bot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Обработчик сообщений
 */

public interface InputMessageHandler {

    SendMessage handler(Message message);

    BotState getState();
}
