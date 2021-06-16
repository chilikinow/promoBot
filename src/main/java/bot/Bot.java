package bot;

import commands.HelpCommand;
import commands.InfoCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Bot extends TelegramLongPollingBot {

    private static final String BOT_NAME;
    private static final String BOT_TOKEN;
    private static final String BOT_PASSWORD;
    private Message message;
    private int messageCounter;
    private boolean pass;

    static {
            Path botInfoPropertiesFile = Paths.get
                    ("src/main/resources/botInfo.properties");
            Properties botInfoProperties = new Properties();
            try {
                botInfoProperties.load(new FileReader(botInfoPropertiesFile.toFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            BOT_NAME = botInfoProperties.getProperty("botUsername");
            BOT_TOKEN = botInfoProperties.getProperty("botToken");
            BOT_PASSWORD = botInfoProperties.getProperty("botPassword");
        }

        {
            this.messageCounter = 0;
            this.pass = false;
        }

    @Override
    public String getBotUsername() {
        return this.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return this.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        message = update.getMessage();

        if (message == null)
            return;

        authorizationUser();

        if (this.pass) {
            processingMessage();
        }
    }

    public void sendMessage(String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void authorizationUser(){

        if (message.getText().equals("/start")){
            this.messageCounter = 0;
            this.pass = false;
        }

        if (this.messageCounter == 0) {
            sendMessage("Добро пожаловать!\nВведите пароль:\n");
            this.messageCounter++;
            return;
        }

        if (this.messageCounter == 1) {
            this.messageCounter++;
            if (BOT_PASSWORD.equals(message.getText())) {
                sendMessage("Доступ Разрешен...\n");
                this.pass = true;
            }
            else {
                sendMessage("Я Вас не знаю, всего Доброго...\n");
                this.messageCounter = 0;
            }
            return;
        }

    }

    public void processingMessage(){ //разобраться

        if (message.getText().equals(BOT_PASSWORD))
            return;

        User user = message.getFrom();
        String messageText = message.getText().toLowerCase(Locale.ROOT);

        switch (messageText) {
            case "/help":
                sendMessage(new HelpCommand().init());
                return;
            case "/info":
                sendMessage(new InfoCommand().init());
                return;
        }

        //обработка информации об акциях
        if (messageText.equalsIgnoreCase("акции") || messageText.equalsIgnoreCase("promo")){
            Map <String, String> promoInfoMap = Promo.getInstance();

            StringBuilder mapToString;
            for (Map.Entry<String, String> entry: promoInfoMap.entrySet()){
                mapToString = new StringBuilder();
                mapToString.append(entry.getKey())
                        .append("\n\n")
                        .append(entry.getValue());
                sendMessage(mapToString.toString());
            }

            sendMessage("Список акций интернет магазина:\n" +
                    "https://galaxystore.ru/promo/");
            return;
        }
        if (!(messageText.startsWith("tab")
                || messageText.startsWith("s")
                || messageText.startsWith("a")
                || messageText.startsWith("buds")
                || messageText.startsWith("smart"))) {
            sendMessage("У Компании Samsung нет такого продукта!");
            return;
        }

                sendMessage("Всю необходимую информацию об "
                        + message.getText()
                        +" ты можещь найти здесь:\nhttp://uspmobile.ru/");

    }

}
