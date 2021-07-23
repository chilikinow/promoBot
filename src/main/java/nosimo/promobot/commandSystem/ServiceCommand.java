package nosimo.promobot.commandSystem;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ServiceCommand {


    public String create() {

        Path phonesInfoPropertiesFile = Paths.get(".")
                .toAbsolutePath()
                .normalize()
                .getParent()
                .resolve("outResources")
                .resolve("phonesInfo.properties");

        if (!Files.exists(phonesInfoPropertiesFile)) {
            String separator = File.separator;
            phonesInfoPropertiesFile = Paths.get("src" + separator + "main" + separator + "resources" + separator + "phonesInfo.properties");
        }

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

        List<String> seviceInfoStringList =  new ArrayList<>();

        Path serviceInfoFile = Paths.get(".")
                .toAbsolutePath()
                .normalize()
                .getParent()
                .resolve("outResources")
                .resolve("serviceInfo.txt");

        if (!Files.exists(serviceInfoFile)) {
            String separator = File.separator;
            serviceInfoFile = Paths.get("src" + separator + "main" + separator + "resources" + separator + "serviceInfo.txt");
        }

        try {
            seviceInfoStringList = new ArrayList<>(Files.readAllLines(serviceInfoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder response = new StringBuilder();

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
