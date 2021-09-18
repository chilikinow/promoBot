package nosimo.promobot.bot;

import nosimo.promobot.bot.authorization.AuthorizationWithUsername;
import nosimo.promobot.bot.botData.BotData;
import nosimo.promobot.commandSystem.InfoCommand;
import nosimo.promobot.commandSystem.ServiceCommand;
import nosimo.promobot.commandSystem.StartCommand;
import org.apache.commons.io.FilenameUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class ProcessingUserMessage {

    public String startButtonInfo;
    private SendMessage notPassReplyMessage;
    private List<Object> replyMessageList;
    private SendMessage replyMessage;

    {
        startButtonInfo = "Стартовое меню: /start_menu";
        replyMessageList = new ArrayList<>();
    }

    public Object searchAnswer(Long chatId, String userName, String messageText) {

        notPassReplyMessage = Response.createTextMessageWithKeyboardRMK(chatId,
                "У Вас нет доступа к данной системе."
                        + "\n\n"
                        + "Для получения доступа необходимо обратится к Управляющему Вашего магазина.",
                Response.TypeKeyboard.START);

        // Вывод списка пользователей, делавших запрос

        System.out.println(
                new SimpleDateFormat("dd.MM.yyyy hh:mm")
                        .format(Calendar.getInstance().getTime())
                        + " "
                        + "@" + userName
                        + ": "
                        + messageText);

        if (messageText.equals("/start_menu")) {

            //Стартовое меню
            replyMessage = Response.createTextMessageWithKeyboardRMK(chatId,
                    startButtonInfo, Response.TypeKeyboard.START);

            return replyMessage;
        }

//////////////////////////// Поиск подробной информации об устройстве

        if (messageText.equals("Характеристики устройств")
                || messageText.equals("Device info")
                || messageText.equals("/device_info")) {
            SendMessage replyMessage = Response.createTextMessage(chatId,
                    "Введите название устройства:");

            return replyMessage;
        }

        //поиск устройства
        var categoryDeviceList = new Device().getCategoryDeviceList();
        List<String> bufferCategoryDeviceList = new ArrayList<>(categoryDeviceList);
        String deviceMessageText = messageText
                .toLowerCase(Locale.ROOT)
                .replaceAll(" ", "")
                .replaceAll("galaxy", "")
                .replaceAll("samsung", "")
                .replaceAll("-", "")
                .replaceAll("_", "")
                .replaceAll("wifi", "")
                .replaceAll("lte", "")
                .replaceAll("plus", "\\+")
                .replace("+", "\\+");

        if (deviceMessageText.length() < 20)
        for (int i = 0; i < bufferCategoryDeviceList.size(); i++) {

            if (deviceMessageText.startsWith(bufferCategoryDeviceList.get(i))){

                if (deviceMessageText.length() == 1){
                    SendMessage replyMessage = Response.createTextMessage(chatId
                            , "Уточните модель:");

                    return replyMessage;
                }

                Set<Path> resultDeviceInfoList;

                Path directory = BotData.outResources.resolve("dataBaseProducts");

                resultDeviceInfoList = new Device().findInfo(messageText, directory);

                if (!resultDeviceInfoList.isEmpty()) {

                    for (Path resultDeviseInfo : resultDeviceInfoList) {
                        SendPhoto replyPhoto = Response.createPhotoMessage(chatId,
                                FilenameUtils.removeExtension(resultDeviseInfo.getFileName().toString()),
                                resultDeviseInfo.getParent().resolve(resultDeviseInfo.getFileName()).toString());
                        replyMessageList.add(replyPhoto);
                    }
                    replyMessageList.add(Response.createTextMessageWithKeyboardRMK(chatId,
                            startButtonInfo, Response.TypeKeyboard.START));
                    return replyMessageList;

                } else {
                    replyMessage = Response.createTextMessageWithKeyboardRMK(chatId,
                            "Устройство не найдено!\n\nСписок доступных для поиска устройств\n" +
                                    "находится в разделе ИНФО стартового меню." + "\n\n" + startButtonInfo
                            , Response.TypeKeyboard.START);
                    return replyMessage;
                }
            }
        }

//////////////////////////// Запрос списка устройств для поиска

        if (messageText.equals("Инфо Мобайл")
                || messageText.equals("Mobile info")
                || messageText.equals("/mobile_info")) {

            String heading = "Список доступных для поиска устройств:\n\nМобильная электроника:\n\n";

            Path directory = BotData.outResources.resolve("dataBaseProducts").resolve("mobile");

            String ending = "\nСписок устройств в процессе пополнения...";
            String replyTextMessage = new InfoCommand().create(heading, directory, ending);

            replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                    , replyTextMessage
                            + "\n\n"
                            + "Технические характеристики и USP:\nhttp://uspmobile.ru/"
                            + "\n\n"
                            + startButtonInfo
                    , Response.TypeKeyboard.START);

            return replyMessage;
        }

        if (messageText.equals("Инфо ТВ")
                || messageText.equals("TV info")
                || messageText.equals("/tv_info")) {

            String heading = "Список доступных для поиска устройств:\n\nТелевизоры:\n\n";

            Path directory = BotData.outResources.resolve("dataBaseProducts").resolve("tv");

            String ending = "\nСписок устройств в процессе пополнения...";
            String replyTextMessage = new InfoCommand().create(heading, directory, ending);

            replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                    , replyTextMessage + "\n\n" + startButtonInfo
                    , Response.TypeKeyboard.START);

            return replyMessage;
        }

        if (messageText.equals("Инфо БТ")
                || messageText.equals("Appliances info")
                || messageText.equals("/appliances_info")) {

            String heading = "Список доступных для поиска устройств:\n\nБытовая техника:\n\n";

            Path directory = BotData.outResources.resolve("dataBaseProducts").resolve("appliances");

            String ending = "\nСписок устройств в процессе пополнения...";
            String replyTextMessage = new InfoCommand().create(heading, directory, ending);

            replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                    , replyTextMessage + "\n\n" + startButtonInfo
                    , Response.TypeKeyboard.START);

            return replyMessage;
        }

