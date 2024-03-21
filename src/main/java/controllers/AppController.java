package controllers;

import diagnosis.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
//import org.kie.api.KieServices;
//import org.kie.api.runtime.KieContainer;

import java.time.LocalDate;
import java.util.ArrayList;


public class AppController {
   
    public Button diagnose_btn;
    public CheckBox bentPosture, bradykinesia, difficultyBreathing, difficultySwallowing, diplopia;
    public CheckBox facialMovements, hypomimia, inabilityToMove, involvementVoluntarySkeletalMuscles;
    public CheckBox lackOfCoordination, muscleCramps, muscleStiffness, muscleWeakness, painFlexingUpperLimb;
    public CheckBox partialOrCompleteParalysis, tingling, ptosis, reducedBlinking, tremor, unsteadyGait;
    public CheckBox anxietyStressTension, behavioralDisturbances, delusions, depression;
    public CheckBox emotionalIncontinence, hallucinations, irritability, psychosis, socialIsolation;
    public CheckBox sleepDisturbance, communicationDifficulty, confusion, difficultyLearning;
    public CheckBox difficultyProblemSolving, inabilityPerformRoutineActivities, incompleteVoiding;
    public CheckBox lackOfAttention, memoryImpairment, orientationImpairment, scanningSpeech;
    public CheckBox blurredVision, constipation, delirium, excessiveSalivation, hyperesthesia;
    public CheckBox hyperreflexia, increasedUrination, lossGagReflex, opticNeuritis;
    public CheckBox respiratoryCompromise, seizures, spasms, urinaryIncontinence, weightLoss;
    public TextField searchTextMotor, searchTextCognitive, searchTextPsychiatric, searchTextOthers;
    public Label lbl;
    public Label diagnosisMessageAlert;

    public void handleButtonDiagnose(ActionEvent actionEvent) {

//        KieServices ks = KieServices.Factory.get();
//        KieContainer kc = ks.getKieClasspathContainer();
//        KieSession ksession = kc.newKieSession("NAME OF THE KSESSION IN KMODULE");

        LocalDate date_of_birth = LocalDate.now();
        Patient patient = new Patient("1", "Patient 1", 20, date_of_birth, Sex.FEMALE);

        ArrayList<CheckBox> allCheckBoxes = new ArrayList<>();
        allCheckBoxes.addAll(getCheckBoxes(searchTextMotor, 2));
        allCheckBoxes.addAll(getCheckBoxes(searchTextCognitive, 5));
        allCheckBoxes.addAll(getCheckBoxes(searchTextPsychiatric, 8));
        allCheckBoxes.addAll(getCheckBoxes(searchTextOthers, 11));

        for (CheckBox checkBox : allCheckBoxes){
            if (checkBox.isSelected()){
                Symptom symptom = new Symptom(checkBox.getText().toLowerCase().trim());
                patient.addSymptom(symptom);
            }
        }
        System.out.println(patient);

        // ksession.insert(patient);

        // ksession.fireAllRules();
        // ksession.dispose();

        String pathname = "C:\\Users\\User\\Documents\\Universidad\\4 ANO\\DSS\\NeuroDD\\src\\main\\resources\\symptom_weights\\Symptoms_DSS.xlsx";
        SymptomWeight symptomWeight = new SymptomWeight(pathname);
//        System.out.println(symptomWeight.getAlzheimer_weights());
//        System.out.println(symptomWeight.getHuntington_weights());
//        System.out.println(symptomWeight.getAmyotrophic_lateral_sclerosis_weights());
//        System.out.println(symptomWeight.getMyasthenia_gravis_weights());
//        System.out.println(symptomWeight.getMultiple_sclerosis_weights());
//        System.out.println(symptomWeight.getParkinson_weights());


        patient.calculateDiseaseScore(symptomWeight.getAlzheimer_weights(), "alzheimer");
        patient.calculateDiseaseScore(symptomWeight.getAmyotrophic_lateral_sclerosis_weights(), "amyotrophic lateral sclerosis");
        patient.calculateDiseaseScore(symptomWeight.getHuntington_weights(), "huntington");
        patient.calculateDiseaseScore(symptomWeight.getMultiple_sclerosis_weights(), "multiple sclerosis");
        patient.calculateDiseaseScore(symptomWeight.getMyasthenia_gravis_weights(), "myasthenia gravis");
        patient.calculateDiseaseScore(symptomWeight.getParkinson_weights(), "parkinson");

        System.out.println(patient);

        diagnosisMessageAlert.setVisible(true);

    }

