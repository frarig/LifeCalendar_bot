package ru.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.bot.MyTestBot;
import ru.bot.calendar.Calendar;
import ru.bot.data.UserDataCache;
import ru.bot.handler.BotStateContext;

@Configuration
public class BotConfig {
    @Value("${telegramBot.userName}")
    private String botUserName;
    @Value("${telegramBot.botToken}")
    private String botToken;
    @Value("${telegramBot.webHookPath}")
    private String webHookPath;

    private final UserDataCache userDataCache;
    private final Calendar calendar;

    public BotConfig(UserDataCache userDataCache, Calendar calendar) {
        this.userDataCache = userDataCache;
        this.calendar = calendar;
    }

    @Bean
    public MyTestBot myTestBot(BotStateContext botStateContext) {

        MyTestBot testBot = new MyTestBot(botStateContext, userDataCache, calendar);

        testBot.setBotUserName(botUserName);
        testBot.setBotToken(botToken);
        testBot.setWebHookPath(webHookPath);

        return testBot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    public String getBotUserName() {
        return botUserName;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getWebHookPath() {
        return webHookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }
}
