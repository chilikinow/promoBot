package bot;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;

public class Device {

    private List<String> categoryDeviceList;

    public List<String> getCategoryDeviceList(){

        this.categoryDeviceList = new ArrayList<>();

        this.categoryDeviceList.add("galaxy");
        this.categoryDeviceList.add("s");
        this.categoryDeviceList.add("note");
        this.categoryDeviceList.add("z");
        this.categoryDeviceList.add("fold");
        this.categoryDeviceList.add("flip");
        this.categoryDeviceList.add("a");
        this.categoryDeviceList.add("tab");
        this.categoryDeviceList.add("buds");
        this.categoryDeviceList.add("smart");
        this.categoryDeviceList.add("watch");
        this.categoryDeviceList.add("active");
        this.categoryDeviceList.add("rb");
        this.categoryDeviceList.add("rs");
        this.categoryDeviceList.add("brb");
        this.categoryDeviceList.add("ww");
        this.categoryDeviceList.add("wd");
        this.categoryDeviceList.add("dv");
        this.categoryDeviceList.add("jet");
        this.categoryDeviceList.add("vc");
        this.categoryDeviceList.add("vr");

        return categoryDeviceList;
    }

    public List<Path> findInfo(Message message, String directory){

        Path directoryPath = Paths.get(directory);

        String messageText = message.getText().toLowerCase(Locale.ROOT).replaceAll(" ", "");

        messageText = messageText.replace("galaxy", "");

        messageText = messageText.replace("samsung", "");

        messageText = messageText.replace("plus", "+");

        List<Path> deviceInfoFilesList = new ArrayList<>();

        try {
            deviceInfoFilesList =  Files.walk(directoryPath)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Path> resultDeviceInfoList = new ArrayList<>();
        for (Path deviceInfoFile : deviceInfoFilesList){
            Pattern pattern = Pattern.compile(messageText);
            Matcher matcher = pattern.matcher(deviceInfoFile.getFileName().toString());
            if (matcher.find()){
                resultDeviceInfoList.add(deviceInfoFile);
            }
        }

        return resultDeviceInfoList;
    }

    public List<String> getFilesName(String directory){

        Path directoryPath = Paths.get(directory);

        List<Path> filesList = new ArrayList<>();

        try {
            filesList =  Files.walk(directoryPath)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> filesNamesList = new ArrayList<>();

        for(Path deviceInfo : filesList){
            String bufferFileName = deviceInfo.getFileName().toString();
//            bufferFileName = bufferFileName.replaceFirst("[.][^.]+$", "");
            bufferFileName = FilenameUtils.removeExtension(bufferFileName);
            filesNamesList.add(bufferFileName);
        }

        return filesNamesList;
    }
}
