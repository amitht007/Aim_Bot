package com.example.aim_bot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    Stage stage;
    public void setStage(Stage stage){
        this.stage=stage;
    }
    public void startPlaying() throws IOException {
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root=fxmlLoader.load();
            Controller controller=fxmlLoader.getController();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
