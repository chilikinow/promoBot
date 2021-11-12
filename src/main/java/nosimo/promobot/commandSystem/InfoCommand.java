package nosimo.promobot.commandSystem;

import nosimo.promobot.bot.DeviceInfo;

import java.nio.file.Path;
import java.util.List;

public class InfoCommand{

    public String create(String heading, Path directory, String ending) {

        StringBuilder response = new StringBuilder();
        response.append(heading);
        List<String> productNamesList = new DeviceInfo().getFilesName(directory);

        for (String mobileName: productNamesList){
            response.append(mobileName + "\n");
        }

        response.append(ending);

        return response.toString();
    }
}
