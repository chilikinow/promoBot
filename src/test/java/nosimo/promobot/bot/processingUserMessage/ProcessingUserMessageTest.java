package nosimo.promobot.bot.processingUserMessage;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

class ProcessingUserMessageTest {

    @Test
    void searchAnswerShouldReturnInfoBTFasterThanTwoSeconds() {
        assertTimeout(Duration.ofSeconds(2), () -> {
            new ProcessingUserMessage().searchAnswer(new Long(123456789), "test_pass_username", "Инфо БТ");
        });
    }

    @Test
    void searchAnswerShouldReturnInfoBonusCardWhenWeNowPhoneNumberFasterThanSixSeconds() {
        assertTimeout(Duration.ofSeconds(9), () -> {
            new ProcessingUserMessage().searchAnswer(new Long(123456789), "test_pass_username", "9999999911");
        });
    }
}