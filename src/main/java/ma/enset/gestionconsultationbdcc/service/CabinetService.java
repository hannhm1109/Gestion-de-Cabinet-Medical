package ma.enset.gestionconsultationbdcc.service;

import ma.enset.gestionconsultationbdcc.dao.IPatientDao;
import ma.enset.gestionconsultationbdcc.dao.IConsultationDao;
import ma.enset.gestionconsultationbdcc.entities.Patient;
import ma.enset.gestionconsultationbdcc.entities.Consultation;

import java.util.List;

public class CabinetService implements ICabinetService {
    private IPatientDao patientDao;
    private IConsultationDao iConsultationDao;

    public CabinetService(IPatientDao patientDao, IConsultationDao iConsultationDao) {
        this.patientDao = patientDao;
        this.iConsultationDao = iConsultationDao;
    }

    // Patient methods
    @Override
    public void addPatient(Patient patient) {
        patientDao.create(patient);
    }

    @Override
    public void deletePatient(Patient patient) {
        patientDao.delete(patient);
    }

    @Override
    public void updatePatient(Patient patient) {
        patientDao.update(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientDao.findAll();
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientDao.findById(id);
    }

    // Consultation methods
    @Override
    public void addConsultation(Consultation consultation) {
        iConsultationDao.create(consultation);
    }

    @Override
    public void deleteConsultation(Consultation consultation) {
        iConsultationDao.delete(consultation);
    }

    @Override
    public void updateConsultation(Consultation consultation) {
        iConsultationDao.update(consultation);
    }

    @Override
    public List<Consultation> getAllConsultations() {
        return iConsultationDao.findAll();
    }

    @Override
    public Consultation getConsultationById(Long id) {
        return iConsultationDao.findById(id);
    }
}