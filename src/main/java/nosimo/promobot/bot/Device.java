package nosimo.promobot.bot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import nosimo.promobot.bot.botData.BotData;
import org.apache.commons.io.FilenameUtils;

public class Device {

    private Set<String> categoryDeviceList;

    public Set<String> getCategoryDeviceList(){

        Path directory = BotData.outResources.resolve("categoryDeviceForFind.txt");

//        String directory = Paths.get(".")
//                .toAbsolutePath()
//                .normalize()
//                .getParent()
//                .resolve("outResources")
//                .resolve("categoryDeviceForFind.txt").toString();
//
//        if (!Files.exists(Paths.get(directory))) {
//            String separator = File.separator;
//            directory = "src" + separator + "main" + separator + "resources" + separator + "categoryDeviceForFind.txt";
//        }

        List<String> tempCategoryList = new ArrayList<>();
        try {
            tempCategoryList = new ArrayList<>(Files.readAllLines(directory));
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.categoryDeviceList = new TreeSet<>();

        for (String tempCategoryName: tempCategoryList){
            if (!tempCategoryName.equals("")) {
                this.categoryDeviceList.add(tempCategoryName.toLowerCase(Locale.ROOT));
            }
        }

        return categoryDeviceList;
    }

    public Set<Path> findInfo(String messageText, Path directory){

        messageText = messageText
                .toLowerCase(Locale.ROOT)
                .replaceAll(" ", "")
                .replaceAll("galaxy", "")
                .replaceAll("samsung", "")
                .replaceAll("-", "")
                .replaceAll("_", "")
                .replaceAll("plus", "\\+")
                .replace("+", "\\+");

        Set<Path> deviceInfoFilesList = new TreeSet<>();

        try {
            deviceInfoFilesList = Files.walk(directory)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<Path> resultDeviceInfoList = new TreeSet<>();
        for (Path deviceInfoFile : deviceInfoFilesList){
            String tempDeviceInfoFile = deviceInfoFile.getFileName().toString();
            tempDeviceInfoFile = tempDeviceInfoFile
                    .toLowerCase(Locale.ROOT)
                    .replaceAll(" ", "")
                    .replace("-", "")
                    .replace("_", "");
            Pattern pattern = Pattern.compile(messageText);
            Matcher matcher = pattern.matcher(tempDeviceInfoFile);
            if (matcher.find()){
                resultDeviceInfoList.add(deviceInfoFile);
            }
        }

        return resultDeviceInfoList;
    }

    public Set<String> getFilesName(Path directory){

        Set<Path> filesList = new TreeSet<>();

        try {
            filesList =  Files.walk(directory)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toSet());

        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> filesNamesList = new TreeSet<>();

        for(Path deviceInfo : filesList){
            String bufferFileName = deviceInfo.getFileName().toString();
//            bufferFileName = bufferFileName.replaceFirst("[.][^.]+$", "");
            bufferFileName = FilenameUtils.removeExtension(bufferFileName);
            if (!bufferFileName.equals(""))
                filesNamesList.add(bufferFileName);
        }

        return filesNamesList;
    }
}
