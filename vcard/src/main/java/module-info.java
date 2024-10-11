module fr.afpa {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.sql;

    opens fr.afpa to javafx.fxml;
    opens fr.afpa.controllers to javafx.fxml;
    exports fr.afpa;
    exports fr.afpa.models;
}
