package ru.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bot.calendar.Calendar;
import ru.bot.calendar.DateOfBirth;
import ru.bot.data.PersonData;
import ru.bot.data.UserDataCache;
import ru.bot.handler.BotState;
import ru.bot.handler.BotStateContext;
import ru.bot.service.LocaleMessageService;
import ru.bot.service.ReplyMessageService;
import ru.bot.service.UserDataService;

import static ru.bot.handler.BotStateContext.CALENDAR;

public class MyTestBot extends TelegramWebhookBot {

    private final BotStateContext botStateContext;
    private final UserDataCache userDataCache;
    private final Calendar calendar;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private ReplyMessageService messageService;

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
        int userId = update.getMessage().getFrom().getId();
        long chatId = update.getMessage().getChatId();
        String locale = update.getMessage().getFrom().getLanguageCode();

        if ((userDataCache.getUsersCurrentBotState(userId).equals(BotState.PRINT_CALENDAR)
                || update.getMessage().getText().startsWith(CALENDAR))) {

            PersonData personData = userDataService.getPersonProfileData(chatId);

            if (personData != null) {
                DateOfBirth dateOfBirth = DateOfBirth.getInstance();

                dateOfBirth.setBirthDay(personData.getDateOfBirth());
                calendar.setYear(Integer.parseInt(personData.getAge()));

                try {
                    calendarOutput(personData.getChatId(), locale, personData);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                userDataCache.setUsersCurrentBotState(userId, BotState.PRINT_CALENDAR);
            }
        }
        return botStateContext.handlerUpdate(update);
    }

    private void calendarOutput(long chatId, String locale, PersonData personData) throws TelegramApiException {
        double num = calendar.getYear() / (double) 25;
        int quantityMessage = (int) Math.ceil(num);
        calendar.getCompletedCalendar();
        String years = "year";

        for (int i = 0; i < quantityMessage; i++) {
            execute(new SendMessage(chatId, calendar.getListCalendar().get(i)));
        }

        int year = Integer.parseInt(personData.getAge());
        if (year < 1 && locale.equals("en")) {
            years = "years";
        } else  if (year > 1 && year < 5 && locale.equals("ru")) {
            years = "years";
        } else if (year > 4 && locale.equals("ru")) {
            years = "over5years";
        }

        execute(messageService.getReplyMessage(chatId, personData.getDateOfBirth(), "dateOfBirth",
                personData.getAge(), years, locale));

        calendar.cleanCalendar();
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
}
