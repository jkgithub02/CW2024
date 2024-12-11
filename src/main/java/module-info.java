module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.prefs;

    opens com.example.demo to javafx.fxml;
    opens com.example.demo.actors to javafx.fxml;
    opens com.example.demo.actors.planes to javafx.fxml;
    opens com.example.demo.actors.projectiles to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml;

    exports com.example.demo;
    exports com.example.demo.controller;
    exports com.example.demo.actors;
    exports com.example.demo.view;
    opens com.example.demo.view to javafx.fxml;
    exports com.example.demo.view.arcade;
    opens com.example.demo.view.arcade to javafx.fxml;
    exports com.example.demo.view.effects;
    opens com.example.demo.view.effects to javafx.fxml;
    exports com.example.demo.view.bosslevel;
    opens com.example.demo.view.bosslevel to javafx.fxml;
    exports com.example.demo.controller.gameControllers;
    opens com.example.demo.controller.gameControllers to javafx.fxml;
}