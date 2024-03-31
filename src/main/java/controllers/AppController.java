package controllers;

import diagnosis.*;
import javafx.animation.FadeTransition;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AppController implements Initializable {

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
    public CheckBox blurredVision, constipation, excessiveSalivation, hyperesthesia;
    public CheckBox hyperreflexia, increasedUrination, lossGagReflex, opticNeuritis;
    public CheckBox respiratoryCompromise, seizures, spasms, urinaryIncontinence, weightLoss;
    public TextField searchTextMotor, searchTextCognitive, searchTextPsychiatric, searchTextOthers;
    public Label diagnosisMessageAlert, patient_info_lbl, symptoms_lbl;
    public TextField dni_txt, name_txt;
    public ChoiceBox sex_box;
    public DatePicker date_picker;
    public ImageView ok_img;
    public VBox probabilities_vbox;
    public Label alzheimer_prob, amyotrophic_prob, huntington_prob;
    public Label multiple_sclerosis_prob, myasthenia_prob, parkinson_prob;
    public ProgressBar alzheimer_pbar, amyotrophic_pbar, huntington_pbar;
    public ProgressBar multiple_sclerosis_pbar, myasthenia_pbar, parkinson_pbar;
    public Hyperlink alzheimer_link, amyotrophic_link, huntington_link, multiple_sclerosis_link;
    public Hyperlink myasthenia_link, parkinson_link;
    private HostServices hostServices;
    public Patient patient;
    PatientUnit patientUnit;
    RuleUnitInstance<PatientUnit> instance;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patientUnit = new PatientUnit();
        instance = RuleUnitProvider.get().createRuleUnitInstance(patientUnit);

        sex_box.getItems().addAll(Sex.MALE, Sex.FEMALE);
        sex_box.setValue(Sex.MALE);

        customizeButton(save_info_btn);
        customizeButton(diagnose_btn);

        createHyperlinkWithIcon(alzheimer_link, "https://medlineplus.gov/alzheimersdisease.html");
        createHyperlinkWithIcon(amyotrophic_link, "https://www.ninds.nih.gov/health-information/disorders/amyotrophic-lateral-sclerosis-als");
        createHyperlinkWithIcon(huntington_link, "https://medlineplus.gov/huntingtonsdisease.html");
        createHyperlinkWithIcon(multiple_sclerosis_link, "https://www.hopkinsmedicine.org/health/conditions-and-diseases/multiple-sclerosis-ms#:~:text=Multiple%20sclerosis%20(MS)%20is%20a%20chronic%20disease%20of%20the%20central,trouble%20walking%2C%20and%20tingling%20feelings.");
        createHyperlinkWithIcon(myasthenia_link, "https://medlineplus.gov/myastheniagravis.html");
        createHyperlinkWithIcon(parkinson_link, "https://www.mayoclinic.org/diseases-conditions/parkinsons-disease/symptoms-causes/syc-20376055");

    }


    public void handleButtonSaveInfo(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.getButtonTypes().setAll(new ButtonType("OK"));

        if (dni_txt.getText().isEmpty()){
            alert.setContentText("Introduced your DNI");
            alert.showAndWait();
        } else if (name_txt.getText().isEmpty()) {
            alert.setContentText("Introduced your full name");
            alert.showAndWait();
        } else if (date_picker.getValue() == null){
                alert.setContentText("Choose your date of birth");
                alert.showAndWait();
        } else {
            ok_img.setOpacity(1);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2.5), ok_img);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.playFromStart();
            patient = new Patient(dni_txt.getText(), name_txt.getText(), date_picker.getValue(), (Sex) sex_box.getValue());
        }
    }

    public void handleButtonDiagnose(ActionEvent actionEvent) {

        if (patient == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Introduced patient data first");
            alert.getButtonTypes().setAll(new ButtonType("OK"));
            alert.showAndWait();
        } else {
            patient.clearSymptoms();
            patient.clearDiseases();

            ArrayList<CheckBox> allCheckBoxes = new ArrayList<>();
            allCheckBoxes.addAll(getCheckBoxes(searchTextMotor, 2));
            allCheckBoxes.addAll(getCheckBoxes(searchTextCognitive, 5));
            allCheckBoxes.addAll(getCheckBoxes(searchTextPsychiatric, 8));
            allCheckBoxes.addAll(getCheckBoxes(searchTextOthers, 11));

            for (CheckBox checkBox : allCheckBoxes){
                if (checkBox.isSelected()){
                    // Poner el texto en el formato adecuado para el enum Symptom
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

            SymptomWeights symptomWeights = new SymptomWeights();

            patient.calculateDiseaseScore(symptomWeights.getAlzheimer_weights(), "Alzheimer", symptomWeights.max_score_alzheimer);
            patient.calculateDiseaseScore(symptomWeights.getAmyotrophic_lateral_sclerosis_weights(), "Amyotrophic lateral sclerosis", symptomWeights.max_score_amyotrophic_lateral_sclerosis);
            patient.calculateDiseaseScore(symptomWeights.getHuntington_weights(), "Huntington", symptomWeights.max_score_huntington);
            patient.calculateDiseaseScore(symptomWeights.getMultiple_sclerosis_weights(), "Multiple sclerosis", symptomWeights.max_score_multiple_sclerosis);
            patient.calculateDiseaseScore(symptomWeights.getMyasthenia_gravis_weights(), "Myasthenia gravis", symptomWeights.max_score_myasthenia_gravis);
            patient.calculateDiseaseScore(symptomWeights.getParkinson_weights(), "Parkinson", symptomWeights.max_score_parkinson);

            System.out.println(patient.getDiseases());
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

    public void handleDiagnosisTab(Event event) {
        if (patient != null) {

            diagnosisMessageAlert.setVisible(false);
            patient_info_lbl.setText(patient.toString());

            String symptom_text = "";
            for (Symptom symptom : patient.getSymptoms()) {
                symptom_text += symptom.toString() + ",  ";
            }
            symptoms_lbl.setText(symptom_text);

            for (Disease disease : patient.getDiseases()) {
                if (disease.getName().equals("Alzheimer")) {
                    String probability = String.format("%.3f", disease.getScore());
                    alzheimer_prob.setText(probability + "%");
                    alzheimer_pbar.setProgress(disease.getScore() / 100);
                } else if (disease.getName().equals("Amyotrophic lateral sclerosis")) {
                    String probability = String.format("%.3f", disease.getScore());
                    amyotrophic_prob.setText(probability + "%");
                    amyotrophic_pbar.setProgress(disease.getScore() / 100);
                } else if (disease.getName().equals("Huntington")) {
                    String probability = String.format("%.3f", disease.getScore());
                    huntington_prob.setText(probability + "%");
                    huntington_pbar.setProgress(disease.getScore() / 100);
                } else if (disease.getName().equals("Multiple sclerosis")) {
                    String probability = String.format("%.3f", disease.getScore());
                    multiple_sclerosis_prob.setText(probability + "%");
                    multiple_sclerosis_pbar.setProgress(disease.getScore() / 100);
                } else if (disease.getName().equals("Myasthenia gravis")) {
                    String probability = String.format("%.3f", disease.getScore());
                    myasthenia_prob.setText(probability + "%");
                    myasthenia_pbar.setProgress(disease.getScore() / 100);
                } else if (disease.getName().equals("Parkinson")) {
                    String probability = String.format("%.3f", disease.getScore());
                    parkinson_prob.setText(probability + "%");
                    parkinson_pbar.setProgress(disease.getScore() / 100);
                }
            }
        }
    }

    private void customizeButton(Button button) {
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #4d88eb;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color:  #357bf2;"));
        button.setOnMousePressed(event -> button.setStyle("-fx-background-color:  #2b74f0;"));
    }

    private void createHyperlinkWithIcon(Hyperlink hyperlink, String url){
        hyperlink.setOnMouseEntered(event -> hyperlink.setUnderline(true));
        hyperlink.setOnMouseExited(event -> hyperlink.setUnderline(false));
        hyperlink.setOnAction(event -> hostServices.showDocument(url));
    }

    public void setHostServices(HostServices hostServices) { this.hostServices = hostServices; }

    public void handleClose() {
        if (instance != null) {
            instance.close();
        }
    }
}
