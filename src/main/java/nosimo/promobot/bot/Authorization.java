package nosimo.promobot.bot;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Authorization {

    public boolean pass(String enterUserName){

        enterUserName = "@" + enterUserName;

        Path userNameFile = Paths.get(".")
                .toAbsolutePath()
                .normalize()
                .getParent()
                .resolve("outResources")
                .resolve("users.txt");

        if (!Files.exists(userNameFile)) {
            return true;
        }

        List<String> userNameList = new ArrayList<>();
        try {
            userNameList = new ArrayList<>(Files.readAllLines(userNameFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        userNameList = userNameList.stream()
                .filter(u -> !u.equals("")
                        && !u.isEmpty()
                        && !u.startsWith("+")
                        && !u.startsWith("\"")
                        && !Pattern.matches(".*\\p{InCyrillic}.*", u))
                .map(u -> {
                    if (!u.startsWith("@"))
                        u = "@" + u;
                    return u;
                })
                .collect(Collectors.toList());

        if (userNameList.contains(enterUserName)) {
            return true;
        }

        return false;
    }
}
