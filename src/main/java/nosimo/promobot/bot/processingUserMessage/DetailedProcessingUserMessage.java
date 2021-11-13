package nosimo.promobot.bot.processingUserMessage;

import nosimo.promobot.bot.BonusInfo;
import nosimo.promobot.bot.DeviceInfo;
import nosimo.promobot.bot.PromoInfo;
import nosimo.promobot.bot.Response;
import nosimo.promobot.bot.authorization.AuthorizationWithUsername;
import nosimo.promobot.bot.botData.BotData;
import org.apache.commons.io.FilenameUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class DetailedProcessingUserMessage {

    public static String startButtonInfo;
    private SendMessage notPassReplyMessage;
    private List<Object> replyMessageList;
    private SendMessage replyMessage;

    {
        startButtonInfo = ProcessingUserMessage.startButtonsInfo;
        replyMessageList = new ArrayList<>();
    }

    public Object searchAnswer(Long chatId, String userName, String messageText) {

        notPassReplyMessage = Response.createTextMessageWithKeyboard(chatId,
                "У Вас нет доступа к данной системе."
                        + "\n\n"
                        + "Для получения доступа необходимо обратится к Управляющему Вашего магазина.",
                Response.TypeKeyboard.START);

        //поиск устройства

        String infoCardName = messageText
                .toLowerCase(Locale.ROOT)
                .replaceAll(" ", "")
                .replaceAll("galaxy", "")
                .replaceAll("samsung", "")
                .replaceAll("-", "")
                .replaceAll("_", "")
//                .replaceAll("wifi", "")
//                .replaceAll("lte", "")
                .replaceAll("plus", "\\+")
                .replace("+", "\\+");

        if (infoCardName.length() < 15){
            var categoryDeviceList = new DeviceInfo().getCategoryDeviceList();
            List<String> bufferCategoryDeviceList = new ArrayList<>(categoryDeviceList);
            for (int i = 0; i < bufferCategoryDeviceList.size(); i++) {
                if (infoCardName.startsWith(bufferCategoryDeviceList.get(i))) {
                    if (infoCardName.length() == 1) {
                        SendMessage replyMessage = Response.createTextMessage(chatId
                                , "Уточните модель:");
                        return replyMessage;
                    }
                    Path directory = BotData.outResources.resolve("dataBaseProducts");
                    List<Path> resultDeviceInfoList = new DeviceInfo().findInfo(messageText, directory);
                    if (!resultDeviceInfoList.isEmpty()) {
                        for (Path resultDeviseInfo : resultDeviceInfoList){
                            SendPhoto replyPhoto = Response.createPhotoMessage(chatId,
                                    FilenameUtils.removeExtension(resultDeviseInfo.getFileName().toString()),
                                    resultDeviseInfo.getParent().resolve(resultDeviseInfo.getFileName()).toString());
                            replyMessageList.add(replyPhoto);
                        }
                        replyMessageList.add(Response.createTextMessageWithKeyboard(chatId,
                                startButtonInfo, Response.TypeKeyboard.START));
                        return replyMessageList;
                    } else {
                        replyMessage = Response.createTextMessageWithKeyboard(chatId,
                                "Устройство не найдено!\n\nСписок доступных для поиска устройств\n" +
                                        "находится в разделе ИНФО стартового меню." + "\n\n" + startButtonInfo
                                , Response.TypeKeyboard.START);
                        return replyMessage;
                    }
                }
            }
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
            String findBonusInfo = new BonusInfo().getInfoPhoneNumber(bonusMessageText);
            if (!findBonusInfo.isEmpty()){
                replyMessage = Response.createTextMessageWithKeyboard(chatId
                        , findBonusInfo + "\n\n" + startButtonInfo
                        , Response.TypeKeyboard.START);
            } else {
                replyMessage = Response.createTextMessageWithKeyboard(chatId
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
            String findBonusInfo = new BonusInfo().getInfoCardNumber(bonusMessageText);
            if (!findBonusInfo.isEmpty()) {
                replyMessage = Response.createTextMessageWithKeyboard(chatId
                        , findBonusInfo + "\n\n" + startButtonInfo
                        , Response.TypeKeyboard.START);
            } else {
                replyMessage = Response.createTextMessageWithKeyboard(chatId
                        , "Карты лояльности нет в базе данных." + "\n\n" + startButtonInfo
                        , Response.TypeKeyboard.START);
            }
            return replyMessage;
        }

        //Поиск подробной информации по запрашиваемой акции

        Map<String, String> promoMobileTVInfoMap = PromoInfo.getInstancePromoMobileTV();
        List<String> promoMobileTVInfoKeyList = promoMobileTVInfoMap.keySet().stream().map(key -> {
           if (key.length() > 30)
               return key.substring(0, 30);
            return key;
        }).collect(Collectors.toList());
        if (promoMobileTVInfoKeyList.contains(messageText)) {
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
                        replyMessageList.add(Response.createTextMessageWithKeyboard(chatId
                                ,replyText.append("\n\n" + startButtonInfo).toString()
                                ,Response.TypeKeyboard.START));
                        return replyMessageList;
                    }else{
                        replyMessage = Response.createTextMessageWithKeyboard(chatId
                                ,replyText.append("\n\n" + startButtonInfo).toString()
                                ,Response.TypeKeyboard.START);
                        return replyMessage;
                    }
                }
            }
        }

        //Поиск подробной информации по запрашиваемой акции

        Map<String, String> promoAppliancesInfoMap = PromoInfo.getInstancePromoAppliances();
        List<String> promoAppliancesInfoKeyList = promoAppliancesInfoMap.keySet().stream().map(key -> {
            if (key.length() > 30)
                return key.substring(0, 30);
            return key;
        }).collect(Collectors.toList());
        if (promoAppliancesInfoKeyList.contains(messageText)) {
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
                        replyMessageList.add(Response.createTextMessageWithKeyboard(chatId
                                ,replyText.append("\n\n" + startButtonInfo).toString()
                                ,Response.TypeKeyboard.START));
                        return replyMessageList;
                    }else{
                        replyMessage = Response.createTextMessageWithKeyboard(chatId
                                ,replyText.append("\n\n" + startButtonInfo).toString()
                                ,Response.TypeKeyboard.START);
                        return replyMessage;
                    }
                }
            }
        }
        return null;
    }
}
