module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.demo to javafx.fxml, org.junit.jupiter.api;
    opens com.example.demo.controller to javafx.fxml, org.junit.jupiter.api;
    opens com.example.demo.managers to org.junit.jupiter.api;
    opens com.example.demo.view to org.junit.jupiter.api;
    opens com.example.demo.levels to org.junit.jupiter.api;

    exports com.example.demo;
    exports com.example.demo.controller;
}