package bot;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public class ProcessingUserMessage {

    MessageType messageType;

    public enum MessageType {
        START_MENU,
        PROMO_MOBILE_TV_MENU,
        PROMO_APPLIANCES,
        DEVICE,
        SYSTEM,
        OTHER
    }

    public MessageType parseCategory(Message message){

        String messageText = message.getText();

        //если сообщение содержит команду стартового меню
        if (new StartMenu().getList().contains(messageText)){
           return MessageType.START_MENU;
        }

        //если сообщение содержит команду меню Акций Мобайл ТВ
        if (PromoInfo.getInstancePromoMobileTV().keySet().contains(messageText)){
            return MessageType.PROMO_MOBILE_TV_MENU;
        }

        //если сообщение содержит команду меню акций БТ
        if (PromoInfo.getInstancePromoAppliances().keySet().contains(messageText)){
            return MessageType.PROMO_APPLIANCES;
        }

        //если сообщение содержит название устройства
        if (new CategoryDevice().getList().contains(messageText)){
            return MessageType.DEVICE;
        }

        if (new BotData().getBotPassword().equals(messageText) || ("/startMenu").equals(messageText)){
            return MessageType.SYSTEM;
        }

        return MessageType.OTHER;
    }

}
