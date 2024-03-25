package diagnosis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Patient {
    private String id;
    private String name;
    private int age;
    private LocalDate dob;
    private Sex sex;
    public ArrayList<Symptom> symptoms;
    public ArrayList<Disease> diseases;

    public Patient(String id, String name, int age, LocalDate dob, Sex sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.sex = sex;
        this.symptoms = new ArrayList<>();
        this.diseases = new ArrayList<>();
    }

    public Patient(ArrayList<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public void addSymptom(Symptom symptom){ this.symptoms.add(symptom); }

    public void addDisease(Disease disease){
        this.diseases.add(disease);
    }

    public ArrayList<Symptom> getSymptoms() {
        return symptoms;
    }

    public ArrayList<Disease> getDiseases() {
        return diseases;
    }

    public void calculateDiseaseScore(Map<String, Integer> disease_weights, String target_disease, int maxScoreDisease){
        float score = 0;
        for (Symptom symptom : symptoms) {
            Integer weight = disease_weights.get(symptom.getName());
            if (weight != null) {
                score += weight;
            }
        }
        score = score / maxScoreDisease * 100;

        for (Disease disease : diseases) {
            if (disease.getName().equals(target_disease)) {
                disease.setScore(score);
                break;
            }
        }
    }



    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dob=" + dob +
                ", sex=" + sex +
                ", symptoms=" + symptoms +
                ", diseases=" + diseases +
                '}';
    }
}
