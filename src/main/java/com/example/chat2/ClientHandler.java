package com.example.chat2;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;

    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = in.readLine();
            clientHandlers.add(this);
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

    private void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (!clientHandler.clientUsername.equals(this.clientUsername)){
                try {
                    clientHandler.out.write(message);
                    clientHandler.out.newLine();
                    clientHandler.out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String message = in.readLine();
                broadcastMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                closeAll(socket, in, out);
                break;
            }
        }
    }
}
