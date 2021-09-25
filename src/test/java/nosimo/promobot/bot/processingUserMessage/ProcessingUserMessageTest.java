package nosimo.promobot.bot.processingUserMessage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.time.Duration;
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
        expectedText = "Приветствую!\n" +
                "\n" +
                "Бот позволяет оперативно узнать актуальную информацию о проходящих в компании маркетинговых акциях, а так же узнать подробные характеристики запрашиваемой техники\n" +
                "\n" +
                "[ Акции Мобайл ТВ ]\n" +
                "\n" +
                "перечень актуальных акций\n" +
                "\n" +
                "- Смартфоны\n" +
                "- Планшеты\n" +
                "- Носимые устройства\n" +
                "\n" +
                "[ Акции Бытовая техника ]\n" +
                "\n" +
                "перечень актуальных акций\n" +
                "\n" +
                "- Стиральные машины\n" +
                "- Холодильники\n" +
                "- Пылесосы\n" +
                "- Микроволновки\n" +
                "- Духовые шкафы\n" +
                "- Варочные панели\n" +
                "\n" +
                "[ Характеристики устройств ]\n" +
                "\n" +
                "поиск технических характеристик и USP\n" +
                "\n" +
                "[ Инфо БТ ] [ Инфо Мобайл ] [ Инфо ТВ ]\n" +
                "\n" +
                "список устройств, доступных для быстрого поиска (постоянно пополняется)\n" +
                "\n" +
                "[ Программа Лояльности ]\n" +
                "\n" +
                "запрос информации по бонусной карте Клиента\n" +
                "\n" +
                "[ Сервис ]\n" +
                "\n" +
                "справочная информация Сервисных служб Samsung\n" +
                "\n" +
                "Стартовое меню: /start_menu";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertEquals(expectedText, result.getText());
    }

    @Test
    void searchAnswerShouldReturnStartMenuText() {
        messageText = "/start_menu";
        expectedText = "Стартовое меню: /start_menu";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).isEqualTo(expectedText);
    }

    @Test
    void whenCommandBackSearchAnswerShouldReturnStartMenuText() {
        messageText = "Назад";
        expectedText = "Стартовое меню: /start_menu";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).isEqualTo(expectedText);
    }

    @Test
    void searchAnswerShouldReturnPromoAppliancesText() {
        messageText = "Акции БТ";
        expectedText = "Список акций интернет магазина:\n" +
                "https://galaxystore.ru/promo/";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).isEqualTo(expectedText);
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
    void searchAnswerShouldReturnInfoBT() {
        messageText = "Инфо БТ";
        expectedText = "Список доступных для поиска устройств:\n" +
                "\n" +
                "Бытовая техника:\n" +
                "\n" +
                "RB3000 RB33J\n" +
                "RB5000 RB37J\n" +
                "RB6000 RB34K:RB37K\n" +
                "RB:RF:RH:RS:RT:RR:RZ:RQ:BRB\n" +
                "\n" +
                "Список устройств в процессе пополнения...\n" +
                "\n" +
                "Стартовое меню: /start_menu";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).isEqualTo(expectedText);
    }

    @Test
    void whenCommandsearchAnswerShouldReturnInfoBT() {
        messageText = "/appliances_info";
        expectedText = "Список доступных для поиска устройств:\n" +
                "\n" +
                "Бытовая техника:\n" +
                "\n" +
                "RB3000 RB33J\n" +
                "RB5000 RB37J\n" +
                "RB6000 RB34K:RB37K\n" +
                "RB:RF:RH:RS:RT:RR:RZ:RQ:BRB\n" +
                "\n" +
                "Список устройств в процессе пополнения...\n" +
                "\n" +
                "Стартовое меню: /start_menu";
        result = (SendMessage) new ProcessingUserMessage().searchAnswer(chatId, username, messageText);
        assertThat(result.getText()).isEqualTo(expectedText);
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