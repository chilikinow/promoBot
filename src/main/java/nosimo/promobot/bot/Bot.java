package nosimo.promobot.bot;

import nosimo.promobot.bot.botData.BotData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private String botName;
    private String botToken;
    private String botPassword;

    private Message message;
    private String userName;
    private Long chatId;
    private String messageText;
    private boolean pass;

    {
        botName = BotData.botName;
        botToken = BotData.botToken;
        botPassword = BotData.botPassword;

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

        if (update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            this.userName = callbackQuery.getFrom().getUserName();
            this.chatId = callbackQuery.getFrom().getId();
            this.messageText = callbackQuery.getData();
        }else {
            this.message = update.getMessage();
            this.userName = this.message.getFrom().getUserName();
            this.chatId = this.message.getFrom().getId();
            this.messageText = this.message.getText();
        }

        this.pass = true; //new Authorization().pass(this.userName);

        if (this.pass) {
            Object replyMessage =  new ProcessingUserMessage().searchAnswer(this.chatId, this.userName, this.messageText);
            sendReply(replyMessage);
        } else {
            var replyMessage = Response.createTextMessage(chatId
                    ,"У Вас нет доступа к данной системе."
            + "\n\n"
            + "Для получения доступа просим отправить Ваше Имя Пользователя (@UserName), Вашему КД.");
            sendReply(replyMessage);
        }
    }

    public void sendReply(Object reply){

        if (reply instanceof List){

           for (Object replyObject: (List)reply){
               sendReply(replyObject);
           }
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
}
