module EMS {
    // Required JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    // Required for database connectivity
    requires transitive java.sql;

    // Open packages to JavaFX
    opens application to javafx.fxml;

    // Export packages to be accessible by other modules if needed
    exports application;
}
