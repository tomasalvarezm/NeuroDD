package diagnosis;

import java.io.InputStream;
import java.util.Map;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;


public class SymptomWeight {

    private Map<String, Integer> alzheimer_weights;
    private Map<String, Integer> amyotrophic_lateral_sclerosis_weights;
    private Map<String, Integer> huntington_weights;
    private Map<String, Integer> multiple_sclerosis_weights;
    private Map<String, Integer> myasthenia_gravis_weights;
    private Map<String, Integer> parkinson_weights;

    public SymptomWeight(String pathname) {
        try {
            FileInputStream file = new FileInputStream(pathname);

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook wb = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet ws = wb.getSheetAt(1);
            //Iterate through each rows one by one
            //Start in rowIndex=2 in order to remove the first row that divides symptoms and the symptoms (cognitive,motor)...
            for (int rowIndex=3; rowIndex<=ws.getLastRowNum(); rowIndex++){
                Row row = ws.getRow(rowIndex);
                //For each row, iterate through all the columns
                //String disease=row.getCell(0).getStringCellValue();
                Iterator<Cell> cellIterator = row.cellIterator();
                int aux=0;
                Cell disease = row.getCell(0);
                //System.out.println(disease.getStringCellValue());
                switch (disease.getStringCellValue()){
                    case "Alzheimer":
                        this.alzheimer_weights = readingRows(ws,cellIterator);
                        break;

                    case "Amyotrophic lateral sclerosis":
                        this.amyotrophic_lateral_sclerosis_weights = readingRows(ws,cellIterator);
                        break;

                    case "Huntington":
                        this.huntington_weights = readingRows(ws,cellIterator);
                        break;

                    case "Multiple sclerosis":
                        this.multiple_sclerosis_weights = readingRows(ws,cellIterator);
                        break;

                    case "Myasthenia gravis":
                        this.myasthenia_gravis_weights = readingRows(ws,cellIterator);
                        break;

                    case "Parkinson":
                        this.parkinson_weights = readingRows(ws,cellIterator);
                        break;
                }
                System.out.println("\n");
            }
            file.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Map<String, Integer> readingRows (XSSFSheet ws, Iterator<Cell> cellIterator) {
        int aux = 1;
        Map<String, Integer> map = new HashMap<>();
        Cell start;
        int init_pos = 1;
        for (int i = 0; i < init_pos && cellIterator.hasNext(); i++) {
            cellIterator.next();
        }
        while (cellIterator.hasNext()) {
            start = cellIterator.next();

            // La primera celda la cojo en texto y el resto en número
            Integer weight_value = (int) start.getNumericCellValue();
            String symptom_name = ws.getRow(2).getCell(aux).getStringCellValue();

            // Agregar el síntoma y su peso al mapa
            map.put(symptom_name, weight_value);
            aux++;
        }
        return map;
    }



    public Map<String, Integer> getAlzheimer_weights() {
        return alzheimer_weights;
    }

    public Map<String, Integer> getAmyotrophic_lateral_sclerosis_weights() {
        return amyotrophic_lateral_sclerosis_weights;
    }

    public Map<String, Integer> getHuntington_weights() {
        return huntington_weights;
    }

    public Map<String, Integer> getMultiple_sclerosis_weights() {
        return multiple_sclerosis_weights;
    }

    public Map<String, Integer> getMyasthenia_gravis_weights() {
        return myasthenia_gravis_weights;
    }

    public Map<String, Integer> getParkinson_weights() {
        return parkinson_weights;
    }
}
