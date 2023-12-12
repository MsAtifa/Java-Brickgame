module com.example.brickgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.brickgame to javafx.fxml;
    exports com.example.brickgame;
}