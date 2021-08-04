package nosimo.promobot.bot;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Authorization {

    public boolean pass(Message message){

        String enterUserName = message.getFrom().getUserName();

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

        if (userNameList.contains(enterUserName)) {
            return true;
        }

        return false;
    }
}
