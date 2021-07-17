package bot;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class BotData {

    private String botName;
    private String botToken;
    private String botPassword;
    private String bonusBaseURI;
    private String bonusUserName;
    private String bonusPassword;
    private String readPromoInfoFileUrl;
    private String downloadPromoInfoFileUrl;

    public BotData(){
        init();
    }

    private void init(){

        Path botInfoPropertiesFile = Paths.get(".")
                .toAbsolutePath()
                .normalize()
                .getParent()
                .resolve("resources")
                .resolve("botData.properties");

        if (!Files.exists(botInfoPropertiesFile)) {
            String separator = File.separator;
            botInfoPropertiesFile = Paths.get("src" + separator + "main" + separator + "resources" + separator + "botData.properties");
        }

        Properties botDataProperties = new Properties();
        try {
            botDataProperties.load(new FileReader(botInfoPropertiesFile.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.botName = botDataProperties.getProperty("botUsername");
        this.botToken = botDataProperties.getProperty("botToken");
        this.botPassword = botDataProperties.getProperty("botPassword");

        this.bonusBaseURI = botDataProperties.getProperty("bonusBaseURI");
        this.bonusUserName = botDataProperties.getProperty("bonusUserName");
        this.bonusPassword = botDataProperties.getProperty("bonusPassword");

        this.readPromoInfoFileUrl = botDataProperties.getProperty("readPromoInfoFileUrl");
        this.downloadPromoInfoFileUrl = botDataProperties.getProperty("downloadPromoInfoFileUrl");
    }

    public String getReadPromoInfoFileUrl() {
        return readPromoInfoFileUrl;
    }

    public String getDownloadPromoInfoFileUrl() {
        return downloadPromoInfoFileUrl;
    }

    public String getBotName() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotPassword() {
        return botPassword;
    }

    public String getBonusBaseURI() {
        return bonusBaseURI;
    }

    public String getBonusUserName() {
        return bonusUserName;
    }

    public String getBonusPassword() {
        return bonusPassword;
    }

}
