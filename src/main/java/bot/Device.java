package bot;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;

public class Device {

    private Set<String> categoryDeviceList;

    public Set<String> getCategoryDeviceList(){

        String separator = File.separator;
        String directory = "src" + separator + "main" + separator + "resources" + separator + "categoryDeviceForFind.txt";
        List<String> tempCategoryList = new ArrayList<>();
        try {
            tempCategoryList = new ArrayList<>(Files.readAllLines(Paths.get(directory)));
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

    public Set<Path> findInfo(Message message, String directory){

        Path directoryPath = Paths.get(directory);

        String messageText = message.getText()
                .toLowerCase(Locale.ROOT)
                .replaceAll(" ", "")
                .replace("galaxy", "")
                .replace("samsung", "")
                .replace("-", "")
                .replace("_", "")
                .replace("plus", "\\+")
                .replace("+", "\\+");

        Set<Path> deviceInfoFilesList = new TreeSet<>();

        try {
            deviceInfoFilesList =  Files.walk(directoryPath)
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
                    .replace("galaxy", "")
                    .replace("samsung", "")
                    .replace("-", "")
                    .replace("_", "")
                    .replace("plus", "+");
            Pattern pattern = Pattern.compile(messageText);
            Matcher matcher = pattern.matcher(tempDeviceInfoFile);
            if (matcher.find()){
                resultDeviceInfoList.add(deviceInfoFile);
            }
        }

        return resultDeviceInfoList;
    }

    public Set<String> getFilesName(String directory){

        Path directoryPath = Paths.get(directory);

        Set<Path> filesList = new TreeSet<>();

        try {
            filesList =  Files.walk(directoryPath)
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
