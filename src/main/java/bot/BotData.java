package bot;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class BotData {

    private String downloadPromoInfoFileDirectoryAddress;
    private String botName;
    private String botToken;
    private String botPassword;

    public BotData(){
        init();
    }

    private void init(){
        Path botInfoPropertiesFile = Paths.get
                ("src/main/resources/botInfo.properties");
        Properties botInfoProperties = new Properties();
        try {
            botInfoProperties.load(new FileReader(botInfoPropertiesFile.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.botName = botInfoProperties.getProperty("botUsername");
        this.botToken = botInfoProperties.getProperty("botToken");
        this.botPassword = botInfoProperties.getProperty("botPassword");

        this.downloadPromoInfoFileDirectoryAddress = botInfoProperties.getProperty("promoInfoFileAddress");
    }

    public String getDownloadPromoInfoFileDirectoryAddress() {
        return downloadPromoInfoFileDirectoryAddress;
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

}
