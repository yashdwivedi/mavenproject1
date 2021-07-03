package com.github.TeamRocketBalleBalle.Ricktionary.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
    private final Socket socket;

    private final BufferedReader reader;
    private final PrintWriter writer;

    private final String name;

    /* TODO: prolly add code here to send an Order to client to send the player name from them */
    /* TODO: also maybe have the client send a response as OrderResponse object? */
    public Player(Socket socket) throws IOException {
        this.socket = socket;

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        reader = new BufferedReader(inputStreamReader);
        writer = new PrintWriter(socket.getOutputStream());

        name = null;
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

    public String getName() {
        return name;
    }
}