//////////////////////////// Запрос информации о Сервисе

        if (messageText.equals("Сервис")
                || messageText.equals("Service")
                || messageText.equals("/service")) {

            replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                    , new ServiceCommand().create()+ "\n\n" + startButtonInfo
                    ,Response.TypeKeyboard.START);

            return replyMessage;
        }

//////////////////////////// Поиск Бонусной карты

        if (messageText.equals("Программа Лояльности")
                || messageText.equals("/bonus_card")) {

            if (!AuthorizationWithUsername.pass(userName)){
                System.out.println("not pass to bonus system");
                return notPassReplyMessage;
            }

            replyMessageList.add(Response.createTextMessage(chatId,
                    "Активация бонусной карты:"
                    + "\n\n"
                    + "https://galaxystore.ru/about/bonus/?utm_source=shop&utm_medium=qr&utm_campaign=activate#card-activate"));
            replyMessageList.add(Response.createTextMessage(chatId, "Введите номер телефона или бонусной карты:"));

            return replyMessageList;
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

            if (!AuthorizationWithUsername.pass(userName)){
                System.out.println("not pass to bonus system");
                return notPassReplyMessage;
            }

            String findBonusInfo = new Bonus().getInfoPhoneNumber(bonusMessageText);

            if (!findBonusInfo.isEmpty()){
                replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                        , findBonusInfo + "\n\n" + startButtonInfo
                        , Response.TypeKeyboard.START);
            } else {
                replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                        , "Номера телефона нет в базе данных." + "\n\n" + startButtonInfo
                        , Response.TypeKeyboard.START);
            }

            return replyMessage;
        }

        //Если ввели номер карты
        if ((bonusMessageText.startsWith("20") || bonusMessageText.startsWith("10"))
                && (bonusMessageText.length() == 10) || (bonusMessageText.length() == 11)){

            if (!AuthorizationWithUsername.pass(userName)){
                System.out.println("not pass to bonus system");
                return notPassReplyMessage;
            }

            String findBonusInfo = new Bonus().getInfoCardNumber(bonusMessageText);

            if (!findBonusInfo.isEmpty()) {
                replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                        , findBonusInfo + "\n\n" + startButtonInfo
                        , Response.TypeKeyboard.START);
            } else {
                replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                        , "Карты лояльности нет в базе данных." + "\n\n" + startButtonInfo
                        , Response.TypeKeyboard.START);
            }

            return replyMessage;
        }

        /////////////////////////// Обновление файла с акциями

        if (messageText.equals("/promo_update")) {

            PromoInfo.updateWorkbook();

            replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                    , "База Акций обновлена!" + "\n\n" + startButtonInfo
                    ,Response.TypeKeyboard.START);

            return replyMessage;
        }

        /////////////////////////// Первый запуск

        if (messageText.equals("/start")){

            replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                    ,new StartCommand().create() + "\n\n" + startButtonInfo
                    ,Response.TypeKeyboard.START);

            return replyMessage;
        }

        //////////////////////////// Запрос акций по Мобильной технике и ТВ

        if (messageText.equals("Акции Мобайл ТВ")
                || messageText.equals("Promo Mobile TV")
                || messageText.equals("/promo_mobile_tv")) {

            replyMessage = Response.createTextMessageWithKeyboardRMK
                    (chatId, "Список акций интернет магазина:\n" +
                                    "https://galaxystore.ru/promo/",
                            Response.TypeKeyboard.PROMO_MOBILE_TV);

            return replyMessage;
        }

        if (messageText.equals("Акции БТ")
                || messageText.equals("Promo Appliances")
                || messageText.equals("/promo_appliances")) {

            replyMessage = Response.createTextMessageWithKeyboardRMK
                    (chatId, "Список акций интернет магазина:\n" +
                                    "https://galaxystore.ru/promo/",
                            Response.TypeKeyboard.PROMO_APPLIANCES);

            return replyMessage;
        }

        //Поиск подробной информации по запрашиваемой акции

        Map<String, String> promoMobileTVInfoMap = PromoInfo.getInstancePromoMobileTV();

        if (promoMobileTVInfoMap.containsKey(messageText)) {
            for (Map.Entry<String, String> entry : promoMobileTVInfoMap.entrySet()) {
                if (entry.getKey().startsWith(messageText)) {
                    StringBuilder replyText = new StringBuilder();
                    replyText.append(entry.getKey() + "\n\n" + entry.getValue());

                    replyText.append("\n\nПодробности:\n\n");
                    replyText.append(BotData.readPromoInfoFileUrl);

                    if (replyText.length() > 3500){
                       while (replyText.length() > 3500){
                           replyMessageList.add(Response.createTextMessage(chatId, replyText.substring(0, 3500)));
                           replyText = new StringBuilder(replyText.substring(3500));
                       }

                        replyMessageList.add(Response.createTextMessageWithKeyboardRMK(chatId
                                ,replyText.append("\n\n" + startButtonInfo).toString()
                                ,Response.TypeKeyboard.START));

                        return replyMessageList;

                    }else{
                        replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                                ,replyText.append("\n\n" + startButtonInfo).toString()
                                ,Response.TypeKeyboard.START);

                        return replyMessage;
                    }
                }
            }
        }

        //////////////////////////// Запрос акций по Бытовой технике

        //Поиск подробной информации по запрашиваемой акции

        Map<String, String> promoAppliancesInfoMap = PromoInfo.getInstancePromoAppliances();

        if (promoAppliancesInfoMap.containsKey(messageText)) {
            for (Map.Entry<String, String> entry : promoAppliancesInfoMap.entrySet()) {
                if (entry.getKey().startsWith(messageText)) {

                    StringBuilder replyText = new StringBuilder();
                    replyText.append(entry.getKey() + "\n\n" + entry.getValue());

                    replyText.append("\n\nПодробности:\n\n");
                    replyText.append(BotData.readPromoInfoFileUrl);

                    if (replyText.length() > 3500){
                        while (replyText.length() > 3500){
                            replyMessageList.add(Response.createTextMessage(chatId, replyText.substring(0, 3500)));
                            replyText = new StringBuilder(replyText.substring(3500));
                        }

                        replyMessageList.add(Response.createTextMessageWithKeyboardRMK(chatId
                                ,replyText.append("\n\n" + startButtonInfo).toString()
                                ,Response.TypeKeyboard.START));

                        return replyMessageList;

                    }else{
                        replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                                ,replyText.append("\n\n" + startButtonInfo).toString()
                                ,Response.TypeKeyboard.START);

                        return replyMessage;
                    }
                }
            }
        }

        //если не найдено ни одного совпадения
        //Стартовое меню
        replyMessage = Response.createTextMessageWithKeyboardRMK(chatId
                ,"Команда не найдена!" + "\n\n" + startButtonInfo
                ,Response.TypeKeyboard.START);

        return replyMessage;
    }
}
