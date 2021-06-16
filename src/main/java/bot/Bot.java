package bot;

import commands.HelpCommand;
import commands.InfoCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Bot extends TelegramLongPollingBot {

    public static final String PROMO_INFO_FILE_ADDRESS;

    private static final String BOT_NAME;
    private static final String BOT_TOKEN;
    private static final String BOT_PASSWORD;
    private Message message;
    private int messageCounter;
    private boolean pass;
    private ReplyKeyboardMarkup replyKeyboardMarkup;

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
            PROMO_INFO_FILE_ADDRESS = botInfoProperties.getProperty("promoInfoFileAddress");
        }

        {
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
                this.messageCounter++;
                this.pass = true;
                sendMessage("Доступ Разрешен...\n\n");
            }
            else {
                sendMessage("Я Вас не знаю, всего Доброго...\n");
                this.messageCounter = 1;
            }
            return;
        }
    }

    public void processingMessage(){ //разобраться
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        replyKeyboardMarkup.setResizeKeyboard(true);//размер клавиатуры адаптируется под количество клавиш
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрыть клавиатуру после использования
        replyKeyboardMarkup.setSelective(false);//персонолизация клавиатуры

        if (this.messageCounter == 3) {
            keyboard.clear();
            keyboardFirstRow.add("Акции");
            keyboardFirstRow.add("Справка");
            keyboardFirstRow.add("Помощь");
            keyboardFirstRow.add("Инфо");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            this.messageCounter++;
            sendMessageWIthKeyboard("Меню:");
        }

        if (this.message.getText().equals(BOT_PASSWORD))
            return;


        User user = this.message.getFrom();
        String messageText = this.message.getText().toLowerCase(Locale.ROOT);

        switch (messageText) {
            case "Помощь":
                sendMessage(new HelpCommand().init());
                return;
            case "Инфо":
                sendMessage(new InfoCommand().init());
                return;
        }

        //обработка информации об акциях
        if (messageText.equalsIgnoreCase("акции") || messageText.equalsIgnoreCase("promo")){
            Map <String, String> promoInfoMap = Promo.getInstance();

//            keyboard.clear();
//            for (Map.Entry<String, String> entry: promoInfoMap.entrySet()) {
//                keyboardFirstRow.add(entry.getKey());
//            }
//            keyboard.add(keyboardFirstRow);
//            replyKeyboardMarkup.setKeyboard(keyboard);
//            sendMessageWIthKeyboard("Акции");

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
        }else {
            sendMessage("Всю необходимую информацию об "
                    + message.getText()
                    + " ты можещь найти здесь:\nhttp://uspmobile.ru/");
        }

    }

}
