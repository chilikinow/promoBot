package commandSystem;

import bot.Device;
import java.util.Set;
import java.util.TreeSet;

public class InfoCommand{

    public String create(String heading, String directory, String ending) {

        StringBuilder response = new StringBuilder();
        response.append(heading);
        Set<String> productNamesList = new TreeSet<>();
        productNamesList = new Device().getFilesName(directory);

        for (String mobileName: productNamesList){
            response.append(mobileName + "\n");
        }

        response.append(ending);

        return response.toString();
    }
}