    public ArrayList<CheckBox> getCheckBoxes(TextField searchText, int scrollableIndex){
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();

        // Obtener el scrollable que hace referencia el searchText
        ScrollPane scrollPane = (ScrollPane) searchText.getParent().getParent().getChildrenUnmodifiable().get(scrollableIndex);

        // Obtener el contenido del ScrollPane, que es el VBox
        VBox vbox = (VBox) scrollPane.getContent();
        // Iterar sobre los nodos hijos del VBox
        for (Node node : vbox.getChildren()){
            CheckBox checkBox = (CheckBox) node;
            checkBoxes.add(checkBox);
        }
        return checkBoxes;
    }

    public void filterMotorSymptoms(KeyEvent keyEvent) {

        // Obtener el texto ingresado en el TextField de búsqueda
        String searchText = searchTextMotor.getText().toLowerCase().trim();

        // Obtener los checkboxes asociados a motor symptoms
        ArrayList<CheckBox> checkBoxes = getCheckBoxes(searchTextMotor, 2);

        // Iterar sobre la lista de checkboxes
        for (CheckBox checkBox : checkBoxes){
            // Mostrar u ocultar según el texto de búsqueda
            String checkBoxText = checkBox.getText().toLowerCase().trim();
            checkBox.setVisible(checkBoxText.contains(searchText));
        }

        // Si el TextField de búsqueda está vacío, restaurar la visibilidad de todos los CheckBox
        if (searchText.isEmpty()){
            for (CheckBox checkBox : checkBoxes){
                checkBox.setVisible(true);
            }
        }
    }

    public void filterCognitiveSymptoms(KeyEvent keyEvent) {

        // Obtener el texto ingresado en el TextField de búsqueda
        String searchText = searchTextCognitive.getText().toLowerCase().trim();

        // Obtener los checkboxes asociados a motor symptoms
        ArrayList<CheckBox> checkBoxes = getCheckBoxes(searchTextCognitive, 5);

        // Iterar sobre la lista de checkboxes
        for (CheckBox checkBox : checkBoxes){
            // Mostrar u ocultar según el texto de búsqueda
            String checkBoxText = checkBox.getText().toLowerCase().trim();
            checkBox.setVisible(checkBoxText.contains(searchText));
        }

        // Si el TextField de búsqueda está vacío, restaurar la visibilidad de todos los CheckBox
        if (searchText.isEmpty()){
            for (CheckBox checkBox : checkBoxes){
                checkBox.setVisible(true);
            }
        }

    }

    public void filterPsychiatricSymptoms(KeyEvent keyEvent) {

        // Obtener el texto ingresado en el TextField de búsqueda
        String searchText = searchTextPsychiatric.getText().toLowerCase().trim();

        // Obtener los checkboxes asociados a motor symptoms
        ArrayList<CheckBox> checkBoxes = getCheckBoxes(searchTextPsychiatric, 8);

        // Iterar sobre la lista de checkboxes
        for (CheckBox checkBox : checkBoxes){
            // Mostrar u ocultar según el texto de búsqueda
            String checkBoxText = checkBox.getText().toLowerCase().trim();
            checkBox.setVisible(checkBoxText.contains(searchText));
        }

        // Si el TextField de búsqueda está vacío, restaurar la visibilidad de todos los CheckBox
        if (searchText.isEmpty()){
            for (CheckBox checkBox : checkBoxes){
                checkBox.setVisible(true);
            }
        }

    }

    public void filterOtherSymptoms(KeyEvent keyEvent) {

        // Obtener el texto ingresado en el TextField de búsqueda
        String searchText = searchTextOthers.getText().toLowerCase().trim();

        // Obtener los checkboxes asociados a motor symptoms
        ArrayList<CheckBox> checkBoxes = getCheckBoxes(searchTextOthers, 11);

        // Iterar sobre la lista de checkboxes
        for (CheckBox checkBox : checkBoxes){
            // Mostrar u ocultar según el texto de búsqueda
            String checkBoxText = checkBox.getText().toLowerCase().trim();
            checkBox.setVisible(checkBoxText.contains(searchText));
        }

        // Si el TextField de búsqueda está vacío, restaurar la visibilidad de todos los CheckBox
        if (searchText.isEmpty()){
            for (CheckBox checkBox : checkBoxes){
                checkBox.setVisible(true);
            }
        }

    }

    public void HideDiagnosisAlert(Event event) {
        diagnosisMessageAlert.setVisible(false);
    }
}
