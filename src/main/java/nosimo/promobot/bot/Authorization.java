package nosimo.promobot.bot;

import nosimo.promobot.bot.botData.BotData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Authorization {

    public static boolean pass(String enterUserName){

        enterUserName = "@" + enterUserName;

        List<String> userNameList = new ArrayList<>();

        Path userNameFile = BotData.outResources.resolve("users.txt");

//        Path userNameFile = Paths.get(".")
//                .toAbsolutePath()
//                .normalize()
//                .getParent()
//                .resolve("outResources")
//                .resolve("users.txt");

        try {
            userNameList = new ArrayList<>(Files.readAllLines(userNameFile));
        } catch (IOException e) {
            System.out.println("File users.txt not found...");
//            e.printStackTrace();
        }

        userNameList = userNameList.stream()
                        .filter(u -> !u.equals("")
                                && !u.startsWith("+")
                                && !u.startsWith("\"")
                                && !Pattern.matches(".*\\p{InCyrillic}.*", u))
                        .map(u -> {
                            if (!u.startsWith("@"))
                                u = "@" + u;
                            return u;
                        })
                        .collect(Collectors.toList());

        if (userNameList.isEmpty()) {
            return true;
        }

        if (userNameList.contains(enterUserName)) {
            return true;
        }

        return false;
    }

    //    public void authorizationUserWithPass(){
//
//        if (this.message.getText().equals("/start")){
//            this.messageCounter = 1;
//            this.pass = false;
//        }
//
//        if (this.messageCounter == 1) {
//
//            var replyMessage = Response.createTextMessage(this.message
//                    ,new StartCommand().create() + "\n\n\nВведите пароль:");
//            sendReply(replyMessage);
//
//            this.messageCounter++;
//
//            PromoInfo.updateWorkbook();
//
//            return;
//        }
//
//        //Финальная часть авторизации
//        if (this.messageCounter == 2) {
//            if (botPassword.equals(this.message.getText())) {
//                this.pass = true;
//                var replyMessage = Response.createTextMessage(this.message,
//                        "Доступ Разрешен...\n\n");
//                sendReply(replyMessage);
//                this.messageCounter++;
//            }
//            else {
//                var replyMessage = Response.createTextMessage(this.message,
//                        "Пароль не от этого Бота, попробуйте другой...:\n");
//                sendReply(replyMessage);
//                this.messageCounter = 2;
//            }
//            return;
//        }
//    }

}
