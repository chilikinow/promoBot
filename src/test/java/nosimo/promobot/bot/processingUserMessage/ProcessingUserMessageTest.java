package nosimo.promobot.bot.processingUserMessage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

    @Test
    void searchAnswerShouldReturnStartInfoText() {
        messageText = "/start";
        expectedText = "Приветствую!";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

    @Test
    void searchAnswerShouldReturnStartMenuText() {
        messageText = "/start_menu";
        expectedText = "Стартовое меню: /start_menu";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

    @Test
    void whenCommandBackSearchAnswerShouldReturnStartMenuText() {
        messageText = "Назад";
        expectedText = "Стартовое меню: /start_menu";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

    @Test
    void searchAnswerShouldReturnPromoAppliancesText() {
        messageText = "Акции БТ";
        expectedText = "Список акций интернет магазина:\n" +
                "https://galaxystore.ru/promo/";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

    @Test
    void whenCommandPromoAppliancesSearchAnswerShouldReturnPromoAppliancesText() {
        messageText = "/promo_appliances";
        expectedText = "Список акций интернет магазина:\n" +
                "https://galaxystore.ru/promo/";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).isEqualTo(expectedText);
    }

    @Test
    void searchAnswerShouldReturnPromoMobileTVText() {
        messageText = "Акции Мобайл ТВ";
        expectedText = "Список акций интернет магазина:\n" +
                "https://galaxystore.ru/promo/";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).isEqualTo(expectedText);
    }

    @Test
    void whenCommandPromoMobileTVSearchAnswerShouldReturnPromoMobileTVText(){
        messageText = "/promo_mobile_tv";
        expectedText = "Список акций интернет магазина:\n" +
                "https://galaxystore.ru/promo/";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).isEqualTo(expectedText);
    }

    @Test
    void searchAnswerShouldReturnDeviseInfoText(){
        messageText = "Характеристики устройств";
        expectedText = "Введите название устройства:";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).isEqualTo(expectedText);
    }

    @Test
    void whenCommandDeviseInfoSearchAnswerShouldReturnDeviseInfoText() {
        messageText = "/device_info";
        expectedText = "Введите название устройства:";
        SendMessage result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).isEqualTo(expectedText);
    }

    @Test
    void searchAnswerShouldReturnAppliancesInfo() {
        messageText = "Инфо БТ";
        expectedText = "Список доступных для поиска устройств:\n" +
                "\n" +
                "Бытовая техника:";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

    @Test
    void whenCommandsearchAnswerShouldReturnAppliancesInfo() {
        messageText = "/appliances_info";
        expectedText = "Список доступных для поиска устройств:\n" +
                "\n" +
                "Бытовая техника:";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

    @Test
    void searchAnswerShouldReturnMobileInfo() {
        messageText = "Инфо Мобайл";
        expectedText = "Список доступных для поиска устройств:\n" +
                "\n" +
                "Мобильная электроника:";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

    @Test
    void whenCommandsearchAnswerShouldReturnMobileInfo() {
        messageText = "/mobile_info";
        expectedText = "Список доступных для поиска устройств:\n" +
                "\n" +
                "Мобильная электроника:";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

    @Test
    void searchAnswerShouldReturnTVInfo() {
        messageText = "Инфо ТВ";
        expectedText = "Список доступных для поиска устройств:\n" +
                "\n" +
                "Мобильная электроника:";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

    @Test
    void whenCommandsearchAnswerShouldReturnTVInfo() {
        messageText = "/tv_info";
        expectedText = "Список доступных для поиска устройств:\n" +
                "\n" +
                "Мобильная электроника:";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

    @Test
    void searchAnswerShouldReturnBonusQuestion() {
        messageText = "Программа Лояльности";
        expectedText = "Введите номер телефона или бонусной карты:";
        var listResult = (List<SendMessage>) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(listResult.get(listResult.size()-1).getText()).contains(expectedText);
    }

    @Test
    void whenCommandsearchAnswerShouldReturnBonusQuestion() {
        messageText = "/bonus_card";
        expectedText = "Введите номер телефона или бонусной карты:";
        var listResult = (List<SendMessage>) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(listResult.get(listResult.size()-1).getText()).contains(expectedText);
    }

    @Test
    void searchAnswerShouldReturnServiceInfo() {
        messageText = "Сервис";
        expectedText = "При обращении в ремонт – направьте клиента:";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

    @Test
    void whenCommandsearchAnswerShouldReturnServiceInfo() {
        messageText = "/service";
        expectedText = "При обращении в ремонт – направьте клиента:";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).as(expectedText);
    }

//    @Test
//    void searchAnswerShouldReturn() {
//        String messageText = "";
//        String expectedText = "";
//        SendMessage result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
//        assertThat(result.getText()).isEqualTo(expectedText);
//    }

    @Test
    void searchAnswerShouldReturnInfoBTFasterThanTwoSeconds() {
        messageText = "Инфо БТ";
        assertTimeout(Duration.ofSeconds(2), () -> {
            new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        });
    }

    @Test
    void searchAnswerShouldReturnInfoBonusCardWhenWeNowPhoneNumberFasterThanSixSeconds() {
        messageText = "9999999911";
        assertTimeout(Duration.ofSeconds(9), () -> {
            new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        });
    }
}