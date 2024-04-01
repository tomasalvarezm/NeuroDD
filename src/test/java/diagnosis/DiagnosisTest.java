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
            alzheimerSymptoms.add(Symptom.MEMORY_IMPAIRMENT);
            alzheimerSymptoms.add(Symptom.ORIENTATION_IMPAIRMENT);
            alzheimerSymptoms.add(Symptom.EMOTIONAL_INCONTINENCE);

            //Creamos un paciente con los sintomas
            Patient alzheimerPatient = new Patient(alzheimerSymptoms);
            Disease alzheimer = new Disease("Alzheimer");

            patientUnit.getPatients().add(alzheimerPatient);

            instance.fire();
            assertTrue(alzheimerPatient.diseases.contains(alzheimer));

        } finally {
            instance.close();
        }
    }

    @Test
    public void testAmyotrophicLateralSclerosis(){
        try{
            ArrayList<Symptom> alsSymptoms = new ArrayList<Symptom>();
            alsSymptoms.add(Symptom.MUSCLE_WEAKNESS);
            alsSymptoms.add(Symptom.PARTIAL_OR_COMPLETE_PARALYSIS);
            alsSymptoms.add(Symptom.INABILITY_TO_COMPLETELY_MOVE);
            alsSymptoms.add(Symptom.URINARY_INCONTINENCE);

            Patient alsPatient = new Patient(alsSymptoms);
            Disease amyotrophicLateralSclerosis = new Disease("Amyotrophic lateral sclerosis");

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
            huntingtonSymptoms.add(Symptom.INVOLVEMENT_OF_VOLUNTARY_SKELETAL_MUSCLES);
            huntingtonSymptoms.add(Symptom.PARTIAL_OR_COMPLETE_PARALYSIS);
            huntingtonSymptoms.add(Symptom.BEHAVIORAL_DISTURBANCES);

            Patient huntingtonPatient = new Patient(huntingtonSymptoms);
            Disease huntington = new Disease("Huntington");

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
            msSymptoms.add(Symptom.BLURRED_VISION);
            msSymptoms.add(Symptom.EMOTIONAL_INCONTINENCE);
            msSymptoms.add(Symptom.PARESTHESIA);
            msSymptoms.add(Symptom.UNSTEADY_GAIT);
            msSymptoms.add(Symptom.ANXIETY_STRESS_OR_TENSION);

            Patient multipleSclerosisPatient = new Patient(msSymptoms);
            Disease multipleSclerosis = new Disease("Multiple sclerosis");

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
            mgSymptoms.add(Symptom.DIPLOPIA);
            mgSymptoms.add(Symptom.MUSCLE_WEAKNESS);
            mgSymptoms.add(Symptom.DIFFICULTY_SWALLOWING);
            mgSymptoms.add(Symptom.DIFFICULTY_BREATHING);

            Patient myastheniaGravisPatient = new Patient(mgSymptoms);
            Disease myastheniaGravis = new Disease("Myasthenia gravis");

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
            parkinsonSymptoms.add(Symptom.BENT_POSTURE);
            parkinsonSymptoms.add(Symptom.BRADYKINESIA);
            parkinsonSymptoms.add(Symptom.MUSCLE_STIFFNESS);
            parkinsonSymptoms.add(Symptom.TREMOR);

            Patient parkinsonPatient = new Patient(parkinsonSymptoms);
            Disease parkinson = new Disease("Parkinson");

            patientUnit.getPatients().add(parkinsonPatient);

            instance.fire();
            assertTrue(parkinsonPatient.diseases.contains(parkinson));

        } finally {
            instance.close();
        }
    }
}
