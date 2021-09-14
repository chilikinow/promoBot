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

    public final static Path outResources;
    public final static String botName;
    public final static String botToken;
    public final static String botPassword;
    public final static String bonusBaseURI;
    public final static String bonusUserName;
    public final static String bonusPassword;
    public static String readPromoInfoFileUrl;
    public static String downloadPromoInfoFileUrl;

    static {
        Path outResourcesCheck;

        outResourcesCheck = Paths.get(".")
                .toAbsolutePath()
                .normalize()
                .getParent()
                .resolve("outResources");

        if (!Files.exists(outResourcesCheck)) {
            outResourcesCheck = Paths.get("C:\\promoBot\\outResources");
        }

        outResources = outResourcesCheck;

        Path botInfoPath = outResources.resolve("botData.json");

//        Path botInfoPath = Paths.get(".")
//                .toAbsolutePath()
//                .normalize()
//                .getParent()
//                .resolve("outResources")
//                .resolve("botData.json");
//
//        if (!Files.exists(botInfoPath)) {
//            String separator = File.separator;
//            botInfoPath = Paths.get("src" + separator + "main" + separator + "resources" + separator + "botData.json");
//        }

        //Jackson

        var botInfo = new BotInfoForJackson();
        try {
            botInfo = new ObjectMapper().readValue(botInfoPath.toFile(), BotInfoForJackson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        botName = botInfo.getAuthorizationBot().getUserName();
        botToken = botInfo.getAuthorizationBot().getToken();
        botPassword = botInfo.getAuthorizationBot().getPassword();

        //Json Simple

        var objectRoot = new JSONObject();
        try {
            objectRoot = (JSONObject) new JSONParser().parse(new FileReader(botInfoPath.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        var objectBonusCardSystem = (JSONObject) objectRoot.get("bonusCardSystem");
        bonusBaseURI = (String) objectBonusCardSystem.get("uri");
        bonusUserName = (String) objectBonusCardSystem.get("userName");
        bonusPassword = (String) objectBonusCardSystem.get("password");

        //JsonPath

        try {
            readPromoInfoFileUrl = JsonPath.read(botInfoPath.toFile(), "$.promoInfoFile.readUri");
            downloadPromoInfoFileUrl = JsonPath.read(botInfoPath.toFile(), "$.promoInfoFile.downloadFileUri");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
