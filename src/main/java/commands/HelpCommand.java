package commands;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HelpCommand{


    public String create() {
        StringBuilder response = new StringBuilder();

        Path phonesInfoPropertiesFile = Paths.get("src/main/resources/phonesInfo.properties");
        Properties phonesInfoProperties = new Properties();
        try {
            phonesInfoProperties.load(new FileReader(phonesInfoPropertiesFile.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();

        for (String name : phonesInfoProperties.stringPropertyNames()) {
            map.put(name, phonesInfoProperties.getProperty(name));
        }
        response.append("Телефоны для Справки:\n");
        for (Map.Entry<String, String> entry: map.entrySet()) {
            response.append(entry.getKey()+ ": ")
                    .append(entry.getValue()+"\n");
        }
        return response.toString();

    }
}
