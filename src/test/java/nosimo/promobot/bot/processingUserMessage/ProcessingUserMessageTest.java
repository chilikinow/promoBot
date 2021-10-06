package nosimo.promobot.bot.processingUserMessage;

import org.junit.jupiter.api.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProcessingUserMessageTest {
    Long chatId;
    String username;
    String messageText;
    String expectedText;
    SendMessage result;

    @BeforeAll
    void init(){
        chatId = new Long(123456789);
        username = "test_pass_username";
    }

//    @Test
//    void failerTest(){
//        assertTrue(false);
//    }


    @Tag("functional")
    @Nested
    @DisplayName("functional test")
    class functionalTest{

        @Test
        @DisplayName("search answer should return start info text")
        void searchAnswerShouldReturnStartInfoText() {
            messageText = "/start";
            expectedText = "Приветствую!";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("search answer should return start menu text")
        void searchAnswerShouldReturnStartMenuText() {
            messageText = "/start_menu";
            expectedText = "Стартовое меню: /start_menu";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("when command back search answer should return start menu text")
        void whenCommandBackSearchAnswerShouldReturnStartMenuText() {
            messageText = "Назад";
            expectedText = "Стартовое меню: /start_menu";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("search answer should return promo appliances text")
        void searchAnswerShouldReturnPromoAppliancesText() {
            messageText = "Акции БТ";
            expectedText = "Список акций интернет магазина:\n" +
                    "https://galaxystore.ru/promo/";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("when command promo appliances search answer should return promo appliances text")
        void whenCommandPromoAppliancesSearchAnswerShouldReturnPromoAppliancesText() {
            messageText = "/promo_appliances";
            expectedText = "Список акций интернет магазина:\n" +
                    "https://galaxystore.ru/promo/";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).isEqualTo(expectedText);
        }

        @Test
        @DisplayName("search answer should return promo mobile TV text")
        void searchAnswerShouldReturnPromoMobileTVText() {
            messageText = "Акции Мобайл ТВ";
            expectedText = "Список акций интернет магазина:\n" +
                    "https://galaxystore.ru/promo/";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).isEqualTo(expectedText);
        }

        @Test
        @DisplayName("when command promo mobile TV search answer should return promo mobile TV text")
        void whenCommandPromoMobileTVSearchAnswerShouldReturnPromoMobileTVText() {
            messageText = "/promo_mobile_tv";
            expectedText = "Список акций интернет магазина:\n" +
                    "https://galaxystore.ru/promo/";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).isEqualTo(expectedText);
        }

        @Test
        @DisplayName("search answer should return devise info text")
        void searchAnswerShouldReturnDeviseInfoText() {
            messageText = "Характеристики устройств";
            expectedText = "Введите название устройства:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).isEqualTo(expectedText);
        }

        @Test
        @DisplayName("when command device info search answer should return devise info text")
        void whenCommandDeviceInfoSearchAnswerShouldReturnDeviseInfoText() {
            messageText = "/device_info";
            expectedText = "Введите название устройства:";
            SendMessage result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).isEqualTo(expectedText);
        }

        @Test
        @DisplayName("search answer should return appliances info")
        void searchAnswerShouldReturnAppliancesInfo() {
            messageText = "Инфо БТ";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Бытовая техника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("when command search answer should return appliances info")
        void whenCommandSearchAnswerShouldReturnAppliancesInfo() {
            messageText = "/appliances_info";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Бытовая техника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("search answer should return mobile info")
        void searchAnswerShouldReturnMobileInfo() {
            messageText = "Инфо Мобайл";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Мобильная электроника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("when command search answer should return mobile info")
        void whenCommandSearchAnswerShouldReturnMobileInfo() {
            messageText = "/mobile_info";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Мобильная электроника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("search answer should return TV info")
        void searchAnswerShouldReturnTVInfo() {
            messageText = "Инфо ТВ";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Мобильная электроника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("when command search answer should return TV info")
        void whenCommandSearchAnswerShouldReturnTVInfo() {
            messageText = "/tv_info";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Мобильная электроника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("search answer should return bonus question")
        void searchAnswerShouldReturnBonusQuestion() {
            messageText = "Программа Лояльности";
            expectedText = "Введите номер телефона или бонусной карты:";
            var listResult = (List<SendMessage>) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(listResult.get(listResult.size() - 1).getText()).contains(expectedText);
        }

        @Test
        @DisplayName("when command search answer should return bonus question")
        void whenCommandSearchAnswerShouldReturnBonusQuestion() {
            messageText = "/bonus_card";
            expectedText = "Введите номер телефона или бонусной карты:";
            var listResult = (List<SendMessage>) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(listResult.get(listResult.size() - 1).getText()).contains(expectedText);
        }


        @Test
        @DisplayName("search answer should return service info")
        void searchAnswerShouldReturnServiceInfo() {
            messageText = "Сервис";
            expectedText = "При обращении в ремонт – направьте клиента:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("when command search answer should return service info")
        void whenCommandSearchAnswerShouldReturnServiceInfo() {
            messageText = "/service";
            expectedText = "При обращении в ремонт – направьте клиента:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }
    }

    @Tag("speed")
    @Nested
    @DisplayName("optimal time execution test")
    class speedTest {

        @Test
        @DisplayName("search answer should return info appliances faster than two seconds")
        void searchAnswerShouldReturnInfoAppliancesFasterThanTwoSeconds() {
            messageText = "Инфо БТ";
            assertTimeout(Duration.ofSeconds(2), () -> {
                new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            });
        }

        @Test
        @DisplayName("search answer should return info bonus card when we now phone number faster than six seconds")
        void searchAnswerShouldReturnInfoBonusCardWhenWeNowPhoneNumberFasterThanSixSeconds() {
            messageText = "9999999911";
            assertTimeout(Duration.ofSeconds(9), () -> {
                new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            });
        }
    }
}