package scripts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient {
    private String id;
    private String name;
    private int age;
    private Date dob;
    private Sex sex;
    public ArrayList<Symptom> symptoms;
    public ArrayList<Disease> diseases;

    public Patient(String id, String name, int age, Date dob, Sex sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.sex = sex;
    }

    public Patient(ArrayList<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public void addSymptom(Symptom symptom){
        this.symptoms.add(symptom);
    }

    public void addDisease(Disease disease){
        this.diseases.add(disease);
    }

    public ArrayList<Symptom> getSymptoms() {
        return symptoms;
    }

    public ArrayList<Disease> getDiseases() {
        return diseases;
    }


}
