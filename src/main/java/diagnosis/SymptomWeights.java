package diagnosis;

import java.util.Map;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;


public class SymptomWeights {
    public final int max_score_alzheimer;
    public final int max_score_amyotrophic_lateral_sclerosis;
    public final int max_score_huntington;
    public final int max_score_multiple_sclerosis;
    public final int max_score_myasthenia_gravis;
    public final int max_score_parkinson;

    private Map<Prueba, Integer> alzheimer_weights;
    private Map<Prueba, Integer> amyotrophic_lateral_sclerosis_weights;
    private Map<Prueba, Integer> huntington_weights;
    private Map<Prueba, Integer> multiple_sclerosis_weights;
    private Map<Prueba, Integer> myasthenia_gravis_weights;
    private Map<Prueba, Integer> parkinson_weights;

    public SymptomWeights(String pathname) {
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
            }
            file.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.max_score_alzheimer=calculateMaxScore(this.alzheimer_weights);
        this.max_score_huntington=calculateMaxScore(this.huntington_weights);
        this.max_score_amyotrophic_lateral_sclerosis=calculateMaxScore(this.amyotrophic_lateral_sclerosis_weights);
        this.max_score_myasthenia_gravis=calculateMaxScore(this.myasthenia_gravis_weights);
        this.max_score_parkinson=calculateMaxScore(this.parkinson_weights);
        this.max_score_multiple_sclerosis=calculateMaxScore(this.multiple_sclerosis_weights);
    }

    public static Map<Prueba, Integer> readingRows (XSSFSheet ws, Iterator<Cell> cellIterator) {
        int aux = 1;
        Map<Prueba, Integer> map = new HashMap<>();
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
            if (symptom_name.contains("(") && symptom_name.contains(")")) {
                // Eliminar los paréntesis y su contenido
                symptom_name = symptom_name.replaceAll("\\(.*?\\)", "").trim();
                System.out.println(symptom_name);
            }
            Prueba symptom = Prueba.valueOf(symptom_name.replace(" ","_").toUpperCase());
            // Agregar el síntoma y su peso al mapa
            map.put(symptom, weight_value);
            aux++;
        }
        return map;
    }

    private int calculateMaxScore (Map <Prueba, Integer> disease_weights){
        int maxScore=0;
        for(int score: disease_weights.values()){
            maxScore += score;
        }
        return maxScore;
    }



    public Map<Prueba, Integer> getAlzheimer_weights() {
        return alzheimer_weights;
    }

    public Map<Prueba, Integer> getAmyotrophic_lateral_sclerosis_weights() {
        return amyotrophic_lateral_sclerosis_weights;
    }

    public Map<Prueba, Integer> getHuntington_weights() {
        return huntington_weights;
    }

    public Map<Prueba, Integer> getMultiple_sclerosis_weights() {
        return multiple_sclerosis_weights;
    }

    public Map<Prueba, Integer> getMyasthenia_gravis_weights() {
        return myasthenia_gravis_weights;
    }

    public Map<Prueba, Integer> getParkinson_weights() {
        return parkinson_weights;
    }


}
