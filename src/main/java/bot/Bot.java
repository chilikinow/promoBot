package bot;

import commands.HelpCommand;
import commands.InfoCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class Bot extends TelegramLongPollingBot {

    private String botName;
    private String botToken;
    private String botPassword;

    private Message message;
    private int messageCounter;
    private boolean pass;

    {
        BotData botData = new BotData();

        botName = botData.getBotName();
        botToken = botData.getBotToken();
        botPassword = botData.getBotPassword();

        this.messageCounter = 1;
        this.pass = false;
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
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
            if (botPassword.equals(this.message.getText())) {
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
        String messageText = this.message.getText();

        if (messageText.equals(botPassword)){

            //Стартовое меню
            var replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
            sendMessage(replyMessage);

            return;
        }

        if (messageText.equals("/startMenu")){

            //Стартовое меню
            var replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
            sendMessage(replyMessage);

            return;
        }

        //формирование клавиатуры с перечнем акций
        if (messageText.equals("Акции Мобайл ТВ")
                || messageText.equals("Promo Mobile TV")){

            var replyMessage = Response.createTextMessageWithKeyboard
                    (this.message, "Список акций интернет магазина:\nhttps://galaxystore.ru/promo/", Response.TypeKeyboard.PROMO_MOBILE_TV);
            sendMessage(replyMessage);
            return;
        }

        //формирование клавиатуры с перечнем акций
        if (messageText.equals("Акции Бытовая техника")
                || messageText.equals("Promo Appliances")){

            var replyMessage = Response.createTextMessageWithKeyboard
                    (this.message, "Список акций интернет магазина:\nhttps://galaxystore.ru/promo/", Response.TypeKeyboard.PROMO_APPLIANCES);
            sendMessage(replyMessage);
            return;
        }

        if (messageText.equals("Характеристики устройств")
            || messageText.equals("Device info")){
            var replyMessage = Response.createTextMessage(this.message, "Введите название интересующего Вас устройства:");
            sendMessage(replyMessage);
            return;
        }

        if (messageText.equals("Помощь")
                || messageText.equals("Help")){

                var replyMessage = Response.createTextMessage(this.message, new HelpCommand().create());
                sendMessage(replyMessage);

                //Стартовое меню
                replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
                sendMessage(replyMessage);

                return;
        }

        if (messageText.equals("Инфо")
                || messageText.equals("Info")){

                var replyMessage = Response.createTextMessage(this.message, new InfoCommand().create());
                sendMessage(replyMessage);

                //Стартовое меню
                replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
                sendMessage(replyMessage);

                return;
        }
        String bufferMessageText = messageText.toLowerCase(Locale.ROOT);
        if (bufferMessageText.startsWith("tab")
                || bufferMessageText.startsWith("s")
                || bufferMessageText.startsWith("a")
                || bufferMessageText.startsWith("buds")
                || bufferMessageText.startsWith("smart")
                || bufferMessageText.startsWith("galaxy")) {
            var replyMessage = Response.createTextMessage(this.message,
                    "Всю необходимую информацию об " + message.getText() + " ты можещь найти здесь:\nhttp://uspmobile.ru/");
            sendMessage(replyMessage);

            //Стартовое меню
            replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
            sendMessage(replyMessage);

            return;
        }

        //поиск акций БТ
        Map<String, String> promoAppliancesInfoMap = PromoInfo.getInstancePromoAppliances();

        if (promoAppliancesInfoMap.keySet().contains(messageText)){
            for (Map.Entry<String, String> entry: promoAppliancesInfoMap.entrySet()){
                if (messageText.equals(entry.getKey())) {
                    var replyMessage = Response.createTextMessage(this.message, entry.getKey()+"\n\n"+entry.getValue());
                    sendMessage(replyMessage);

                    //Стартовое меню
                    replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
                    sendMessage(replyMessage);

                    return;
                }
            }
        }

        //поиск акций мобайл и тв
        Map<String, String> promoMobileTVInfoMap = PromoInfo.getInstancePromoMobileTV();

        if (promoMobileTVInfoMap.keySet().contains(messageText)){
                for (Map.Entry<String, String> entry: promoMobileTVInfoMap.entrySet()){
                    if (messageText.equals(entry.getKey())) {
                        var replyMessage = Response.createTextMessage(this.message, entry.getKey()+"\n\n"+entry.getValue());
                        sendMessage(replyMessage);

                        //Стартовое меню
                        replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
                        sendMessage(replyMessage);

                        return;
                    }
                }
            }

        //Стартовое меню
        var replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
        sendMessage(replyMessage);
    }

}
