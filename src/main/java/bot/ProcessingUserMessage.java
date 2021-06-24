package bot;

import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProcessingUserMessage {

    public enum MessageType {
        PROMO_MOBILE_TV_MENU,
        PROMO_APPLIANCES,
        DEVICE,
        SYSTEM,
        OTHER
    }

    public MessageType parseCategory(Message message){

        String messageText = message.getText();

        //если сообщение содержит команду меню Акций Мобайл ТВ
        Set<String> bufferPromoMobileTVSet = PromoInfo.getInstancePromoMobileTV().keySet();
            bufferPromoMobileTVSet.add("Акции Мобайл ТВ");
            bufferPromoMobileTVSet.add("Promo Mobile TV");
        if (bufferPromoMobileTVSet.contains(messageText)){
            return MessageType.PROMO_MOBILE_TV_MENU;
        }

        //если сообщение содержит команду меню акций БТ
        Set<String> bufferPromoAppliancesSet = PromoInfo.getInstancePromoAppliances().keySet();
            bufferPromoAppliancesSet.add("Акции Бытовая техника");
            bufferPromoAppliancesSet.add("Promo Appliances");
        if (bufferPromoAppliancesSet.contains(messageText)){
            return MessageType.PROMO_APPLIANCES;
        }

        //если сообщение содержит название устройства
        List<String> bufferCategoryDeviceList = new CategoryDevice().getList();
            bufferCategoryDeviceList.add("Характеристики устройств");
            bufferCategoryDeviceList.add("Device info");

        for (int i = 0; i < bufferCategoryDeviceList.size(); i++) {
            if (messageText.startsWith(bufferCategoryDeviceList.get(i))){
                return MessageType.DEVICE;
            }
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
