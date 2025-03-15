package ma.enset.gestionconsultationbdcc.dao;

import ma.enset.gestionconsultationbdcc.entities.Consultation;
import ma.enset.gestionconsultationbdcc.entities.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultationDao implements IConsultationDao {
    private PatientDao patientDao = new PatientDao();

    @Override
    public void create(Consultation consultation) {
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO consultation (DATE_CONSULTATION, DESCRIPTION, ID_PATIENT) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setDate(1, consultation.getDateConsultation());
            ps.setString(2, consultation.getDescription());
            ps.setLong(3, consultation.getPatient().getId());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                consultation.setId(generatedKeys.getLong(1));
            }
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error creating consultation: " + e.getMessage());
        }
    }

    @Override
    public void delete(Consultation consultation) {
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM consultation WHERE ID_CONSULTATION = ?"
            );
            ps.setLong(1, consultation.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error deleting consultation: " + e.getMessage());
        }
    }

    @Override
    public void update(Consultation consultation) {
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE consultation SET DATE_CONSULTATION = ?, DESCRIPTION = ?, ID_PATIENT = ? WHERE ID_CONSULTATION = ?"
            );
            ps.setDate(1, consultation.getDateConsultation());
            ps.setString(2, consultation.getDescription());
            ps.setLong(3, consultation.getPatient().getId());
            ps.setLong(4, consultation.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error updating consultation: " + e.getMessage());
        }
    }

    @Override
    public List<Consultation> findAll() {
        List<Consultation> consultations = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM consultation");

            while (rs.next()) {
                Consultation consultation = new Consultation();
                consultation.setId(rs.getLong("ID_CONSULTATION"));
                consultation.setDateConsultation(rs.getDate("DATE_CONSULTATION"));
                consultation.setDescription(rs.getString("DESCRIPTION"));

                // Get the patient for this consultation
                Long patientId = rs.getLong("ID_PATIENT");
                Patient patient = patientDao.findById(patientId);
                consultation.setPatient(patient);

                consultations.add(consultation);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error retrieving consultations: " + e.getMessage());
        }
        return consultations;
    }

    @Override
    public Consultation findById(Long id) {
        Consultation consultation = null;
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM consultation WHERE ID_CONSULTATION = ?"
            );
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                consultation = new Consultation();
                consultation.setId(rs.getLong("ID_CONSULTATION"));
                consultation.setDateConsultation(rs.getDate("DATE_CONSULTATION"));
                consultation.setDescription(rs.getString("DESCRIPTION"));

                // Get the patient for this consultation
                Long patientId = rs.getLong("ID_PATIENT");
                Patient patient = patientDao.findById(patientId);
                consultation.setPatient(patient);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error retrieving consultation: " + e.getMessage());
        }
        return consultation;
    }
}