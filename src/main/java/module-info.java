module com.example.gestion_college {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gestion_college to javafx.fxml;
    exports com.example.gestion_college;
}