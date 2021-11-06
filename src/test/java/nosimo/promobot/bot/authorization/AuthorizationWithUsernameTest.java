package nosimo.promobot.bot.authorization;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class AuthorizationWithUsernameTest {

    private static String[] artefacts;

    static {
        artefacts = new String[]{"test_pass_username",
                "testPassUsername",
                "Test.pass.username1",
                "test_pass_username",
                "testPassUsername",
                "Test.pass.username1",
                "",
                " ",
                "+79999999999",
                "\"",
                "\\",
                "уборщица",
                "уволен"};
    }

    @BeforeAll
    static void initTestArtefacts(){
        AuthorizationWithUsername.setUsernameToListFOrTest(artefacts);
    }

    @DisplayName("Arguments for pass authorization with username")
    static Stream<Arguments> getArgumentsForPassAuthorizationWithUsernameTest(){
        return Stream.of(
                Arguments.of("test_pass_username"),
                Arguments.of("testPassUsername"),
                Arguments.of("Test.pass.username1")
        );
    }

    @DisplayName("Username should be pass")
    @ParameterizedTest
    @MethodSource("nosimo.promobot.bot.authorization.AuthorizationWithUsernameTest" +
            "#getArgumentsForPassAuthorizationWithUsernameTest")
    void usernameShouldBePass(String username){
        boolean result = AuthorizationWithUsername.pass(username);
        assertThat(result).isTrue();
    }

    @DisplayName("Arguments for not pass authorization with username")
    static Stream<Arguments> getArgumentsForNotPassAuthorizationWithUsernameTest(){
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

    @DisplayName("Username should be not pass")
    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource("nosimo.promobot.bot.authorization.AuthorizationWithUsernameTest" +
            "#getArgumentsForNotPassAuthorizationWithUsernameTest")
    void usernameShouldBeNotPass(String username){
        boolean result = AuthorizationWithUsername.pass(username);
        assertThat(result).isFalse();
    }

    @AfterAll
    static void removeTestArtefacts(){
        AuthorizationWithUsername.removeUsernameToList(artefacts);
    }
}