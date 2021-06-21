package bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class Response {

    public static SendMessage createTextMessage(Message message, String text){

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());

        sendMessage.setText(text);

        return sendMessage;
    }

    public static SendMessage createTextMessageWithStartKeyboard(Message message, String text, TypeKeyboard typeKeyboard){

        SendMessage sendMessage = createTextMessage(message, text);

        if (typeKeyboard == TypeKeyboard.START) {
            sendMessage.setReplyMarkup(new MenuKeyboard().getStartMenu());
        }
        if (typeKeyboard == TypeKeyboard.PROMO){
            sendMessage.setReplyMarkup(new MenuKeyboard().getPromoMenu());
        }

        return sendMessage;
    }

    public enum TypeKeyboard{
        START,
        PROMO
    }
}
