package bot;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        var bufferPromoMobileTVSet = PromoInfo.getInstancePromoMobileTV().keySet();
            bufferPromoMobileTVSet.add("Акции Мобайл ТВ");
            bufferPromoMobileTVSet.add("Promo Mobile TV");
        if (bufferPromoMobileTVSet.contains(messageText)){
            return MessageType.PROMO_MOBILE_TV_MENU;
        }

        //если сообщение содержит команду меню акций БТ
        var bufferPromoAppliancesSet = PromoInfo.getInstancePromoAppliances().keySet();
            bufferPromoAppliancesSet.add("Акции Бытовая техника");
            bufferPromoAppliancesSet.add("Promo Appliances");
        if (bufferPromoAppliancesSet.contains(messageText)){
            return MessageType.PROMO_APPLIANCES;
        }

        //если сообщение содержит название устройства
        var bufferCategoryDeviceList = new CategoryDevice().getList();
            bufferCategoryDeviceList.add("Характеристики устройств");
            bufferCategoryDeviceList.add("Device info");
        if (bufferCategoryDeviceList.contains(messageText)){
            return MessageType.DEVICE;
        }

        List<String> bufferSystemMessageList = new ArrayList<>();
            bufferSystemMessageList.add(new BotData().getBotPassword());
            bufferSystemMessageList.add("/startMenu");
            bufferSystemMessageList.add("Помощь");
            bufferSystemMessageList.add("Help");
            bufferSystemMessageList.add("Инфо");
            bufferSystemMessageList.add("Info");
        if (bufferSystemMessageList.contains(messageText)){
            return MessageType.SYSTEM;
        }

        return MessageType.OTHER;
    }

}
