package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

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

            Object replyMessage =  new ProcessingUserMessage().searchAnswer(this.message);
            sendReply(replyMessage);
        }
    }

    public void sendReply(Object reply){

        if(reply instanceof List){
           List<SendPhoto> replyPhotoList = new ArrayList<>();
           replyPhotoList = (List<SendPhoto>) reply;
           for (SendPhoto replyPhoto: replyPhotoList){
               sendReply(replyPhoto);
           }
            var replyMessage = Response.createTextMessageWithKeyboard(message
                    ,"/start_menu"
                    , Response.TypeKeyboard.START);
            sendReply(replyMessage);

            return;
        }

        try {

            if (reply instanceof SendMessage)
                execute((SendMessage) reply);
            if (reply instanceof SendPhoto)
                execute((SendPhoto) reply);

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
            var replyMessage = Response.createTextMessage(this.message,
                    "Приветствую!\n\n" +
                        "Бот позволяет оперативно узнать актуальную информацию о проходящих в компании "+
                        "маркетинговых акциях, а так же узнать подробные характеристики запрашиваемой техники.\n\n" +
                        "[ Акции Мобайл ТВ ]\n\n" +
                        "перечень актуальных акций категорий:\n\n" +
                        "- Смартфоны\n" +
                        "- Планшеты\n" +
                        "- Носимые устройства\n\n" +
                        "[ Акции Бытовая техника ]\n\n" +
                        "перечень актуальных акций категорий:\n\n" +
                        "- Стиральные машины\n" +
                        "- Холодильники\n" +
                        "- Пылесосы\n" +
                        "- Микроволновки\n" +
                        "- Духовые шкафы\n" +
                        "- Варочные панели\n\n" +
                        "[ Характеристики устройств ]\n\n" +
                        "поиск технических характеристик и USP\n\n" +
                        "[ Инфо ]\n\n" +
                        "список устройств, доступных для быстрого поиска (постоянно пополняется)\n\n" +
                        "[ Сервис ]\n\n" +
                        "справочная информация Сервисных служб Samsung\n\n" +
                        "Вызов Стартового меню: /start_menu");
            sendReply(replyMessage);
            replyMessage = Response.createTextMessage(this.message, "Введите пароль:");
            sendReply(replyMessage);

            this.messageCounter++;

            PromoInfo.getInstancePromoMobileTV();
            PromoInfo.getInstancePromoAppliances();

            return;
        }

        //Финальная часть авторизации
        if (this.messageCounter == 2) {
            if (botPassword.equals(this.message.getText())) {
                this.pass = true;
                var replyMessage = Response.createTextMessage(this.message,
                        "Доступ Разрешен...\n\n");
                sendReply(replyMessage);
                this.messageCounter++;
            }
            else {
                var replyMessage = Response.createTextMessage(this.message,
                        "Пароль не от этого Бота, попробуйте другой...:\n");
                sendReply(replyMessage);
                this.messageCounter = 2;
            }
            return;
        }
    }
}
