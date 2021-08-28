package nosimo.promobot.bot.botData;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        //Jackson

        BotInfoForJackson botInfo = new BotInfoForJackson();
        try {
            botInfo = new ObjectMapper().readValue(botInfoPath.toFile(), BotInfoForJackson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.botName = botInfo.getAuthorizationBot().getUserName();
        this.botToken = botInfo.getAuthorizationBot().getToken();
        this.botPassword = botInfo.getAuthorizationBot().getPassword();

        //Json Simple

        JSONObject objectRoot = new JSONObject();
        try {
            objectRoot = (JSONObject) new JSONParser().parse(new FileReader(botInfoPath.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        JSONObject objectBonusCardSystem = (JSONObject) objectRoot.get("bonusCardSystem");
        this.bonusBaseURI = (String) objectBonusCardSystem.get("uri");
        this.bonusUserName = (String) objectBonusCardSystem.get("userName");
        this.bonusPassword = (String) objectBonusCardSystem.get("password");

        //JsonPath

        try {
            this.readPromoInfoFileUrl = JsonPath.read(botInfoPath.toFile(), "$.promoInfoFile.readUri");
            this.downloadPromoInfoFileUrl = JsonPath.read(botInfoPath.toFile(), "$.promoInfoFile.downloadFileUri");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
