package ma.enset.gestionconsultationbdcc.service;

import ma.enset.gestionconsultationbdcc.dao.ConsultationDao;
import ma.enset.gestionconsultationbdcc.dao.PatientDao;
import ma.enset.gestionconsultationbdcc.entities.Patient;

import java.util.List;

public class ServiceTest {
    public static void main(String[] args) {
        ICabinetService service = new CabinetService(new PatientDao(), new ConsultationDao());

        // Uncomment to add a new patient
        /*
        Patient newPatient = new Patient();
        newPatient.setNom("Nahim");
        newPatient.setPrenom("Hanane");
        newPatient.setTel("0612345678");
        service.addPatient(newPatient);
        System.out.println("Patient added with ID: " + newPatient.getId());
        */

        // Test findAll
        /*System.out.println("\n===== Testing getAllPatients =====");
        List<Patient> patients = service.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found in database");
        } else {
            System.out.println("Found " + patients.size() + " patients:");
            for (Patient p : patients) {
                System.out.println(p.getId() + " - " + p.getNom() + " " + p.getPrenom() + " - " + p.getTel());
            }
        }*/

        // Test findById - select an ID that exists in your database
        /*long patientIdToFind = 1; // Change this to an ID that exists in your DB
        System.out.println("\n===== Testing getPatientById(" + patientIdToFind + ") =====");
        Patient foundPatient = service.getPatientById(patientIdToFind);
        if (foundPatient != null) {
            System.out.println("Found patient: " + foundPatient.getId() + " - " +
                    foundPatient.getNom() + " " + foundPatient.getPrenom() + " - " +
                    foundPatient.getTel());

            // Test update
            System.out.println("\n===== Testing updatePatient =====");
            System.out.println("Original phone: " + foundPatient.getTel());
            String originalPhone = foundPatient.getTel();
            foundPatient.setTel("0699999999"); // Set a new phone number
            service.updatePatient(foundPatient);
            System.out.println("Patient updated with new phone: " + foundPatient.getTel());

            // Verify update worked by finding again
            Patient updatedPatient = service.getPatientById(patientIdToFind);
            System.out.println("Retrieved patient again, phone number: " + updatedPatient.getTel());

            // Revert the change
            foundPatient.setTel(originalPhone);
            service.updatePatient(foundPatient);
            System.out.println("Reverted phone number back to: " + originalPhone);
        } else {
            System.out.println("No patient found with ID " + patientIdToFind);
        }*/

        // Test delete - CAUTION: only uncomment if you want to actually delete a record
        /*
        System.out.println("\n===== Testing deletePatient =====");
        long patientIdToDelete = 2; // Change this to an ID you want to delete
        Patient patientToDelete = service.getPatientById(patientIdToDelete);
        if (patientToDelete != null) {
            System.out.println("Deleting patient: " + patientToDelete.getNom() + " " + patientToDelete.getPrenom());
            service.deletePatient(patientToDelete);
            System.out.println("Patient deleted");

            // Verify the deletion
            Patient shouldBeNull = service.getPatientById(patientIdToDelete);
            if (shouldBeNull == null) {
                System.out.println("Verified: Patient no longer exists in database");
            } else {
                System.out.println("Error: Patient still exists in database after deletion");
            }
        } else {
            System.out.println("No patient found with ID " + patientIdToDelete);
        }
        */
    }
}