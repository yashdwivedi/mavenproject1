package com.github.TeamRocketBalleBalle.Ricktionary.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

abstract public class Player {
    private final Socket socket;

    private final BufferedReader reader;
    private final PrintWriter writer;

    public Player(Socket socket) throws IOException {
        this.socket = socket;

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        reader = new BufferedReader(inputStreamReader);
        writer = new PrintWriter(socket.getOutputStream());
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public Socket getSocket() {
        return socket;
    }
}
