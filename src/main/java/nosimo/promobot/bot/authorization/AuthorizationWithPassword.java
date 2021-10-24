package nosimo.promobot.bot.authorization;

import nosimo.promobot.bot.Response;
import nosimo.promobot.commandSystem.StartCommand;

import static nosimo.promobot.bot.botData.BotDataDAO.botPassword;

public class AuthorizationWithPassword {

    public static void authorizationUserWithPass(Long chatId, String messageText){
        int messageCounter = 0;
        boolean pass = false;

        if (messageText.equals("/start")){
            messageCounter = 1;
            pass = false;
        }

        if (messageCounter == 1) {

            var replyMessage = Response.createTextMessage(chatId
                    ,new StartCommand().create() + "\n\n\nВведите пароль:");
//            sendReply(replyMessage);

            messageCounter++;

            return;
        }

        //Финальная часть авторизации
        if (messageCounter == 2) {
            if (botPassword.equals(messageText)) {
                pass = true;
                var replyMessage = Response.createTextMessage(chatId,
                        "Доступ Разрешен...\n\n");
//                sendReply(replyMessage);
                messageCounter++;
            }
            else {
                var replyMessage = Response.createTextMessage(chatId,
                        "Пароль не от этого Бота, попробуйте другой...:\n");
//                sendReply(replyMessage);
                messageCounter = 2;
            }
            return;
        }
    }
}
