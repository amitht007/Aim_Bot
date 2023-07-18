module com.example.aim_bot {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.aim_bot to javafx.fxml;
    exports com.example.aim_bot;
}