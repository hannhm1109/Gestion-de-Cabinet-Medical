package ma.enset.gestionconsultationbdcc.dao;

import ma.enset.gestionconsultationbdcc.entities.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDao implements IPatientDao {

    @Override
    public void create(Patient patient) {
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO patient (NOM, PRENOM, TEL) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getPrenom());
            ps.setString(3, patient.getTel());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                patient.setId(generatedKeys.getLong(1));
            }
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error creating patient: " + e.getMessage());
        }
    }

    @Override
    public void delete(Patient patient) {
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM patient WHERE ID_PATIENT = ?"
            );
            ps.setLong(1, patient.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error deleting patient: " + e.getMessage());
        }
    }

    @Override
    public void update(Patient patient) {
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE patient SET NOM = ?, PRENOM = ?, TEL = ? WHERE ID_PATIENT = ?"
            );
            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getPrenom());
            ps.setString(3, patient.getTel());
            ps.setLong(4, patient.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error updating patient: " + e.getMessage());
        }
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patient");

            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getLong("ID_PATIENT"));
                patient.setNom(rs.getString("NOM"));
                patient.setPrenom(rs.getString("PRENOM"));
                patient.setTel(rs.getString("TEL"));
                patients.add(patient);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error retrieving patients: " + e.getMessage());
        }
        return patients;
    }

    @Override
    public Patient findById(Long id) {
        Patient patient = null;
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM patient WHERE ID_PATIENT = ?"
            );
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                patient = new Patient();
                patient.setId(rs.getLong("ID_PATIENT"));
                patient.setNom(rs.getString("NOM"));
                patient.setPrenom(rs.getString("PRENOM"));
                patient.setTel(rs.getString("TEL"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error retrieving patient: " + e.getMessage());
        }
        return patient;
    }
}