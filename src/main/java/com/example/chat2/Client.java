package com.example.chat2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;
    private String username;
    public String messageFromChat;
    public boolean messageAcceptable = false;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            e.printStackTrace();
            closeAll(socket, in, out);
        }
    }

    private void closeAll(Socket socket, BufferedReader in, BufferedWriter out) {

        try {
            if (in !=null) {
                in.close();
            }
            if (out !=null) {
                out.close();
            }
            if (socket !=null) {
                socket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String messageText) {
        try {
            if (socket.isConnected()) {
                out.write(messageText);
                out.newLine();
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listenMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        messageFromChat = in.readLine();
                        messageAcceptable = true;
                        System.out.println(messageFromChat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
//    public static void main(String[] args) throws IOException {
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Enter your name");
//        String userName = scan.nextLine();
//        Socket socket = new Socket("192.168.88.18", 5555);
//        Client client = new Client(socket, userName);
//        client.sendMessage();
//        client.listenMessage();
//    }
}
