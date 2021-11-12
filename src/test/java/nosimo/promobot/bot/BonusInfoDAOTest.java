package nosimo.promobot.bot;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith({
        MockitoExtension.class
})
class BonusInfoDAOTest {

    @Mock
    private BonusInfo bonusInfoDAO;

    @Test
    @DisplayName("When send phone number should return bonus info")
    void whenSendPhoneNumberShouldReturnBonusInfo() {
        doReturn("bonusInfo").when(bonusInfoDAO).getInfoPhoneNumber(anyString());
        var result = bonusInfoDAO.getInfoPhoneNumber("9999999999");
        assertThat(result).isEqualTo("bonusInfo");
    }

//    @Test
//    @DisplayName("When send card number should return bonusInfo")
//    void whenSendCardNumberShouldReturnBonusInfo() {
//        doReturn("bonusInfo").when(bonusInfoDAO).getInfoCardNumber(anyString());
//        var result = verify(bonusInfoDAO, times(1)).getInfoCardNumber("2000654321");
//        assertThat(result).isEqualTo("bonusInfo");
//    }

}