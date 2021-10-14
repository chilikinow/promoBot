package nosimo.promobot.bot.authorization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationWithUsernameTest {

    @Test
    void usernameShouldBeNotPass(){
        boolean result = AuthorizationWithUsername.pass("test_not_pass_username");
        assertFalse(result);
    }
}