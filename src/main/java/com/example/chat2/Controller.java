package com.example.chat2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;

public class Controller {
    private Client client;
    private String name;
    @FXML
    TextField nameField;
    @FXML
    TextField hostField;
    @FXML
    TextField portField;
    @FXML
    TextField inputMessageField;
    @FXML
    Button connectButton;
    @FXML
    Button sendButton;
    @FXML
    ListView<String> messageListView = new ListView<String>();


    public void connect() throws IOException {
        name = nameField.getText();
        String host = hostField.getText();
        String port = portField.getText();

        System.out.println("Host: " + host);
        System.out.println("Port: " + port);
        System.out.println("Name: " + name);

        messageListView.getItems().add("Host: " + host);
        messageListView.getItems().add("Port: " + port);
        messageListView.getItems().add("Name: " + name);

        Socket socket = new Socket(host, Integer.parseInt(port));
        client = new Client(socket, name);
        client.listenMessage();
        readMessage();
    }

    public void send() {
        String message = inputMessageField.getText();
        inputMessageField.clear();

        System.out.println(message);
        client.sendMessage(name + ": " + message);
        messageListView.getItems().add("My: " + message);
    }

    public void readMessage () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (client.messageAcceptable) {
                        Platform.runLater(()-> messageListView.getItems().add(client.messageFromChat));
                        client.messageAcceptable = false;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
