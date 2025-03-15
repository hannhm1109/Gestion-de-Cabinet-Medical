package ma.enset.gestionconsultationbdcc.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.gestionconsultationbdcc.dao.ConsultationDao;
import ma.enset.gestionconsultationbdcc.dao.PatientDao;
import ma.enset.gestionconsultationbdcc.entities.Patient;
import ma.enset.gestionconsultationbdcc.service.CabinetService;
import ma.enset.gestionconsultationbdcc.service.ICabinetService;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientController implements Initializable {
    @FXML private TextField textFieldNom;
    @FXML private TextField textFieldPrenom;
    @FXML private TextField textFieldTel;
    @FXML private TextField textFieldSearch;
    @FXML private TableView<Patient> tablePatients;
    @FXML private TableColumn<Patient, Long> columnId;
    @FXML private TableColumn<Patient, String> columnNom;
    @FXML private TableColumn<Patient, String> columnPrenom;
    @FXML private TableColumn<Patient, String> columnTel;
    @FXML private Button Ajouter;
    @FXML private Button Suprimer;
    @FXML private Button Modifier;

    private ICabinetService cabinetService;
    private ObservableList<Patient> patientsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the service
        cabinetService = new CabinetService(new PatientDao(), new ConsultationDao());

        // Configure the table columns
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        columnTel.setCellValueFactory(new PropertyValueFactory<>("tel"));

        // Load data from database
        loadPatients();

        // Set up selection listener for table
        tablePatients.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                textFieldNom.setText(newSelection.getNom());
                textFieldPrenom.setText(newSelection.getPrenom());
                textFieldTel.setText(newSelection.getTel());
            }
        });

        // Set up search field
        textFieldSearch.textProperty().addListener((obs, oldValue, newValue) -> {
            filterPatients(newValue);
        });

        // Configure button handlers
        if (Ajouter != null) {
            Ajouter.setOnAction(event -> {
                addPatient();
            });
        }

        if (Suprimer != null) {
            Suprimer.setOnAction(event -> {
                deletePatient();
            });
        }

        if (Modifier != null) {
            Modifier.setOnAction(event -> {
                updatePatient();
            });
        }
    }

    private void loadPatients() {
        patientsList.clear();
        patientsList.addAll(cabinetService.getAllPatients());
        tablePatients.setItems(patientsList);
    }

    private void filterPatients(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            loadPatients();
        } else {
            ObservableList<Patient> filteredList = FXCollections.observableArrayList();
            for (Patient patient : cabinetService.getAllPatients()) {
                if (patient.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                        patient.getPrenom().toLowerCase().contains(searchText.toLowerCase()) ||
                        patient.getTel().contains(searchText)) {
                    filteredList.add(patient);
                }
            }
            tablePatients.setItems(filteredList);
        }
    }

    private void addPatient() {
        // Validate input fields
        if (textFieldNom.getText().isEmpty() || textFieldPrenom.getText().isEmpty() || textFieldTel.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }

        // Create new patient
        Patient patient = new Patient();
        patient.setNom(textFieldNom.getText());
        patient.setPrenom(textFieldPrenom.getText());
        patient.setTel(textFieldTel.getText());

        // Add to database
        cabinetService.addPatient(patient);

        // Clear fields and reload table
        clearFields();
        loadPatients();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Patient added successfully");
    }

    private void updatePatient() {
        Patient selectedPatient = tablePatients.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a patient to update");
            return;
        }

        // Validate input fields
        if (textFieldNom.getText().isEmpty() || textFieldPrenom.getText().isEmpty() || textFieldTel.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }

        // Update patient
        selectedPatient.setNom(textFieldNom.getText());
        selectedPatient.setPrenom(textFieldPrenom.getText());
        selectedPatient.setTel(textFieldTel.getText());

        // Update in database
        cabinetService.updatePatient(selectedPatient);

        // Clear fields and reload table
        clearFields();
        loadPatients();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Patient updated successfully");
    }

    private void deletePatient() {
        Patient selectedPatient = tablePatients.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a patient to delete");
            return;
        }

        // Confirm deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Patient");
        alert.setContentText("Are you sure you want to delete patient: " +
                selectedPatient.getNom() + " " + selectedPatient.getPrenom() + "?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // Delete from database
            cabinetService.deletePatient(selectedPatient);

            // Clear fields and reload table
            clearFields();
            loadPatients();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Patient deleted successfully");
        }
    }

    private void clearFields() {
        textFieldNom.clear();
        textFieldPrenom.clear();
        textFieldTel.clear();
        tablePatients.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}