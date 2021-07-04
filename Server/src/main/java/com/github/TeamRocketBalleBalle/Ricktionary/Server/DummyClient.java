package com.github.TeamRocketBalleBalle.Ricktionary.Server;


import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DummyClient {
    public void test() {
        try {
            Socket socket = new Socket("4.tcp.ngrok.io", 16644);
            System.out.println(socket.isConnected());
            ObjectInputStream out = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out2 = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                if (0 < out.available()) {
                    System.out.println(out.readByte());
                    System.out.println(out.readObject());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        DummyClient obj = new DummyClient();
        obj.test();


    }
}


