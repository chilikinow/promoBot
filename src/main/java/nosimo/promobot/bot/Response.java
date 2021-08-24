package nosimo.promobot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;

public class Response {

    public static SendMessage createTextMessage(Long chatId, String text){

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.enableMarkdown(false);
        sendMessage.setText(text);

        return sendMessage;
    }

    public static SendPhoto createPhotoMessage(Long chatId, String path){

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
//        sendPhoto.setReplyToMessageId(message.getMessageId());
        sendPhoto.setPhoto(new InputFile(new File(path)));

        return sendPhoto;
    }

    public static SendPhoto createPhotoMessage(Long chatId, String text, String path){

        SendPhoto sendPhoto = createPhotoMessage(chatId, path);
        sendPhoto.setCaption(text);

        return sendPhoto;
    }

    public static SendMessage createTextMessageWithKeyboardIKM(Long chatId, String text, String buttonName, String callbackData){

        SendMessage sendMessage = createTextMessage(chatId, text);
        sendMessage.setReplyMarkup(new MenuKeyboard().getMenuIKM(buttonName, callbackData));

        return sendMessage;
    }

    public static SendMessage createTextMessageWithKeyboardRMK(Long chatId, String text, TypeKeyboard typeKeyboard){

        SendMessage sendMessage = createTextMessage(chatId, text);

        if (typeKeyboard == TypeKeyboard.START) {
            sendMessage.setReplyMarkup(new MenuKeyboard().getStartMenu());
            return sendMessage;
        }
        if (typeKeyboard == TypeKeyboard.PROMO_MOBILE_TV){
            sendMessage.setReplyMarkup(new MenuKeyboard().getMobileTVPromoMenu());
            return sendMessage;
        }
        if (typeKeyboard == TypeKeyboard.PROMO_APPLIANCES){
            sendMessage.setReplyMarkup(new MenuKeyboard().getAppliancesMenu());
            return sendMessage;
        }

        return sendMessage;
    }

    public enum TypeKeyboard{
        START,
        PROMO_APPLIANCES,
        PROMO_MOBILE_TV

    }
}
