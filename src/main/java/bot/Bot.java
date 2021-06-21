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

    public static final String DOWNLOAD_PROMO_INFO_FILE_DIRECTORY_ADDRESS;

    private static final String BOT_NAME;
    private static final String BOT_TOKEN;
    private static final String BOT_PASSWORD;

    private Message message;
    private int messageCounter;
    private boolean pass;
    Map <String, String> promoInfoMap;

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
            DOWNLOAD_PROMO_INFO_FILE_DIRECTORY_ADDRESS = botInfoProperties.getProperty("promoInfoFileAddress");
        }

        {
            promoInfoMap = PromoInfo.getInstance();
            this.messageCounter = 1;
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
        this.message = update.getMessage();

        if (this.message == null)
            return;

        if (this.message.getText().equals("/start") || !this.pass) {
            authorizationUser();
        }

        if (this.pass) {
            processingMessage();
        }
    }

    public void sendMessage(SendMessage sendMessage){

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void authorizationUser(){

        if (this.message.getText().equals("/start")){
            this.messageCounter = 1;
            this.pass = false;
        }

        if (this.messageCounter == 1) {
            var replyMessage = Response.createTextMessage(this.message,"Добро пожаловать!\nВведите пароль:\n");
            sendMessage(replyMessage);
            this.messageCounter++;
            return;
        }

        //Финальная часть авторизации
        if (this.messageCounter == 2) {
            if (BOT_PASSWORD.equals(this.message.getText())) {
                this.pass = true;
                var replyMessage = Response.createTextMessage(this.message, "Доступ Разрешен...\n\n");
                sendMessage(replyMessage);
                this.messageCounter++;
            }
            else {
                var replyMessage = Response.createTextMessage(this.message, "Пароль не от этого Бота, попробуйте другой...:\n");
                sendMessage(replyMessage);
                this.messageCounter = 2;
            }
            return;
        }
    }

    public void processingMessage(){

        User user = this.message.getFrom();
        String messageText = this.message.getText().toLowerCase(Locale.ROOT);

        //Вывод всех акций с подробным описанием механики исполнения
        if(messageText.equalsIgnoreCase("все акции")
                || messageText.equalsIgnoreCase("full promo")){

            for (Map.Entry<String, String> entry: this.promoInfoMap.entrySet()){
                StringBuilder mapToString = new StringBuilder();
                mapToString.append(entry.getKey())
                        .append("\n\n")
                        .append(entry.getValue());
                mapToString.append("\n-----------------------\n");
                var replyMessage = Response.createTextMessage(this.message, mapToString.toString());
                sendMessage(replyMessage);
            }

            var replyMessage = Response.createTextMessage(this.message,
                    "\nСписок акций интернет магазина:\nhttps://galaxystore.ru/promo/");
            sendMessage(replyMessage);

            //Стартовое меню
            replyMessage = Response.createTextMessageWithStartKeyboard(this.message, "/menu", Response.TypeKeyboard.START);
            sendMessage(replyMessage);

            return;
        }

        //формирование клавиатуры с перечнем акций
        if (messageText.equalsIgnoreCase("акции")
                || messageText.equalsIgnoreCase("promo")){

            var replyMessage = Response.createTextMessageWithStartKeyboard
                    (this.message, "Список акций интернет магазина:\nhttps://galaxystore.ru/promo/", Response.TypeKeyboard.PROMO);
            sendMessage(replyMessage);
            return;
        }

        if (messageText.equalsIgnoreCase("характеристики устройств")
            || messageText.equalsIgnoreCase("device info")){
            var replyMessage = Response.createTextMessage(this.message, "Введите название интересующего Вас устройства:");
            sendMessage(replyMessage);
            return;
        }

        if (messageText.equalsIgnoreCase("помощь")
                || messageText.equalsIgnoreCase("help")){

                var replyMessage = Response.createTextMessage(this.message, new HelpCommand().init());
                sendMessage(replyMessage);

                //Стартовое меню
                replyMessage = Response.createTextMessageWithStartKeyboard(this.message, "/menu", Response.TypeKeyboard.START);
                sendMessage(replyMessage);

                return;
        }

        if (messageText.equalsIgnoreCase("инфо")
                || messageText.equalsIgnoreCase("info")){

                var replyMessage = Response.createTextMessage(this.message, new InfoCommand().getInfo());
                sendMessage(replyMessage);

                //Стартовое меню
                replyMessage = Response.createTextMessageWithStartKeyboard(this.message, "/menu", Response.TypeKeyboard.START);
                sendMessage(replyMessage);

                return;
        }

        if (messageText.startsWith("tab")
                || messageText.startsWith("s")
                || messageText.startsWith("a")
                || messageText.startsWith("buds")
                || messageText.startsWith("smart")) {
            var replyMessage = Response.createTextMessage(this.message,
                    "Всю необходимую информацию об " + message.getText() + " ты можещь найти здесь:\nhttp://uspmobile.ru/");
            sendMessage(replyMessage);

            //Стартовое меню
            replyMessage = Response.createTextMessageWithStartKeyboard(this.message, "/menu", Response.TypeKeyboard.START);
            sendMessage(replyMessage);

            return;
        }

        for (int i = 0; i < PromoInfo.getPromoKeys().size(); i++) {
            if (messageText.equalsIgnoreCase(PromoInfo.getPromoKeys().get(i))) {
                for (Map.Entry<String, String> entry: promoInfoMap.entrySet()){
                    if (messageText.equalsIgnoreCase(entry.getKey())) {
                        var replyMessage = Response.createTextMessage(this.message, entry.getKey()+"\n\n"+entry.getValue());
                        sendMessage(replyMessage);
                    }
                }


                //Стартовое меню
                var replyMessage = Response.createTextMessageWithStartKeyboard(this.message, "/menu", Response.TypeKeyboard.START);
                sendMessage(replyMessage);

                return;
            }
        }

        if (messageText.equalsIgnoreCase("/menu")){

            //Стартовое меню
            var replyMessage = Response.createTextMessageWithStartKeyboard(this.message, "/menu", Response.TypeKeyboard.START);
            sendMessage(replyMessage);

            return;
        }

        //Стартовое меню
        var replyMessage = Response.createTextMessageWithStartKeyboard(this.message, "/menu", Response.TypeKeyboard.START);
        sendMessage(replyMessage);
    }

}
