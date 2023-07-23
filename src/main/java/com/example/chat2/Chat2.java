package com.example.chat2;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Chat2 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Chat2.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.show();
    }
}
