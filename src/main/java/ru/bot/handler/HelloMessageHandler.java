package ru.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bot.data.UserDataCache;
import ru.bot.service.ReplyMessageService;

/**
 * Приветствует пользователя
 */

@Component
public class HelloMessageHandler implements InputMessageHandler {

    private final ReplyMessageService messageService;
    private final UserDataCache userDataCache;

    public HelloMessageHandler(ReplyMessageService messageService, UserDataCache userDataCache) {
        this.messageService = messageService;
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage handler(Message message) {
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();

        userDataCache.setUsersCurrentBotState(userId, BotState.ASK_DATE_OF_BIRTH);
        return messageService.getReplyMessage(chatId, "hello");
    }

    @Override
    public BotState getState() {
        return BotState.GREETING;
    }
}