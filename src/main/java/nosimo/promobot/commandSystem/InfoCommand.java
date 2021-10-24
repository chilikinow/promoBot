package nosimo.promobot.commandSystem;

import nosimo.promobot.bot.DeviceInfoDAO;

import java.nio.file.Path;
import java.util.Set;

public class InfoCommand{

    public String create(String heading, Path directory, String ending) {

        StringBuilder response = new StringBuilder();
        response.append(heading);
        Set<String> productNamesList;
        productNamesList = new DeviceInfoDAO().getFilesName(directory);

        for (String mobileName: productNamesList){
            response.append(mobileName + "\n");
        }

        response.append(ending);

        return response.toString();
    }
}
