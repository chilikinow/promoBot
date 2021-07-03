package commandSystem;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class StartCommand {

    public String create() {

        String separator = File.separator;

        List<String> seviceInfoStringList =  new ArrayList<>();
        Path serviceInfoFile = Paths.get("src" + separator + "main" + separator + "resources" + separator + "startInfo.txt");
        try {
            seviceInfoStringList = new ArrayList<>(Files.readAllLines(serviceInfoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder response = new StringBuilder();

        for (String serviceInfoLine: seviceInfoStringList) {
            response.append(serviceInfoLine + "\n");
        }

        return response.toString().trim();
    }

}
