package commandSystem;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceInfoCommand {


    public String create() {


        Path phonesInfoPropertiesFile = Paths.get("src/main/resources/phonesInfo.properties");
        Properties phonesInfoProperties = new Properties();
        try {
            phonesInfoProperties.load(new FileReader(phonesInfoPropertiesFile.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();

        for (String name : phonesInfoProperties.stringPropertyNames()) {
            map.put(name, phonesInfoProperties.getProperty(name));
        }

        StringBuilder response = new StringBuilder();
        response.append("При обращении в ремонт – направьте клиента:\n\n" +
                "- в коллцентр (КЦ) Самсунг 8-800-555-55-55\n 15 минут\n" +
                "- в Сервис Плазу или в АСЦ – ремонт за 1 час\n" +
                "- в бесплатную доставку смартфона через КЦ\n 3 дня\n\n");
        response.append("Обязательно предупредите клиента что:\n\n" +
                "- ремонт через магазин в среднем\n- 20 дней (до 45)\n" +
                "- клиенту нужно сохранить данные\n" +
                "и вызвать курьера\n" +
                "- не сделав этого не стоит сдавать\n" +
                "аппарат сейчас\n" +
                "- В Москве и Питере – доступен Мобильный сервис\n\n");
        response.append("Полезные ссылки:\n\n" +
                "Бесплатная доставка смартфонов\n" +
                "и портативной техники в сервис: \n https://www.samsung.com/ru/support/d2d/ \n\n" +
                "Новая фишка - мобильный сервисный центр! Samsung ремонтирует смартфоны в любом месте " +
                "(для Москвы и Санкт-Петербурга). \n https://www.samsung.com/ru/support/repair-van/ \n\n" +
                "Дистанционная настройка и диагностика техники, помощь по телефону и онлайн:\n" +
                "Все контакты службы поддержки Samsung: \n https://www.samsung.com/ru/info/contactus/ \n\n");
        response.append("Телефоны Поддержки:\n\n");
        for (Map.Entry<String, String> entry: map.entrySet()) {
            response.append(entry.getKey()+ ": ")
                    .append(entry.getValue()+"\n");
        }
        return response.toString().trim();

    }
}
