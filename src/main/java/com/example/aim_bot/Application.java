package com.example.aim_bot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        Parent root=fxmlLoader.load();
        MainController mainController=fxmlLoader.getController();
        mainController.setStage(stage);
        Scene scene = new Scene(root);
        stage.setTitle("Game");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}