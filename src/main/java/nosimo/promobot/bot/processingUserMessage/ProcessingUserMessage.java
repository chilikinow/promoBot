package nosimo.promobot.bot.processingUserMessage;

import nosimo.promobot.bot.PromoInfo;
import nosimo.promobot.bot.Response;
import nosimo.promobot.bot.botData.BotData;
import nosimo.promobot.commandSystem.InfoCommand;
import nosimo.promobot.commandSystem.ServiceCommand;
import nosimo.promobot.commandSystem.StartCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProcessingUserMessage {

    public static String startButtonsInfo;
    public static String buttonsInfo;
    private List<Object> replyMessageList;
    private SendMessage replyMessage;
    private SendDocument replyDocument;

    {
        startButtonsInfo = "Главное меню:";
        buttonsInfo = "Меню:";
        replyMessageList = new ArrayList<>();
    }

    public Object searchAnswer(Long chatId, String userName, String messageText) {

        // Вывод списка пользователей, делавших запрос

        System.out.println(
                new SimpleDateFormat("dd.MM.yyyy hh:mm")
                        .format(Calendar.getInstance().getTime())
                        + " "
                        + "@" + userName
                        + ": "
                        + messageText);

        switch (messageText) {

            case "Акции Мобайл ТВ": // Запрос акций по Мобильной технике и ТВ
            case "/promo_mobile_tv":

                replyMessage = Response.createTextMessageWithKeyboard
                        (chatId, "Список акций интернет магазина:\n" +
                                        "https://galaxystore.ru/promo/",
                                Response.TypeKeyboard.PROMO_MOBILE_TV);
                return replyMessage;

            case "Акции БТ":
            case "/promo_appliances":

                replyMessage = Response.createTextMessageWithKeyboard
                        (chatId, "Список акций интернет магазина:\n" +
                                        "https://galaxystore.ru/promo/",
                                Response.TypeKeyboard.PROMO_APPLIANCES);
                return replyMessage;

            case "Характеристики устройств": // Поиск подробной информации об устройстве
            case "/devices":

                SendMessage replyMessage = Response.createTextMessage(chatId,
                        "Введите название устройства:");
                return replyMessage;

            case "Что можно быстро найти?":
            case "/info":
                replyMessage = Response.createTextMessageWithKeyboard(chatId
                        , "Доступные для поиска категории:"
                        , Response.TypeKeyboard.INFO);
                return replyMessage;

            case "Мобильная техника": // Запрос списка устройств для поиска
            case "/mobile":

                String heading = "Список доступных для поиска устройств:\n\nМобильная электроника:\n\n";
                Path directory = BotData.outResources.resolve("dataBaseProducts").resolve("mobile");
                String ending = "\nСписок устройств в процессе пополнения...";
                String replyTextMessage = new InfoCommand().create(heading, directory, ending);
                replyMessage = Response.createTextMessageWithKeyboard(chatId
                        , replyTextMessage
                                + "\n\n"
                                + "Технические характеристики и USP:\nhttp://uspmobile.ru/"
                                + "\n\n"
                                + buttonsInfo
                        , Response.TypeKeyboard.INFO);
                return replyMessage;

            case "Телевизоры":
            case "/tv":

                heading = "Список доступных для поиска устройств:\n\nТелевизоры:\n\n";
                directory = BotData.outResources.resolve("dataBaseProducts").resolve("tv");
                ending = "\nСписок устройств в процессе пополнения...";
                replyTextMessage = new InfoCommand().create(heading, directory, ending);
                replyMessage = Response.createTextMessageWithKeyboard(chatId
                        , replyTextMessage
                                + "\n\n"
                                + buttonsInfo
                        , Response.TypeKeyboard.INFO);
                return replyMessage;

            case "Бытовая техника": // Запрос акций по Бытовой технике
            case "/appliances":

                heading = "Список доступных для поиска устройств:\n\nБытовая техника:\n\n";
                directory = BotData.outResources.resolve("dataBaseProducts").resolve("appliances");
                ending = "\nСписок устройств в процессе пополнения...";
                replyTextMessage = new InfoCommand().create(heading, directory, ending);
                replyMessage = Response.createTextMessageWithKeyboard(chatId
                        , replyTextMessage
                                + "\n\n"
                                + buttonsInfo
                        , Response.TypeKeyboard.INFO);
                return replyMessage;

            case "Сервис": // Запрос информации о Сервисе
            case "/service":

                replyMessage = Response.createTextMessageWithKeyboard(chatId
                        , new ServiceCommand().create()
                                + "\n\n"
                                + buttonsInfo
                        , Response.TypeKeyboard.INFO);
                return replyMessage;

            case "Программа лояльности": // Поиск Бонусной карты
            case "/bonus_card":
//            replyMessageList.add(Response.createTextMessage(chatId,
//                    "Активация бонусной карты:"
//                    + "\n\n"
//                    + "https://galaxystore.ru/about/bonus/?utm_source=shop&utm_medium=qr&utm_campaign=activate#card-activate"));
                replyMessageList.add(Response.createTextMessage(chatId, "Введите номер телефона или бонусной карты:"));
                return replyMessageList;

            case "/promo_update": // Обновление файла с акциями

                PromoInfo.updateWorkbook();
                replyMessage = Response.createTextMessageWithKeyboard(chatId
                        , "База Акций обновлена!" + "\n\n" + startButtonsInfo
                        , Response.TypeKeyboard.START);
                return replyMessage;

            case "/start_menu": //Стартовое меню
            case "Главное меню":

                replyMessage = Response.createTextMessageWithKeyboard(chatId,
                        startButtonsInfo, Response.TypeKeyboard.START);
                return replyMessage;

            case "/start": // Первый запуск

                replyMessage = Response.createTextMessageWithKeyboard(chatId
                        , new StartCommand().create() + "\n\n" + startButtonsInfo
                        , Response.TypeKeyboard.START);
                return replyMessage;

            case "/pdf":

                replyDocument = Response.createDocumentMessage(chatId
                        , "test"
                        , BotData.outResources.resolve("dataBaseProducts").resolve("Сервис Плаз.pdf").toString());
                return replyDocument;
        }

        Object detailedReplyMessage = new DetailedProcessingUserMessage().searchAnswer(chatId, userName, messageText);

        if (detailedReplyMessage != null)
            return detailedReplyMessage;

        //если не найдено ни одного совпадения
        //Стартовое меню
        replyMessage = Response.createTextMessageWithKeyboard(chatId
                ,"Команда не найдена!" + "\n\n" + startButtonsInfo
                ,Response.TypeKeyboard.START);
        return replyMessage;

    }
}
