package nosimo.promobot.bot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BonusInfoDAOTest {

    @Test
    @DisplayName("When send phone number should return bonus info")
    void whenSendPhoneNumberShouldReturnBonusInfo() {
        var bonusInfoDAO = Mockito.mock(BonusInfoDAO.class);
        Mockito.doReturn("bonusInfo").when(bonusInfoDAO).getInfoPhoneNumber(Mockito.anyString());
        var result = bonusInfoDAO.getInfoPhoneNumber("9999999999");
        assertThat(result).isEqualTo("bonusInfo");
    }

    @Test
    @DisplayName("When send card number should return bonusInfo")
    void whenSendCardNumberShouldReturnBonusInfo() {
        var bonusInfoDAO = Mockito.mock(BonusInfoDAO.class);
        Mockito.doReturn("bonusInfo").when(bonusInfoDAO).getInfoCardNumber(Mockito.anyString());
        var result = bonusInfoDAO.getInfoCardNumber("2000654321");
        assertThat(result).isEqualTo("bonusInfo");
    }
}