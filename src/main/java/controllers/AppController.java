package controllers;

import diagnosis.*;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
//import org.kie.api.KieServices;
//import org.kie.api.runtime.KieContainer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AppController implements Initializable {


    public Patient patient;
    public Button diagnose_btn, save_info_btn;
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
    public Label diagnosis_lbl, diagnosisMessageAlert;
    public TextField dni_txt, name_txt;
    public ChoiceBox sex_box;
    public DatePicker date_picker;
    public ImageView ok_img;
    PatientUnit patientUnit;
    RuleUnitInstance<PatientUnit> instance;


    public void handleButtonSaveInfo(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        if (dni_txt.getText().isEmpty()){
            alert.setContentText("Introduced your DNI");
            alert.showAndWait();
        } else if (name_txt.getText().isEmpty()){
            alert.setContentText("Introduced your full name");
            alert.showAndWait();
        } else {
            ok_img.setOpacity(1);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), ok_img);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            Sex sex;
            if (sex_box.getValue().toString().equals("Male")){
                sex = Sex.MALE;
            } else {
                sex = Sex.FEMALE;
            }
            fadeTransition.playFromStart();
            patient = new Patient(dni_txt.getText(), name_txt.getText(), 18, date_picker.getValue(), sex);
        }
    }

    public void handleButtonDiagnose(ActionEvent actionEvent) {

        if (patient == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Introduced patient data first");
            alert.showAndWait();
        } else {
            ArrayList<CheckBox> allCheckBoxes = new ArrayList<>();
            allCheckBoxes.addAll(getCheckBoxes(searchTextMotor, 2));
            allCheckBoxes.addAll(getCheckBoxes(searchTextCognitive, 5));
            allCheckBoxes.addAll(getCheckBoxes(searchTextPsychiatric, 8));
            allCheckBoxes.addAll(getCheckBoxes(searchTextOthers, 11));

            for (CheckBox checkBox : allCheckBoxes){
                if (checkBox.isSelected()){
                    String input = checkBox.getText();
                    if (input.contains("(") && input.contains(")")) {
                        // Eliminar los paréntesis y su contenido
                        input = input.replaceAll("\\(.*?\\)", "").trim();
                    }
                    if (input.contains(",")){
                        input = input.replace(",", "");
                    }
                    Symptom symptom = Symptom.valueOf(input.replace(" ","_").toUpperCase());
                    patient.addSymptom(symptom);

                }
            }
            patientUnit.getPatients().add(patient);

            instance.fire();

            String pathname = "../NeuroDD/src/main/resources/symptom_weights/Symptoms_DSS.xlsx";
            SymptomWeights symptomWeights = new SymptomWeights(pathname);

            patient.calculateDiseaseScore(symptomWeights.getAlzheimer_weights(), "alzheimer", symptomWeights.max_score_alzheimer);
            patient.calculateDiseaseScore(symptomWeights.getAmyotrophic_lateral_sclerosis_weights(), "amyotrophic lateral sclerosis", symptomWeights.max_score_amyotrophic_lateral_sclerosis);
            patient.calculateDiseaseScore(symptomWeights.getHuntington_weights(), "huntington", symptomWeights.max_score_huntington);
            patient.calculateDiseaseScore(symptomWeights.getMultiple_sclerosis_weights(), "multiple sclerosis", symptomWeights.max_score_multiple_sclerosis);
            patient.calculateDiseaseScore(symptomWeights.getMyasthenia_gravis_weights(), "myasthenia gravis", symptomWeights.max_score_myasthenia_gravis);
            patient.calculateDiseaseScore(symptomWeights.getParkinson_weights(), "parkinson", symptomWeights.max_score_parkinson);

            System.out.println(patient);

            diagnosisMessageAlert.setVisible(true);
        }
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
        diagnosis_lbl.setText(patient.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sex_box.getItems().addAll("Male", "Female");
        sex_box.setValue("Male");
        patientUnit = new PatientUnit();
        instance = RuleUnitProvider.get().createRuleUnitInstance(patientUnit);

    }

    public void handleClose(WindowEvent event) {
        if (instance != null) {
            System.out.println("cerrando instance");
            instance.close();
            System.out.println("instance cerrada");
        }
    }
}
