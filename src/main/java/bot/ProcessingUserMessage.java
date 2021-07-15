package bot;

import commandSystem.InfoCommand;
import commandSystem.ServiceCommand;
import org.apache.commons.io.FilenameUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class ProcessingUserMessage {

    public Object searchAnswer(Message message) {

        String messageText = message.getText();

//////////////////////////// Запрос акций по Мобильной технике и ТВ

        if (messageText.equals("Акции Мобайл ТВ")
                || messageText.equals("Promo Mobile TV")
                || messageText.equals("/promo_mobile_tv")) {

            SendMessage replyMessage = Response.createTextMessageWithKeyboard
                    (message, "Список акций интернет магазина:\nhttps://galaxystore.ru/promo/",
                            Response.TypeKeyboard.PROMO_MOBILE_TV);

            return replyMessage;
        }

        //Поиск подробной информации по запрашиваемой акции

        Map<String, String> promoMobileTVInfoMap = PromoInfo.getInstancePromoMobileTV();

        if (promoMobileTVInfoMap.containsKey(messageText)) {
            for (Map.Entry<String, String> entry : promoMobileTVInfoMap.entrySet()) {
                if (messageText.equals(entry.getKey())) {
                    StringBuilder replyText = new StringBuilder();
                    replyText.append(entry.getKey() + "\n\n" + entry.getValue());

                    //При запросе данной акции добавляется ссылка на таблицу с уточнением условий по каждой модели

                    if ("Скидки по акции ценопад".equals(messageText)) {
                        replyText.append("\n\nПодробности:\n\n");
                        replyText.append(new BotData().getReadPromoInfoFileUrl());
                    }

                    SendMessage replyMessage = Response.createTextMessageWithKeyboard(message
                            ,replyText.append("\n\n" + "/start_menu").toString()
                            ,Response.TypeKeyboard.START);

                    return replyMessage;
                }
            }
        }

//////////////////////////// Запрос акций по Бытовой технике

        if (messageText.equals("Акции БТ")
                || messageText.equals("Promo Appliances")
                || messageText.equals("/promo_appliances")) {

            SendMessage replyMessage = Response.createTextMessageWithKeyboard
                    (message, "Список акций интернет магазина:\nhttps://galaxystore.ru/promo/",
                            Response.TypeKeyboard.PROMO_APPLIANCES);

            return replyMessage;
        }

        //Поиск подробной информации по запрашиваемой акции

        Map<String, String> promoAppliancesInfoMap = PromoInfo.getInstancePromoAppliances();

        if (promoAppliancesInfoMap.containsKey(messageText)) {
            for (Map.Entry<String, String> entry : promoAppliancesInfoMap.entrySet()) {
                if (messageText.equals(entry.getKey())) {
                    SendMessage replyMessage = Response.createTextMessageWithKeyboard(message
                            , entry.getKey() + "\n\n" + entry.getValue() + "\n\n" + "/start_menu"
                            , Response.TypeKeyboard.START);

                    return replyMessage;
                }
            }
        }

//////////////////////////// Поиск подробной информации об устройстве

        if (messageText.equals("Характеристики устройств")
                || messageText.equals("Device info")
                || messageText.equals("/device_info")) {
            SendMessage replyMessage = Response.createTextMessage(message,
                    "Введите название устройства:");

            return replyMessage;
        }

        //поиск устройства

        var categoryDeviceList = new Device().getCategoryDeviceList();
        List<String> bufferCategoryDeviceList = new ArrayList<>(categoryDeviceList);
        for (int i = 0; i < bufferCategoryDeviceList.size(); i++) {
            String deviceMessageText = message.getText()
                    .toLowerCase(Locale.ROOT)
                    .replaceAll(" ", "")
                    .replaceAll("galaxy", "")
                    .replaceAll("samsung", "")
                    .replaceAll("-", "")
                    .replaceAll("_", "")
                    .replaceAll("plus", "\\+")
                    .replace("+", "\\+");

            if (deviceMessageText.startsWith(bufferCategoryDeviceList.get(i))){ //перевели всю строку запроса в нижний регистр

                if (deviceMessageText.length() == 1){
                    SendMessage replyMessage = Response.createTextMessage(message
                            , "Уточните модель:");

                    return replyMessage;
                }

                String separator = File.separator;
                Set<Path> resultDeviceInfoList;
                resultDeviceInfoList = new Device().findInfo(message,
                        "src" + separator + "main" + separator + "resources" + separator + "dataBaseProducts");

                if (!resultDeviceInfoList.isEmpty()) {
                    List<SendPhoto> replyPhotoList = new ArrayList<>();
                    for (Path resultDeviseInfo : resultDeviceInfoList) {
                        SendPhoto replyPhoto = Response.createPhotoMessage(message,
                                FilenameUtils.removeExtension(resultDeviseInfo.getFileName().toString())
                                        + "\n\nТехнические характеристики и USP:\nhttp://uspmobile.ru/",
                                resultDeviseInfo.getParent().resolve(resultDeviseInfo.getFileName()).toString());
                        replyPhotoList.add(replyPhoto);
                    }
                    return replyPhotoList;
                } else {
                    SendMessage replyMessage = Response.createTextMessageWithKeyboard(message,
                            "Устройство не найдено!\n\nСписок доступных для поиска устройств\n" +
                                    "находится в разделе ИНФО стартового меню." + "\n\n" + "/start_menu"
                            , Response.TypeKeyboard.START);
                    return replyMessage;
                }
            }
        }

        if (messageText.equals(new BotData().getBotPassword()) || messageText.equals("/start_menu")) {

            //Стартовое меню
            SendMessage replyMessage = Response.createTextMessageWithKeyboard(message,
                    "/start_menu", Response.TypeKeyboard.START);

            return replyMessage;
        }

//////////////////////////// Запрос списка устройств для поиска

        if (messageText.equals("Инфо Мобайл")
                || messageText.equals("Mobile info")
                || messageText.equals("/mobile_info")) {

            String heading = "Список доступных для поиска устройств:\n\nМобильная электроника:\n\n";
            String separator = File.separator;
            String directory = "src" + separator + "main" + separator + "resources" + separator + "dataBaseProducts" + separator + "mobile";
            String ending = "\nСписок устройств в процессе пополнения...";
            String replyTextMessage = new InfoCommand().create(heading, directory, ending);

            SendMessage replyMessage = Response.createTextMessageWithKeyboard(message
                    , replyTextMessage + "\n\n" + "/start_menu"
                    , Response.TypeKeyboard.START);

            return replyMessage;
        }

        if (messageText.equals("Инфо ТВ")
                || messageText.equals("TV info")
                || messageText.equals("/tv_info")) {

            String heading = "Список доступных для поиска устройств:\n\nТелевизоры:\n\n";
            String separator = File.separator;
            String directory = "src" + separator + "main" + separator + "resources" + separator + "dataBaseProducts" + separator + "tv";
            String ending = "\nСписок устройств в процессе пополнения...";
            String replyTextMessage = new InfoCommand().create(heading, directory, ending);

            SendMessage replyMessage = Response.createTextMessageWithKeyboard(message
                    , replyTextMessage + "\n\n" + "/start_menu"
                    , Response.TypeKeyboard.START);

            return replyMessage;
        }

        if (messageText.equals("Инфо БТ")
                || messageText.equals("Appliances info")
                || messageText.equals("/appliances_info")) {

            String heading = "Список доступных для поиска устройств:\n\nБытовая техника:\n\n";
            String separator = File.separator;
            String directory = "src" + separator + "main" + separator + "resources" + separator + "dataBaseProducts" + separator + "appliances";
            String ending = "\nСписок устройств в процессе пополнения...";
            String replyTextMessage = new InfoCommand().create(heading, directory, ending);

            SendMessage replyMessage = Response.createTextMessageWithKeyboard(message
                    , replyTextMessage + "\n\n" + "/start_menu"
                    , Response.TypeKeyboard.START);

            return replyMessage;
        }

//////////////////////////// Запрос информации о Сервисе

        if (messageText.equals("Сервис")
                || messageText.equals("Service")
                || messageText.equals("/service")) {

            SendMessage replyMessage = Response.createTextMessageWithKeyboard(message
                    , new ServiceCommand().create()+ "\n\n" + "/start_menu"
                    ,Response.TypeKeyboard.START);

            return replyMessage;
        }

//////////////////////////// Поиск Бонусной карты

        if (messageText.equals("Программа Лояльности")
                || messageText.equals("/bonus_card")) {

            SendMessage replyMessage = Response.createTextMessage(message,
                    "Введите номер телефона или бонусной карты:\n\n");

            return replyMessage;
        }

        //Убираем из номера телефона все лишние символы и цифры
        String bonusMessageText = messageText.replaceAll(" ", "")
                .replaceAll("\\+", "")
                .replaceAll("-", "")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "");
        if (bonusMessageText.startsWith("8"))
            bonusMessageText = bonusMessageText.replaceFirst("8", "");
        if (bonusMessageText.startsWith("7"))
            bonusMessageText = bonusMessageText.replaceFirst("7", "");

        //Если ввели номер телефона
        if (bonusMessageText.startsWith("9") && bonusMessageText.length() == 10) {

            SendMessage replyMessage = null;

            String findBonusInfo = new Bonus().getInfo(bonusMessageText);

            if (!findBonusInfo.isEmpty()) {
                replyMessage = Response.createTextMessageWithKeyboard(message
                        , new Bonus().getInfo(bonusMessageText) + "\n\n" + "/start_menu"
                        , Response.TypeKeyboard.START);
            } else {
                replyMessage = Response.createTextMessageWithKeyboard(message
                        , "Номера телефона нет в базе данных." + "\n\n" + "/start_menu"
                        , Response.TypeKeyboard.START);
            }

            return replyMessage;
        }

        //Если ввели номер карты
        if (bonusMessageText.startsWith("20") && bonusMessageText.length() == 10){

            SendMessage replyMessage = null;

            String findBonusInfo = new Bonus().getInfo(bonusMessageText);

            if (!findBonusInfo.isEmpty()) {
                replyMessage = Response.createTextMessageWithKeyboard(message
                        , new Bonus().getInfo(bonusMessageText) + "\n\n" + "/start_menu"
                        , Response.TypeKeyboard.START);
            } else {
                replyMessage = Response.createTextMessageWithKeyboard(message
                        , "Карты лояльности нет в базе данных." + "\n\n" + "/start_menu"
                        , Response.TypeKeyboard.START);
            }

            return replyMessage;
        }

        //если не найдено ни одного совпадения
        //Стартовое меню
        SendMessage replyMessage = Response.createTextMessageWithKeyboard(message
                ,"Команда не найдена!" + "\n\n" + "/start_menu"
                ,Response.TypeKeyboard.START);

        return replyMessage;
    }
}
