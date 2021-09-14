package nosimo.promobot.commandSystem;

import nosimo.promobot.bot.botData.BotData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class StartCommand {

    public String create() {

        List<String> seviceInfoStringList =  new ArrayList<>();

        Path serviceInfoFile = BotData.outResources.resolve("startInfo.txt");

        try {
            seviceInfoStringList = new ArrayList<>(Files.readAllLines(serviceInfoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder response = new StringBuilder();

        for (String serviceInfoLine: seviceInfoStringList) {
            response.append(serviceInfoLine + "\n");
        }

        return response.toString().trim();
    }

}
