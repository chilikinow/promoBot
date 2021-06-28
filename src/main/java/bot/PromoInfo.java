package bot;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PromoInfo {

    private static Calendar date;
    private static Map <String, String> promoMobileTVMap;
    private static Map <String, String> promoAppliancesMap;
    private static XSSFWorkbook workBook;

    private PromoInfo(){
    }

    static {
        date = Calendar.getInstance();
        promoMobileTVMap = new HashMap<>();
        promoAppliancesMap = new HashMap<>();

        String separator = File.separator;
        Path PromoFilePath = Paths.get("src" + separator + "main" + separator + "resources" + separator + "Samsung_Календарь акций.xlsx");
        workBook = null;
        try (FileInputStream fIS = new FileInputStream(PromoFilePath.toFile())) {
            workBook = new XSSFWorkbook(fIS);//получили книгу exel
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
        } catch (IOException e) {
            System.out.println("Файл не читается.");
        }
    }

    //Singleton
    public static Map<String, String> getInstancePromoMobileTV(){
        Calendar currentDate = Calendar.getInstance();
        if (promoMobileTVMap.isEmpty() || date.get(Calendar.DATE) != currentDate.get(Calendar.DATE)) {
            promoMobileTVMap = new HashMap<>(addMap(workBook,0));
            date = currentDate;
        }

        return promoMobileTVMap;
    }

    //Singleton
    public static Map<String, String> getInstancePromoAppliances(){
        Calendar currentDate = Calendar.getInstance();
        if (promoAppliancesMap.isEmpty() || date.get(Calendar.DATE) != currentDate.get(Calendar.DATE)) {
            promoAppliancesMap = new HashMap<>(addMap(workBook,1));
            date = currentDate;
        }

        return promoAppliancesMap;
    }

    private static Map<String, String> addMap(XSSFWorkbook workBook, int sheetNumber) {

        //получили страницу книги
        XSSFSheet sheet = workBook.getSheetAt(sheetNumber);

        //получаем лист строк
        ArrayList<Row> rowList = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.rowIterator(); //формируем список строк из страницы
        while (rowIterator.hasNext()){
            Row bufferRow = rowIterator.next();
            if(!bufferRow.getCell(0).getStringCellValue().equals("")) {  //за исключением пустых
                rowList.add(bufferRow);
            }
        }

        //перебираем лист строк без первой строки, где хронятся названия столбцов
        ArrayList<Cell> cellList = new ArrayList<>();
        StringBuilder valueForMap = new StringBuilder();

        Map<String, String> map = new HashMap<>();

        for (int i = 1; i < rowList.size(); i++) {
            cellList = new ArrayList<>();
            Iterator<Cell> cellIterator = rowList.get(i).iterator();
            Cell bufferCell;
            while (cellIterator.hasNext()){  //формируем лист ячеек из строки
                bufferCell = cellIterator.next();
                if(!bufferCell.getStringCellValue().equals("")) {  //за исключением пустых
                    cellList.add(bufferCell);
                }
            }

            //переменная для формирования информации со всех ячеек (подробности акции) кроме названия акции
            valueForMap = new StringBuilder();
            for (int j = 1; j < cellList.size(); j++) {
                String cellString = cellList.get(j).getStringCellValue();
                    valueForMap.append(cellString + "\n");
            }
            //создаем элемент Map, где key- название акции, value- подробности акции
            //key очищается от лишних пробелов
            map.put(cellList.get(0).getStringCellValue().trim(), valueForMap.toString());
        }


//            блок для проверки вывода Map в консоль
//            for (Map.Entry<String, String> entry: map.entrySet()){
//                StringBuilder mapToString = new StringBuilder();
//                mapToString.append(entry.getKey())
//                        .append("\n\n")
//                        .append(entry.getValue());
//                System.out.println(mapToString.toString());
//                System.out.println("------------------------------");
//            }
//            System.out.println("_________End Map_________\n\n\n");

//        for (map.keySet())


        return map;
    }
}