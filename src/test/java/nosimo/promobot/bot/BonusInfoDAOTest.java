package nosimo.promobot.bot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class BonusInfoDAOTest {

    @Test
    @DisplayName("When send phone number should return bonus info")
    void whenSendPhoneNumberShouldReturnBonusInfo() {
        var bonusInfoDAO = mock(BonusInfoDAO.class);
        doReturn("bonusInfo").when(bonusInfoDAO).getInfoPhoneNumber(anyString());
        var result = bonusInfoDAO.getInfoPhoneNumber("9999999999");
        assertThat(result).isEqualTo("bonusInfo");
    }

    @Test
    @DisplayName("When send card number should return bonusInfo")
    void whenSendCardNumberShouldReturnBonusInfo() {
        var bonusInfoDAO = mock(BonusInfoDAO.class);
        doReturn("bonusInfo").when(bonusInfoDAO).getInfoCardNumber(anyString());
        var result = bonusInfoDAO.getInfoCardNumber("2000654321");
        assertThat(result).isEqualTo("bonusInfo");
    }
}