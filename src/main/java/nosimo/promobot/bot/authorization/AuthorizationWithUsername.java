package nosimo.promobot.bot.authorization;

import nosimo.promobot.bot.botData.BotData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AuthorizationWithUsername {

    public static boolean pass(String enterUserName){

        enterUserName = "@" + enterUserName;

        List<String> userNameList = new ArrayList<>();

        Path userNameFile = BotData.outResources.resolve("users.txt");

        try {
            userNameList = new ArrayList<>(Files.readAllLines(userNameFile));
        } catch (IOException e) {
            System.out.println("File users.txt not found...");
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
}
