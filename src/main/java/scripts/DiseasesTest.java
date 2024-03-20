package scripts;


import org.drools.ruleunits.api.RuleUnitProvider;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DiseasesTest {

    //this could be my main/interface in which the user adds the symptoms
    static final Logger LOG = LoggerFactory.getLogger(DiseasesTest.class);

    @Test
    public void test() {
        LOG.info("Creating RuleUnit");
        PatientUnit patientUnit = new PatientUnit();


        RuleUnitInstance<PatientUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(patientUnit);


        try {
            //PACIENTE ALZHEIMER
            ArrayList<Symptom> alzheimerSymptoms= new ArrayList<Symptom>();
            alzheimerSymptoms.add(new Symptom("memory_impairment"));
            alzheimerSymptoms.add(new Symptom("orientation_impairment"));

            //Creamos un paciente con los sintomas
            //TODO hacer que en vez de crear diferentes pacientes cree uno y asigne enfermedades
            Patient alzPatient = new Patient(alzheimerSymptoms);
            //insertar el paciente en la regla
            patientUnit.setPatient(alzPatient);

            //patientUnit.getSymptom().add(patient.symptoms.get(1));
            instance.fire();
            assertTrue(alzPatient.diseases.contains("alzheimer"));


            //PACIENTE ALS
            // Crear un paciente con los síntomas adecuados para ALS
            ArrayList<Symptom> alsSymptoms = new ArrayList<>();
            alsSymptoms.add(new Symptom("muscle_weakness"));
            alsSymptoms.add(new Symptom("partial_or_complete_paralysis"));
            alsSymptoms.add(new Symptom("inability_to_move_completely"));
            Patient alsPatient = new Patient(alsSymptoms);
            patientUnit.setPatient(alsPatient);

            // Ejecutar reglas para confirmar ALS
            instance.fire();
            assertTrue(alsPatient.diseases.contains("amyotrophicLateralSclerosis"));


            //PACIENTE HUNTINGTON
            ArrayList<Symptom> huntingtonSymptoms = new ArrayList<>();
            huntingtonSymptoms.add(new Symptom("involvement_in_voluntary_skeletal_muscles"));
            Patient huntingtonPatient = new Patient(huntingtonSymptoms);
            patientUnit.setPatient(huntingtonPatient);

            // Ejecutar reglas para confirmar Huntington
            instance.fire();
            assertTrue(huntingtonPatient.diseases.contains("huntington"));

            // PACIENTE MULTIPLE SCLEROSIS
            ArrayList<Symptom> msSymptoms = new ArrayList<>();
            msSymptoms.add(new Symptom("blurred_vision"));
            msSymptoms.add(new Symptom("emotional_incontinence"));
            Patient msPatient = new Patient(msSymptoms);
            patientUnit.setPatient(msPatient);

            // Ejecutar reglas para confirmar Multiple Sclerosis
            instance.fire();
            assertTrue(msPatient.diseases.contains("multipleSclerosis"));

            // PACIENTE MYASTHENIA GRAVIS
            ArrayList<Symptom> mgSymptoms = new ArrayList<>();
            mgSymptoms.add(new Symptom("muscle_weakness"));
            Patient mgPatient = new Patient(mgSymptoms);
            patientUnit.setPatient(mgPatient);

            // Ejecutar reglas para confirmar Myasthenia Gravis
            instance.fire();
            assertTrue(mgPatient.diseases.contains("myastheniaGravis"));

            // PACIENTE PARKINSON
            ArrayList<Symptom> parkinsonSymptoms = new ArrayList<>();
            parkinsonSymptoms.add(new Symptom("bradykinesia"));
            parkinsonSymptoms.add(new Symptom("muscle_stiffness"));
            Patient parkinsonPatient = new Patient(parkinsonSymptoms);
            patientUnit.setPatient(parkinsonPatient);

            // Ejecutar reglas para confirmar Parkinson
            instance.fire();
            assertTrue(parkinsonPatient.diseases.contains("parkinson"));
        } finally {
            instance.close();
        }
    }
}


