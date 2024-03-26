package diagnosis;

import org.drools.ruleunits.api.RuleUnitProvider;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DiagnosisTest {

    //this could be my main/interface in which the user adds the symptoms
    static final Logger LOG = LoggerFactory.getLogger(DiagnosisTest.class);
    PatientUnit patientUnit;
    RuleUnitInstance<PatientUnit> instance;

    @Before
    public void setUp(){
        LOG.info("Creating RuleUnit");
        patientUnit = new PatientUnit();
        instance = RuleUnitProvider.get().createRuleUnitInstance(patientUnit);
    }

    @Test
    public void testAlzheimerPatient() {

        try {

            ArrayList<Symptom> alzheimerSymptoms= new ArrayList<Symptom>();
            alzheimerSymptoms.add(new Symptom("memory_impairment"));
            alzheimerSymptoms.add(new Symptom("orientation_impairment"));

            //Creamos un paciente con los sintomas
            Patient alzheimerPatient = new Patient(alzheimerSymptoms,Prueba.TREMOR);
            Disease alzheimer = new Disease("alzheimer");

            patientUnit.getPatients().add(alzheimerPatient);

            instance.fire();
            assertTrue(alzheimerPatient.diseases.contains(alzheimer));

        } finally {
            instance.close();
        }
    }
/*
    @Test
    public void testAmyotrophicLateralSclerosis(){
        try{
            ArrayList<Symptom> alsSymptoms = new ArrayList<>();
            alsSymptoms.add(new Symptom("muscle_weakness"));
            alsSymptoms.add(new Symptom("partial_or_complete_paralysis"));
            alsSymptoms.add(new Symptom("inability_to_move_completely"));

            Patient alsPatient = new Patient(alsSymptoms);
            Disease amyotrophicLateralSclerosis = new Disease("amyotrophic lateral sclerosis");

            patientUnit.getPatients().add(alsPatient);

            instance.fire();
            assertTrue(alsPatient.diseases.contains(amyotrophicLateralSclerosis));

        } finally {
            instance.close();
        }
    }

    @Test
    public void testHuntington(){
        try{
            ArrayList<Symptom> huntingtonSymptoms = new ArrayList<>();
            huntingtonSymptoms.add(new Symptom("involvement_in_voluntary_skeletal_muscles"));

            Patient huntingtonPatient = new Patient(huntingtonSymptoms);
            Disease huntington = new Disease("huntington");

            patientUnit.getPatients().add(huntingtonPatient);

            instance.fire();
            assertTrue(huntingtonPatient.diseases.contains(huntington));

        } finally {
            instance.close();
        }
    }

    @Test
    public void testMultipleSclerosis(){
        try{
            ArrayList<Symptom> msSymptoms = new ArrayList<>();
            msSymptoms.add(new Symptom("blurred_vision"));
            msSymptoms.add(new Symptom("emotional_incontinence"));

            Patient multipleSclerosisPatient = new Patient(msSymptoms);
            Disease multipleSclerosis = new Disease("multiple sclerosis");

            patientUnit.getPatients().add(multipleSclerosisPatient);

            instance.fire();
            assertTrue(multipleSclerosisPatient.diseases.contains(multipleSclerosis));

        } finally {
            instance.close();
        }
    }

    @Test
    public void testMyastheniaGravis(){
        try{
            ArrayList<Symptom> mgSymptoms = new ArrayList<>();
            mgSymptoms.add(new Symptom("muscle_weakness"));

            Patient myastheniaGravisPatient = new Patient(mgSymptoms);
            Disease myastheniaGravis = new Disease("myasthenia gravis");

            patientUnit.getPatients().add(myastheniaGravisPatient);

            instance.fire();
            assertTrue(myastheniaGravisPatient.diseases.contains(myastheniaGravis));

        } finally {
            instance.close();
        }
    }

    @Test
    public void testParkinson(){
        try{
            ArrayList<Symptom> parkinsonSymptoms = new ArrayList<>();
            parkinsonSymptoms.add(new Symptom("bradykinesia"));
            parkinsonSymptoms.add(new Symptom("muscle_stiffness"));

            Patient parkinsonPatient = new Patient(parkinsonSymptoms);
            Disease parkinson = new Disease("parkinson");

            patientUnit.getPatients().add(parkinsonPatient);

            instance.fire();
            assertTrue(parkinsonPatient.diseases.contains(parkinson));

        } finally {
            instance.close();
        }
    }
*/
}
