package nosimo.promobot.bot.authorization;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class AuthorizationWithUsernameTest {

    @BeforeAll
    static void initTestArtefacts(){
        AuthorizationWithUsername.setUsernameToList(
                "test_pass_username",
                "testPassUsername",
                "Test.pass.username1",
                "@test_pass_username",
                "@testPassUsername",
                "@Test.pass.username1",
                "",
                " ",
                "+79999999999",
                "\"",
                "\\",
                "уборщица",
                "уволен"
                );
    }

    @DisplayName("Username should be pass")
    @ParameterizedTest
    @MethodSource("nosimo.promobot.bot.authorization.AuthorizationWithUsernameTest" +
            "#getGetArgumentsForPassAuthorizationWithUsernameTest")
    void usernameShouldBePass(String username){
        boolean result = AuthorizationWithUsername.pass(username);
        assertTrue(result);
    }

    @DisplayName("Arguments for pass authorization with username")
    static Stream<Arguments> getGetArgumentsForPassAuthorizationWithUsernameTest(){
        return Stream.of(
                Arguments.of("test_pass_username"),
                Arguments.of("testPassUsername"),
                Arguments.of("Test.pass.username1")
        );
    }

    @DisplayName("Username should be not pass")
    @ParameterizedTest
    @MethodSource("nosimo.promobot.bot.authorization.AuthorizationWithUsernameTest" +
            "#getGetArgumentsForNotPassAuthorizationWithUsernameTest")
    void usernameShouldBeNotPass(String username){
        boolean result = AuthorizationWithUsername.pass(username);
        assertFalse(result);
    }

    @DisplayName("Arguments for not pass authorization with username")
    static Stream<Arguments> getGetArgumentsForNotPassAuthorizationWithUsernameTest(){
        return Stream.of(
                Arguments.of("test_not_pass_username"),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("+79999999999"),
                Arguments.of("уборщица"),
                Arguments.of("\""),
                Arguments.of("уволен")
                );
    }

    @AfterAll
    static void removeTestArtefacts(){
        AuthorizationWithUsername.removeUsernameToList(
                "test_pass_username",
                "testPassUsername",
                "Test.pass.username1",
                "@test_pass_username",
                "@testPassUsername",
                "@Test.pass.username1",
                "",
                " ",
                "+79999999999",
                "\"",
                "\\",
                "уборщица",
                "уволен"
        );
    }
}