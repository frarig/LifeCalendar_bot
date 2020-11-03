package ru.bot;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bot.calendar.Calendar;
import ru.bot.data.UserDataCache;
import ru.bot.handler.BotState;
import ru.bot.handler.BotStateContext;
import ru.bot.service.ReplyMessageService;

public class MyTestBot extends TelegramWebhookBot {

    private final BotStateContext botStateContext;
    private final UserDataCache userDataCache;
    private ReplyMessageService messageService;
    private final Calendar calendar;

    private String botUserName;
    private String botToken;
    private String webHookPath;

    public MyTestBot(BotStateContext botStateContext, UserDataCache userDataCache, Calendar calendar) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.calendar = calendar;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        int id = update.getMessage().getFrom().getId();
        long chatId = update.getMessage().getChatId();

        if (userDataCache.getUsersCurrentBotState(id).equals(BotState.PRINT_CALENDAR)) {
            double num = calendar.getYear() / (double) 25;
            int quantityMessage = (int) Math.ceil(num);
            calendar.getCalendar();

            for (int i = 0; i < quantityMessage; i++) {
                try {
                    execute(new SendMessage(chatId, calendar.getListOfCalendar().get(i)));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            calendar.cleanCalendar();
        }
        return botStateContext.handlerUpdate(update);
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public ReplyMessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(ReplyMessageService messageService) {
        this.messageService = messageService;
    }
}
