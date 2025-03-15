package ma.enset.gestionconsultationbdcc.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.gestionconsultationbdcc.dao.ConsultationDao;
import ma.enset.gestionconsultationbdcc.dao.PatientDao;
import ma.enset.gestionconsultationbdcc.entities.Consultation;
import ma.enset.gestionconsultationbdcc.entities.Patient;
import ma.enset.gestionconsultationbdcc.service.CabinetService;
import ma.enset.gestionconsultationbdcc.service.ICabinetService;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {
    @FXML private DatePicker dateConsultation;
    @FXML private ComboBox<Patient> comboPatient;
    @FXML private TextArea textFieldDescription;
    @FXML private TableView<Consultation> tableConsultation;
    @FXML private TableColumn<Consultation, Long> columnId;
    @FXML private TableColumn<Consultation, Date> columnDateConsultation;
    @FXML private TableColumn<Consultation, String> columnDescription;
    @FXML private TableColumn<Consultation, Patient> columnPatient;
    @FXML private Button Ajouter;
    @FXML private Button Supprimer;

    private ICabinetService cabinetService;
    private ObservableList<Consultation> consultationsList = FXCollections.observableArrayList();
    private ObservableList<Patient> patientsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the service
        cabinetService = new CabinetService(new PatientDao(), new ConsultationDao());

        // Configure the table columns
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDateConsultation.setCellValueFactory(new PropertyValueFactory<>("dateConsultation"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnPatient.setCellValueFactory(new PropertyValueFactory<>("patient"));

        // Set up patient name display in the patient column
        columnPatient.setCellFactory(column -> {
            return new TableCell<Consultation, Patient>() {
                @Override
                protected void updateItem(Patient patient, boolean empty) {
                    super.updateItem(patient, empty);
                    if (empty || patient == null) {
                        setText(null);
                    } else {
                        setText(patient.getNom() + " " + patient.getPrenom());
                    }
                }
            };
        });

        // Load patients for combo box
        loadPatients();

        // Load consultations for table
        loadConsultations();

        // Set up selection listener for table
        tableConsultation.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Convert java.sql.Date to LocalDate for DatePicker
                dateConsultation.setValue(newSelection.getDateConsultation().toLocalDate());
                textFieldDescription.setText(newSelection.getDescription());
                comboPatient.setValue(newSelection.getPatient());
            }
        });

        // Configure button handlers
        if (Ajouter != null) {
            Ajouter.setOnAction(event -> {
                addConsultation();
            });
        }

        if (Supprimer != null) {
            Supprimer.setOnAction(event -> {
                deleteConsultation();
            });
        }
    }

    private void loadPatients() {
        patientsList.clear();
        patientsList.addAll(cabinetService.getAllPatients());
        comboPatient.setItems(patientsList);

        // Set up display of patient names in combo box
        comboPatient.setCellFactory(param -> new ListCell<Patient>() {
            @Override
            protected void updateItem(Patient patient, boolean empty) {
                super.updateItem(patient, empty);
                if (empty || patient == null) {
                    setText(null);
                } else {
                    setText(patient.getNom() + " " + patient.getPrenom());
                }
            }
        });

        // Set up display of selected patient in combo box button
        comboPatient.setButtonCell(new ListCell<Patient>() {
            @Override
            protected void updateItem(Patient patient, boolean empty) {
                super.updateItem(patient, empty);
                if (empty || patient == null) {
                    setText(null);
                } else {
                    setText(patient.getNom() + " " + patient.getPrenom());
                }
            }
        });
    }

    private void loadConsultations() {
        consultationsList.clear();
        consultationsList.addAll(cabinetService.getAllConsultations());
        tableConsultation.setItems(consultationsList);
    }

    private void addConsultation() {
        // Validate input fields
        if (dateConsultation.getValue() == null || comboPatient.getValue() == null ||
                textFieldDescription.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }

        // Create new consultation
        Consultation consultation = new Consultation();
        consultation.setDateConsultation(Date.valueOf(dateConsultation.getValue()));
        consultation.setDescription(textFieldDescription.getText());
        consultation.setPatient(comboPatient.getValue());

        // Add to database
        cabinetService.addConsultation(consultation);

        // Clear fields and reload table
        clearFields();
        loadConsultations();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Consultation added successfully");
    }

    private void deleteConsultation() {
        Consultation selectedConsultation = tableConsultation.getSelectionModel().getSelectedItem();
        if (selectedConsultation == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a consultation to delete");
            return;
        }

        // Confirm deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Consultation");
        alert.setContentText("Are you sure you want to delete this consultation?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // Delete from database
            cabinetService.deleteConsultation(selectedConsultation);

            // Clear fields and reload table
            clearFields();
            loadConsultations();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Consultation deleted successfully");
        }
    }

    private void clearFields() {
        dateConsultation.setValue(null);
        textFieldDescription.clear();
        comboPatient.setValue(null);
        tableConsultation.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}