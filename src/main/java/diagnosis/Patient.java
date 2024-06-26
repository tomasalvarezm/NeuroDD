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

    public Patient(String id, String name, LocalDate dob, Sex sex) {
        this.id = id;
        this.name = name;
        getAge(dob);
        this.dob = dob;
        this.sex = sex;
        this.symptoms = new ArrayList<>();
        this.diseases = new ArrayList<>();
    }

    public Patient(ArrayList<Symptom> symptoms) {
        this.symptoms = symptoms;
        this.diseases = new ArrayList<>();
    }

    public void addSymptom(Symptom symptom){ this.symptoms.add(symptom); }

    public void addDisease(Disease disease){ this.diseases.add(disease); }

    public ArrayList<Symptom> getSymptoms() { return symptoms; }

    public ArrayList<Disease> getDiseases() { return diseases; }

    public void calculateDiseaseScore(Map<Symptom, Integer> disease_weights, String target_disease, int maxScoreDisease){
        float score = 0;
        for (Symptom symptom : symptoms) {
            Integer weight = disease_weights.get(symptom);
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

    private void getAge(LocalDate birth) {
        LocalDate now = LocalDate.now();
        if (now.getMonthValue() < birth.getMonthValue()) {
            this.age = now.getYear() - birth.getYear() -1;
        } else if (now.getMonthValue() > birth.getMonthValue()) {
            this.age = now.getYear() - birth.getYear();
        } else {
            if (now.getDayOfMonth() < birth.getDayOfMonth()){
                this.age = now.getYear() - birth.getYear() -1;
            } else this.age = now.getYear() - birth.getYear();
        }
    }

    public void clearSymptoms(){ this.symptoms.clear(); }

    public void clearDiseases() { this.diseases.clear(); }

    @Override
    public String toString() {
        return  "Name: " + name + ",   " +
                "DNI: " + id  + ",   " +
                "Age: " + age + ",    " +
                "Date of birth: " + dob + ",   " +
                "Sex: " + sex ;

    }

}
