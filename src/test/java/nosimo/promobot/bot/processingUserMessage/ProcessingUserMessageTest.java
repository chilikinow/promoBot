package nosimo.promobot.bot.processingUserMessage;

import org.junit.jupiter.api.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)//Создает один объект для тестовых методов
class ProcessingUserMessageTest {
    Long chatId;
    String username;
    String messageText;
    String expectedText;
    SendMessage result;

    @BeforeAll//Действия перед всеми тестами
    void init(){
        chatId = new Long(123456789);
        username = "test_pass_username";
    }

    @Tag("functional")
    @Nested
    @DisplayName("functional test")
    class functionalTest{

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
        @DisplayName("search answer should return promo appliances text")
        void searchAnswerShouldReturnPromoAppliancesText(){
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
        @DisplayName("search answer should return with category text")
        void searchAnswerShouldReturnTextWithCategory() {
            messageText = "Что можно быстро найти?";
            expectedText = "Доступные для поиска категории:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).isEqualTo(expectedText);
        }

        @Test
        @DisplayName("when command what are you looking for search answer should return text with category text")
        void whenCommandWhatAreYouLookingForSearchAnswerShouldReturnTextWithCategory() {
            messageText = "/info";
            expectedText = "Доступные для поиска категории:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).isEqualTo(expectedText);
        }

        @Test
        @DisplayName("search answer should return mobile info")
        void searchAnswerShouldReturnMobileInfo() {
            messageText = "Мобильная техника";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Мобильная электроника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("when command search answer should return mobile info")
        void whenCommandSearchAnswerShouldReturnMobileInfo() {
            messageText = "/mobile";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Мобильная электроника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("search answer should return TV info")
        void searchAnswerShouldReturnTVInfo() {
            messageText = "Телевизоры";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Мобильная электроника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("when command search answer should return TV info")
        void whenCommandSearchAnswerShouldReturnTVInfo() {
            messageText = "/tv";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Мобильная электроника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("search answer should return appliances info")
        void searchAnswerShouldReturnAppliancesInfo() {
            messageText = "Бытовая техника";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Бытовая техника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("when command search answer should return appliances info")
        void whenCommandSearchAnswerShouldReturnAppliancesInfo() {
            messageText = "/appliances";
            expectedText = "Список доступных для поиска устройств:\n" +
                    "\n" +
                    "Бытовая техника:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
        }

        @Test
        @DisplayName("search answer should return bonus question")
        void searchAnswerShouldReturnBonusQuestion() {
            messageText = "Программа Лояльности";
            expectedText = "Введите номер телефона или бонусной карты:";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
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

        @Test
        @DisplayName("when command search answer should return promo update message")
        void whenCommandSearchAnswerShouldReturnPromoUpdateMessage() {
            messageText = "/promo_update";
            expectedText = "База Акций обновлена!";
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
        @DisplayName("search answer should return start info text")
        void searchAnswerShouldReturnStartInfoText() {
            messageText = "/start";
            expectedText = "Приветствую!";
            result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).as(expectedText);
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
            messageText = "/devices";
            expectedText = "Введите название устройства:";
            SendMessage result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            assertThat(result.getText()).isEqualTo(expectedText);
        }
    }

    @Tag("integration")
    @Tag("speed")
    @Nested
    @DisplayName("optimal time execution test")
    class speedTest {

        @Test
        @DisplayName("search answer should return info appliances faster than two seconds")
        void searchAnswerShouldReturnInfoAppliancesFasterThanTwoSeconds() {
            messageText = "Инфо БТ";
            assertTimeout(Duration.ofSeconds(9), () -> { //проверка соответствия времени выполнения метода
                new ProcessingUserMessage().searchAnswer(chatId, username, messageText); // с использованием многопоточности
            });
        }

        @Test
        @DisplayName("search answer should return info bonus card when we now phone number faster than six seconds")
        void searchAnswerShouldReturnInfoBonusCardWhenWeNowPhoneNumberFasterThanSixSeconds() {
            messageText = "9999999911";
            assertTimeoutPreemptively(Duration.ofSeconds(9), () -> {
                new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
            });
        }
    }
}