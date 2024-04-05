module com.example.gestion_circuit {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.gestion_circuit to javafx.fxml;
    exports com.example.gestion_circuit;
    exports Controller;
    opens Controller to javafx.fxml;
    opens Manager.Entities to javafx.base;
}