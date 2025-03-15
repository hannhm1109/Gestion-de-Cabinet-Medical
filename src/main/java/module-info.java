module ma.enset.gestionconsultationbdcc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ma.enset.gestionconsultationbdcc to javafx.fxml;
    opens ma.enset.gestionconsultationbdcc.controllers to javafx.fxml;
    opens ma.enset.gestionconsultationbdcc.entities to javafx.base;

    exports ma.enset.gestionconsultationbdcc;
    exports ma.enset.gestionconsultationbdcc.controllers;
}