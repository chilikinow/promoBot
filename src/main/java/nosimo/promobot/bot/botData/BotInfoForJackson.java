package nosimo.promobot.bot.botData;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonAutoDetect
@Getter
@Setter
@NoArgsConstructor
public class BotInfoForJackson {
    private AuthorizationBot authorizationBot;
    private BonusCardSystem bonusCardSystem;
    private PromoInfoFile promoInfoFile;
}

@JsonAutoDetect
@Getter
@Setter
@NoArgsConstructor
class AuthorizationBot {
    private String UserName;
    private String token;
    private String password;
}

@JsonAutoDetect
@Getter
@Setter
@NoArgsConstructor
class BonusCardSystem {
    private String uri;
    private String userName;
    private String password;
}

@JsonAutoDetect
@Getter
@Setter
@NoArgsConstructor
class PromoInfoFile {
    private String readUri;
    private String downloadFileUri;
}
