package nosimo.promobot.commandSystem;

import nosimo.promobot.bot.botData.BotDataDAO;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ServiceCommand {

    public String create() {

        Path phonesInfoPropertiesFile = BotDataDAO.outResources.resolve("phonesInfo.properties");

        var phonesInfoProperties = new Properties();
        try {
            phonesInfoProperties.load(new FileReader(phonesInfoPropertiesFile.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();

        for (String name : phonesInfoProperties.stringPropertyNames()) {
            map.put(name, phonesInfoProperties.getProperty(name));
        }

        List<String> seviceInfoStringList =  new ArrayList<>();

        Path serviceInfoFile = BotDataDAO.outResources.resolve("serviceInfo.txt");

        try {
            seviceInfoStringList = new ArrayList<>(Files.readAllLines(serviceInfoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        var response = new StringBuilder();

        for (String serviceInfoLine: seviceInfoStringList) {
            response.append(serviceInfoLine + "\n");
        }

        response.append("Телефоны Поддержки:\n\n");
        for (Map.Entry<String, String> entry: map.entrySet()) {
            response.append(entry.getKey()+ ": ")
                    .append(entry.getValue()+"\n");
        }
        return response.toString().trim();

    }
}
