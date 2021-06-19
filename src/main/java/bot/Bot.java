package bot;

import commands.HelpCommand;
import commands.InfoCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
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
    private ReplyKeyboardMarkup replyKeyboardMarkup;
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
            this.replyKeyboardMarkup = new ReplyKeyboardMarkup();
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

    public void sendMessageWIthKeyboard(String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(this.message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setReplyMarkup(this.replyKeyboardMarkup);
        try {
            sendMessage.setText(text);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(this.message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
        try {
            sendMessage.setText(text);
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
            sendMessage("Добро пожаловать!\nВведите пароль:\n");
            this.messageCounter++;
            return;
        }

        //Финальная часть авторизации
        if (this.messageCounter == 2) {
            if (BOT_PASSWORD.equals(this.message.getText())) {
                this.pass = true;
                sendMessage("Доступ Разрешен...\n\n");
                this.messageCounter++;
            }
            else {
                sendMessage("Пароль не от этого Бота, попробуйте другой...:\n");
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
                mapToString.append("\n------------------\n");
                sendMessage(mapToString.toString());
            }

            sendMessage("\nСписок акций интернет магазина:\n"
                    +"https://galaxystore.ru/promo/");

            //Стартовое меню
            replyKeyboardMarkup = new MenuKeyboard().getFirstMenu();
            sendMessageWIthKeyboard("/menu");

            return;
        }

        //формирование клавиатуры с перечнем акций
        if (messageText.equalsIgnoreCase("акции")
                || messageText.equalsIgnoreCase("promo")){

            replyKeyboardMarkup = new MenuKeyboard().getPromoMenu();
            sendMessageWIthKeyboard("Список акций интернет магазина:\n"
                                     + "https://galaxystore.ru/promo/");
            return;
        }

        if (messageText.equalsIgnoreCase("характеристики устройств")
            || messageText.equalsIgnoreCase("device info")){
            sendMessage("Введите название интересующего Вас устройства:");
            return;
        }

        if (messageText.equalsIgnoreCase("помощь")
                || messageText.equalsIgnoreCase("help")){

                sendMessage(new HelpCommand().init());

                //Стартовое меню
                replyKeyboardMarkup = new MenuKeyboard().getFirstMenu();
                sendMessageWIthKeyboard("/menu");

                return;
        }

        if (messageText.equalsIgnoreCase("инфо")
                || messageText.equalsIgnoreCase("info")){

                sendMessage(new InfoCommand().getInfo());

                //Стартовое меню
                replyKeyboardMarkup = new MenuKeyboard().getFirstMenu();
                sendMessageWIthKeyboard("/menu");

                return;
        }

        if (messageText.startsWith("tab")
                || messageText.startsWith("s")
                || messageText.startsWith("a")
                || messageText.startsWith("buds")
                || messageText.startsWith("smart")) {
            sendMessage("Всю необходимую информацию об "
                    + message.getText()
                    + " ты можещь найти здесь:\nhttp://uspmobile.ru/");

            //Стартовое меню
            replyKeyboardMarkup = new MenuKeyboard().getFirstMenu();
            sendMessageWIthKeyboard("/menu");

            return;
        }
        promoInfoMap = PromoInfo.getInstance();
        for (int i = 0; i < PromoInfo.getPromoKeys().size(); i++) {
            if (messageText.equalsIgnoreCase(PromoInfo.getPromoKeys().get(i))) {
                for (Map.Entry<String, String> entry: promoInfoMap.entrySet()){
                    if (messageText.equalsIgnoreCase(entry.getKey()))
                        sendMessage(entry.getKey()+"\n\n"+entry.getValue());
                }

                //Стартовое меню
                replyKeyboardMarkup = new MenuKeyboard().getFirstMenu();
                sendMessageWIthKeyboard("/menu");

                return;
            }
        }

        if(messageText.equalsIgnoreCase("/menu")){

            //Стартовое меню
            replyKeyboardMarkup = new MenuKeyboard().getFirstMenu();
            sendMessageWIthKeyboard("/menu");

            return;
        }

        //Стартовое меню
        replyKeyboardMarkup = new MenuKeyboard().getFirstMenu();
        sendMessageWIthKeyboard("/menu");
    }

}
