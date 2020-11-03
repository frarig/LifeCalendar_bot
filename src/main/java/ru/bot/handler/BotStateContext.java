package ru.bot.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bot.data.UserDataCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {

    private static final Logger log = LoggerFactory.getLogger(BotStateContext.class);
    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();
    private final UserDataCache userDataCache;

    public BotStateContext(List<InputMessageHandler> inputMessageHandlers, UserDataCache userDataCache) {
        inputMessageHandlers.forEach(handler -> this.messageHandlers.put(handler.getState(), handler));
        this.userDataCache = userDataCache;
    }

    public SendMessage handlerUpdate(Update update) {
        Message message = update.getMessage();

        log.info("New message from User: {}, chatId: {}, with text: {}", message.getFrom().getUserName(),
                message.getChatId(), message.getText());
        return inputMessageHandler(message);
    }

    private SendMessage inputMessageHandler(Message message) {
        String inputMessage = message.getText();
        int userId = message.getFrom().getId();
        BotState botState;

        if (inputMessage.equals("/start") || inputMessage.equals("Hello")) {
            botState = BotState.GREETING;
        } else {
            botState = userDataCache.getUsersCurrentBotState(userId);
        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        InputMessageHandler currentMessageHandler = messageHandlers.get(botState);
        return currentMessageHandler.handler(message);
    }
}
