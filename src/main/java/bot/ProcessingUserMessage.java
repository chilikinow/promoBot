package bot;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;

public class ProcessingUserMessage {

    public enum MessageType {
        PROMO_MOBILE_TV_MENU,
        PROMO_APPLIANCES_MENU,
        DEVICE,
        SYSTEM,
        BONUS,
        OTHER
    }

    public MessageType parseCategory(Message message){

        String messageText = message.getText();

        //если сообщение содержит команду меню Акций Мобайл ТВ
        var promoMobileTvSet = PromoInfo.getInstancePromoMobileTV().keySet();
        Set<String> bufferPromoMobileTVSet = new HashSet<>(promoMobileTvSet);
            bufferPromoMobileTVSet.add("/promo_mobile_tv");
            bufferPromoMobileTVSet.add("Акции Мобайл ТВ");
            bufferPromoMobileTVSet.add("Promo Mobile TV");
        if (bufferPromoMobileTVSet.contains(messageText)){
            return MessageType.PROMO_MOBILE_TV_MENU;
        }

        //если сообщение содержит команду меню акций БТ
        var promoAppliancesSet = PromoInfo.getInstancePromoAppliances().keySet();
        Set<String> bufferPromoAppliancesSet = new HashSet<>(promoAppliancesSet);
            bufferPromoAppliancesSet.add("/promo_appliances");
            bufferPromoAppliancesSet.add("Акции БТ");
            bufferPromoAppliancesSet.add("Promo Appliances");
        if (bufferPromoAppliancesSet.contains(messageText)){
            return MessageType.PROMO_APPLIANCES_MENU;
        }

        //если сообщение содержит название устройства
        var categoryDeviceList = new Device().getCategoryDeviceList();
        List<String> bufferCategoryDeviceList = new ArrayList<>(categoryDeviceList);
            bufferCategoryDeviceList.add("/device_info");
            bufferCategoryDeviceList.add("Характеристики устройств".toLowerCase(Locale.ROOT));
            bufferCategoryDeviceList.add("Device info".toLowerCase(Locale.ROOT));

        String lowerMessageText = messageText.toLowerCase(Locale.ROOT);

        for (int i = 0; i < bufferCategoryDeviceList.size(); i++) {
            if (lowerMessageText.startsWith(bufferCategoryDeviceList.get(i))){
                return MessageType.DEVICE;
            }
        }


        List<String> bufferSystemMessageList = new ArrayList<>();
            bufferSystemMessageList.add(new BotData().getBotPassword());
            bufferSystemMessageList.add("/start_menu");
            bufferSystemMessageList.add("Сервис");
            bufferSystemMessageList.add("Service");
            bufferSystemMessageList.add("/service");
            bufferSystemMessageList.add("Инфо Мобайл");
            bufferSystemMessageList.add("/mobile_info");
            bufferSystemMessageList.add("Инфо ТВ");
            bufferSystemMessageList.add("/tv_info");
            bufferSystemMessageList.add("Инфо БТ");
            bufferSystemMessageList.add("/appliances_info");
        if (bufferSystemMessageList.contains(messageText)){
            return MessageType.SYSTEM;
        }

        List<String> bufferBonusMessageList = new ArrayList<>();
        bufferBonusMessageList.add("Программа Лояльности");
        bufferBonusMessageList.add("/bonus_card");
        if (bufferBonusMessageList.contains(messageText)
        || messageText.startsWith("9")
        || messageText.startsWith("20")){
            return MessageType.BONUS;
        }

        return MessageType.OTHER;
    }

}
