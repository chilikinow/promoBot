package bot;

import commandSystem.ServiceInfoCommand;
import commandSystem.InfoCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.file.Path;
import java.util.*;

public class Bot extends TelegramLongPollingBot {

    private String botName;
    private String botToken;
    private String botPassword;

    private Message message;
    private int messageCounter;
    private boolean pass;

    {
        BotData botData = new BotData();

        botName = botData.getBotName();
        botToken = botData.getBotToken();
        botPassword = botData.getBotPassword();

        this.messageCounter = 1;
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
        this.message = update.getMessage();

        if (this.message == null)
            return;

        if (this.message.getText().equals("/start") || !this.pass) {
            authorizationUser();
        }

        if (this.pass) {

            ProcessingUserMessage processingUM = new ProcessingUserMessage();

            ProcessingUserMessage.MessageType messageType = processingUM.parseCategory(this.message);

            this.processingTypeMessage(this.message, messageType);

//            processingMessage();
        }
    }

    public void sendReply(Object reply){

            try {

                if (reply instanceof SendMessage)
                    execute((SendMessage) reply);
                if (reply instanceof SendPhoto)
                    execute((SendPhoto) reply);

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
    }

    public void authorizationUser(){

        if (this.message.getText().equals("/start")){
            this.messageCounter = 1;
            this.pass = false;
        }

        if (this.messageCounter == 1) {
            var replyMessage = Response.createTextMessage(this.message,
                    "Приветствую!\n\n" +
                            "Бот позволяет оперативно узнать актуальную информацию о проходящих в компании "+
                            "маркетинговых акциях, а так же узнать подробные характеристики запрашиваемой техники.\n\n" +
                            "[ Акции Мобайл ТВ ]\n\n" +
                            "перечень актуальных акций категорий:\n\n" +
                            "- Смартфоны\n" +
                            "- Планшеты\n" +
                            "- Носимые устройства\n\n" +
                            "[ Акции Бытовая техника ]\n\n" +
                            "перечень актуальных акций категорий:\n\n" +
                            "- Стиральные машины\n" +
                            "- Холодильники\n" +
                            "- Пылесосы\n" +
                            "- Микроволновки\n" +
                            "- Духовые шкафы\n" +
                            "- Варочные панели\n\n" +
                            "[ Характеристики устройств ]\n\n" +
                            "поиск технических характеристик и USP\n\n" +
                            "[ Инфо ]\n\n" +
                            "список устройств, доступных для быстрого поиска (постоянно пополняется)\n\n" +
                            "[ Сервис ]\n\n" +
                            "справочная информация Сервисных служб Samsung\n\n" +
                            "Вызов Стартового меню: /startMenu\n"
                            + "\n\nВведите пароль:");
            sendReply(replyMessage);
            this.messageCounter++;

            PromoInfo.getInstancePromoMobileTV();
            PromoInfo.getInstancePromoAppliances();

            return;
        }

        //Финальная часть авторизации
        if (this.messageCounter == 2) {
            if (botPassword.equals(this.message.getText())) {
                this.pass = true;
                var replyMessage = Response.createTextMessage(this.message, "Доступ Разрешен...\n\n");
                sendReply(replyMessage);
                this.messageCounter++;
            }
            else {
                var replyMessage = Response.createTextMessage(this.message, "Пароль не от этого Бота, попробуйте другой...:\n");
                sendReply(replyMessage);
                this.messageCounter = 2;
            }
            return;
        }
    }

    public void processingTypeMessage(Message message, ProcessingUserMessage.MessageType messageType){

        String messageText = message.getText();

        if (messageType == ProcessingUserMessage.MessageType.PROMO_MOBILE_TV_MENU){

            //формирование клавиатуры с перечнем акций
            if (messageText.equals("Акции Мобайл ТВ")
                    || messageText.equals("Promo Mobile TV")){

                var replyMessage = Response.createTextMessageWithKeyboard
                        (message, "Список акций интернет магазина:\nhttps://galaxystore.ru/promo/", Response.TypeKeyboard.PROMO_MOBILE_TV);
                sendReply(replyMessage);
                return;
            }

            //поиск акций мобайл и тв
            Map<String, String> promoMobileTVInfoMap = PromoInfo.getInstancePromoMobileTV();

            if (promoMobileTVInfoMap.keySet().contains(messageText)){
                for (Map.Entry<String, String> entry: promoMobileTVInfoMap.entrySet()){
                    if (messageText.equals(entry.getKey())) {
                        var replyMessage = Response.createTextMessage(message, entry.getKey()+"\n\n"+entry.getValue());
                        sendReply(replyMessage);

                        //Стартовое меню
                        replyMessage = Response.createTextMessageWithKeyboard(message, "/startMenu", Response.TypeKeyboard.START);
                        sendReply(replyMessage);

                        return;
                    }
                }
            }

            return;
        }


        if (messageType == ProcessingUserMessage.MessageType.PROMO_APPLIANCES_MENU){

            //формирование клавиатуры с перечнем акций
            if (messageText.equals("Акции Бытовая техника")
                    || messageText.equals("Promo Appliances")){

                var replyMessage = Response.createTextMessageWithKeyboard
                        (this.message, "Список акций интернет магазина:\nhttps://galaxystore.ru/promo/", Response.TypeKeyboard.PROMO_APPLIANCES);
                sendReply(replyMessage);
                return;
            }

            //поиск акций БТ
            Map<String, String> promoAppliancesInfoMap = PromoInfo.getInstancePromoAppliances();

            if (promoAppliancesInfoMap.keySet().contains(messageText)){
                for (Map.Entry<String, String> entry: promoAppliancesInfoMap.entrySet()){
                    if (messageText.equals(entry.getKey())) {
                        var replyMessage = Response.createTextMessage(this.message, entry.getKey()+"\n\n"+entry.getValue());
                        sendReply(replyMessage);

                        //Стартовое меню
                        replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
                        sendReply(replyMessage);

                        return;
                    }
                }
            }

            return;
        }


        if (messageType == ProcessingUserMessage.MessageType.DEVICE){

            if (messageText.equals("Характеристики устройств")
                    || messageText.equals("Device info")){
                var replyMessage = Response.createTextMessage(this.message, "Введите название интересующего Вас устройства:");
                sendReply(replyMessage);
                return;
            }

            //поиск устройства

            messageText = messageText.toLowerCase(Locale.ROOT);//перевели всю строку запроса в нижний регистр
            var categoryDeviceList = new Device().getCategoryDeviceList();
            List<String> bufferCategoryDeviceList = new ArrayList<>(categoryDeviceList);

            for (int i = 0; i < bufferCategoryDeviceList.size(); i++) {
                if (messageText.startsWith(bufferCategoryDeviceList.get(i))){

                    List<Path> resultDeviceInfoList = new ArrayList<>();
                    resultDeviceInfoList = new Device().findInfo(this.message, "src/main/resources/dataBaseProducts");

                    if (!resultDeviceInfoList.isEmpty()) {
                        for (Path resultDeviseInfo : resultDeviceInfoList) {
                            SendPhoto replyPhoto = Response.createPhotoMessage(this.message, "Технические характеристики и USP:\nhttp://uspmobile.ru/"
                                    , resultDeviseInfo.getParent().resolve(resultDeviseInfo.getFileName()).toString());
                            sendReply(replyPhoto);
                        }
                    }else{
                        var replyMessage = Response.createTextMessage(this.message,
                                "Устройство не найдено!\n\nСписок доступных для поиска устройств\n" +
                                        "находится в разделе ИНФО стартового меню.");
                        sendReply(replyMessage);
                    }

                    //Стартовое меню
                    var replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
                    sendReply(replyMessage);

                    return;
                }
            }

            return;
        }

        if (messageType == ProcessingUserMessage.MessageType.SYSTEM){

            if (messageText.equals(botPassword)){

                //Стартовое меню
                var replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
                sendReply(replyMessage);

                return;
            }

            if (messageText.equals("/startMenu")){

                //Стартовое меню
                var replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
                sendReply(replyMessage);

                return;
            }

            if (messageText.equals("Сервис")
                    || messageText.equals("Service")){

                var replyMessage = Response.createTextMessage(this.message, new ServiceInfoCommand().create());
                sendReply(replyMessage);

                //Стартовое меню
                replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
                sendReply(replyMessage);

                return;
            }

            if (messageText.equals("Инфо")
                    || messageText.equals("Info")){

                var replyMessage = Response.createTextMessage(this.message, new InfoCommand().create());
                sendReply(replyMessage);

                //Стартовое меню
                replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
                sendReply(replyMessage);

                return;
            }

            return;
        }

        if (messageType == ProcessingUserMessage.MessageType.OTHER) {

            //Стартовое меню
            var replyMessage = Response.createTextMessageWithKeyboard(this.message,
                    "Команда не найдена!\n\n/startMenu", Response.TypeKeyboard.START);
            sendReply(replyMessage);

            return;
        }
        return;
    }

//    public void processingMessage(){
//
//        User user = this.message.getFrom();
//        String messageText = this.message.getText();
//
//        if (messageText.equals(botPassword)){
//
//            //Стартовое меню
//            var replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
//            sendReply(replyMessage);
//
//            return;
//        }
//
//        if (messageText.equals("/startMenu")){
//
//            //Стартовое меню
//            var replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
//            sendReply(replyMessage);
//
//            return;
//        }
//
//        //формирование клавиатуры с перечнем акций
//        if (messageText.equals("Акции Мобайл ТВ")
//                || messageText.equals("Promo Mobile TV")){
//
//            var replyMessage = Response.createTextMessageWithKeyboard
//                    (this.message, "Список акций интернет магазина:\nhttps://galaxystore.ru/promo/", Response.TypeKeyboard.PROMO_MOBILE_TV);
//            sendReply(replyMessage);
//            return;
//        }
//
//        //формирование клавиатуры с перечнем акций
//        if (messageText.equals("Акции Бытовая техника")
//                || messageText.equals("Promo Appliances")){
//
//            var replyMessage = Response.createTextMessageWithKeyboard
//                    (this.message, "Список акций интернет магазина:\nhttps://galaxystore.ru/promo/", Response.TypeKeyboard.PROMO_APPLIANCES);
//            sendReply(replyMessage);
//            return;
//        }
//
//        if (messageText.equals("Характеристики устройств")
//            || messageText.equals("Device info")){
//            var replyMessage = Response.createTextMessage(this.message, "Введите название интересующего Вас устройства:");
//            sendReply(replyMessage);
//            return;
//        }
//
//        if (messageText.equals("Помощь")
//                || messageText.equals("Help")){
//
//                var replyMessage = Response.createTextMessage(this.message, new ServiceInfoCommand().create());
//                sendReply(replyMessage);
//
//                //Стартовое меню
//                replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
//                sendReply(replyMessage);
//
//                return;
//        }
//
//        if (messageText.equals("Инфо")
//                || messageText.equals("Info")){
//
//                var replyMessage = Response.createTextMessage(this.message, new InfoCommand().create());
//                sendReply(replyMessage);
//
//                //Стартовое меню
//                replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
//                sendReply(replyMessage);
//
//                return;
//        }
//
//        String bufferMessageText = messageText.toLowerCase(Locale.ROOT);
//        if (bufferMessageText.startsWith("tab")
//                || bufferMessageText.startsWith("s")
//                || bufferMessageText.startsWith("a")
//                || bufferMessageText.startsWith("buds")
//                || bufferMessageText.startsWith("smart")
//                || bufferMessageText.startsWith("galaxy")) {
//
//            SendPhoto replyPhoto = Response.createPhotoMessage(this.message, "Технические характеристики и USP:\nhttp://uspmobile.ru/", "src/main/deviceDB/zfold2.jpeg");
//            sendReply(replyPhoto);
//
//            //Стартовое меню
//            var replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
//            sendReply(replyMessage);
//
//            return;
//        }
//
//        //поиск акций БТ
//        Map<String, String> promoAppliancesInfoMap = PromoInfo.getInstancePromoAppliances();
//
//        if (promoAppliancesInfoMap.keySet().contains(messageText)){
//            for (Map.Entry<String, String> entry: promoAppliancesInfoMap.entrySet()){
//                if (messageText.equals(entry.getKey())) {
//                    var replyMessage = Response.createTextMessage(this.message, entry.getKey()+"\n\n"+entry.getValue());
//                    sendReply(replyMessage);
//
//                    //Стартовое меню
//                    replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
//                    sendReply(replyMessage);
//
//                    return;
//                }
//            }
//        }
//
//        //поиск акций мобайл и тв
//        Map<String, String> promoMobileTVInfoMap = PromoInfo.getInstancePromoMobileTV();
//
//        if (promoMobileTVInfoMap.keySet().contains(messageText)){
//                for (Map.Entry<String, String> entry: promoMobileTVInfoMap.entrySet()){
//                    if (messageText.equals(entry.getKey())) {
//                        var replyMessage = Response.createTextMessage(this.message, entry.getKey()+"\n\n"+entry.getValue());
//                        sendReply(replyMessage);
//
//                        //Стартовое меню
//                        replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
//                        sendReply(replyMessage);
//
//                        return;
//                    }
//                }
//            }
//
//        //Стартовое меню
//        var replyMessage = Response.createTextMessageWithKeyboard(this.message, "/startMenu", Response.TypeKeyboard.START);
//        sendReply(replyMessage);
//    }

}
