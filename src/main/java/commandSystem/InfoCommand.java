package commandSystem;

import bot.Device;
import java.util.ArrayList;
import java.util.List;

public class InfoCommand{

    public String create() {
        StringBuilder response = new StringBuilder();

        response.append("Список доступных для поиска устройств:\n\n");
        response.append("Мобильная электроника:");

        List<String> productNamesList = new ArrayList<>();

        productNamesList = new Device().getFilesName("src/main/resources/dataBaseProducts/mobile");

        for (String mobileName: productNamesList){
            response.append(mobileName + "\n");
        }



        response.append("\nТелевизоры:\n\n");

        productNamesList = new Device().getFilesName("src/main/resources/dataBaseProducts/tv");

        for (String tvName: productNamesList){
            response.append(tvName + "\n");
        }



        response.append("\nБытовая техника:\n\n");

        productNamesList = new Device().getFilesName("src/main/resources/dataBaseProducts/appliances");

        for (String applianceName: productNamesList){
            response.append(applianceName + "\n");
        }

        response.append("\nСписок устройств в процессе пополнения.\n");

        return response.toString();
    }
}
