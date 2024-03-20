package scripts;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

import java.util.ArrayList;


public class PatientUnit implements RuleUnitData {

    private Patient patient;
    private ArrayList<Symptom> symptoms;


    public PatientUnit() {
        this.symptoms = symptoms;
    }
    public void setSymptoms(ArrayList<Symptom> symptoms) {
        this.symptoms = symptoms;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}


