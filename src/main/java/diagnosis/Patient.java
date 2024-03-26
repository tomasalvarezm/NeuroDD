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
        this.age = getAge(dob);
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

    private int getAge(LocalDate birth) {
        int age = 0;
        if (LocalDate.now().getMonthValue() > birth.getMonthValue()) {
            age = LocalDate.now().getYear() - birth.getYear();
        } else if (LocalDate.now().getDayOfMonth() == birth.getMonthValue()) {
            if (LocalDate.now().getDayOfMonth() >= birth.getDayOfMonth()) {
                age = LocalDate.now().getYear() - birth.getYear();
            }
        } else {
            age = LocalDate.now().getYear() - birth.getYear() - 1;
        }
        return age;
    }
    @Override
    public String toString() {
        return  "Name: " + name + ",   " +
                "DNI: " + id  + ",   " +
                "Age: " + age + ",    " +
                "Date of birth: " + dob + ",   " +
                "Sex: " + sex ;

    }
}
