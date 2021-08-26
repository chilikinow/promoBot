package nosimo.promobot.bot.botData;

import com.fasterxml.jackson.databind.ObjectMapper;

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

        Path botInfoPath = Paths.get(".")
                .toAbsolutePath()
                .normalize()
                .getParent()
                .resolve("outResources")
                .resolve("botData.json");

        if (!Files.exists(botInfoPath)) {
            String separator = File.separator;
            botInfoPath = Paths.get("src" + separator + "main" + separator + "resources" + separator + "botData.json");
        }

        BotInfo botInfo = new BotInfo();
        try {
            botInfo = new ObjectMapper().readValue(botInfoPath.toFile(), BotInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.botName = botInfo.getAuthorizationBot().getUserName();
        this.botToken = botInfo.getAuthorizationBot().getToken();
        this.botPassword = botInfo.getAuthorizationBot().getPassword();

        this.bonusBaseURI = botInfo.getBonusCardSystem().getUri();
        this.bonusUserName = botInfo.getBonusCardSystem().getUserName();
        this.bonusPassword = botInfo.getBonusCardSystem().getPassword();

        this.readPromoInfoFileUrl = botInfo.getPromoInfoFile().getReadUri();
        this.downloadPromoInfoFileUrl = botInfo.getPromoInfoFile().getDownloadFileUri();
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
