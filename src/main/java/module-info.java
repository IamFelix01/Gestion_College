module com.example.gestion_college {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;


    opens com.example.gestion_college to javafx.fxml;
    exports com.example.gestion_college;
}