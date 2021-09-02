package nosimo.promobot.bot.botData;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class BotInfoForJackson {
    private AuthorizationBot authorizationBot;
    private BonusCardSystem bonusCardSystem;
    private PromoInfoFile promoInfoFile;

    public BotInfoForJackson() {
    }

    public AuthorizationBot getAuthorizationBot() {
        return authorizationBot;
    }

    public void setAuthorizationBot(AuthorizationBot authorizationBot) {
        this.authorizationBot = authorizationBot;
    }

    public BonusCardSystem getBonusCardSystem() {
        return bonusCardSystem;
    }

    public void setBonusCardSystem(BonusCardSystem bonusCardSystem) {
        this.bonusCardSystem = bonusCardSystem;
    }

    public PromoInfoFile getPromoInfoFile() {
        return promoInfoFile;
    }

    public void setPromoInfoFile(PromoInfoFile promoInfoFile) {
        this.promoInfoFile = promoInfoFile;
    }
}

@JsonAutoDetect
class AuthorizationBot {
    private String UserName;
    private String token;
    private String password;

    public AuthorizationBot() {
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

@JsonAutoDetect
class BonusCardSystem {
    private String uri;
    private String userName;
    private String password;

    public BonusCardSystem() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

@JsonAutoDetect
class PromoInfoFile {
    private String readUri;
    private String downloadFileUri;

    public PromoInfoFile() {
    }

    public String getReadUri() {
        return readUri;
    }

    public void setReadUri(String readUri) {
        this.readUri = readUri;
    }

    public String getDownloadFileUri() {
        return downloadFileUri;
    }

    public void setDownloadFileUri(String downloadFileUri) {
        this.downloadFileUri = downloadFileUri;
    }
}
