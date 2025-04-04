module com.example.projectfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.projectfx to javafx.fxml;
    opens CurrentPlayers to javafx.base;
    exports com.example.projectfx;
}