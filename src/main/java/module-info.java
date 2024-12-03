module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.demo to javafx.fxml;
    opens com.example.demo.actors to javafx.fxml;
    opens com.example.demo.actors.planes to javafx.fxml;
    opens com.example.demo.actors.projectiles to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml;

    exports com.example.demo;
    exports com.example.demo.controller;
}