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

    public static SendMessage createTextMessageWithKeyboard(Message message, String text, TypeKeyboard typeKeyboard){

        SendMessage sendMessage = createTextMessage(message, text);

        if (typeKeyboard == TypeKeyboard.START) {
            sendMessage.setReplyMarkup(new MenuKeyboard().getStartMenu());
        }
        if (typeKeyboard == TypeKeyboard.PROMO_MOBILE_TV){
            sendMessage.setReplyMarkup(new MenuKeyboard().getMobileTVPromoMenu());
        }
        if (typeKeyboard == TypeKeyboard.PROMO_APPLIANCES){
            sendMessage.setReplyMarkup(new MenuKeyboard().getAppliancesMenu());
        }

        return sendMessage;
    }

    public enum TypeKeyboard{
        START,
        PROMO_APPLIANCES,
        PROMO_MOBILE_TV

    }
}
