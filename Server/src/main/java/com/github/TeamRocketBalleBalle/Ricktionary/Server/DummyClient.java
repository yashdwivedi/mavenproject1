package com.github.TeamRocketBalleBalle.Ricktionary.Server;

import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.ClientPacket;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.Reply;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.ServerPacket;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.PacketType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DummyClient {
    public void test() {
        try {
            Socket socket = new Socket("127.0.0.1", 5000);
            System.out.println(socket.isConnected());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out2 = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("starting loop...");
            while (true) {
                //                if (0 < input.available()) {
                System.out.println("Data available to read");
                Object o = input.readObject();
                System.out.println(o);
                System.out.println("sending my name");
                ClientPacket meme =
                        new ClientPacket(
                                PacketType.INITIALISE,
                                ((ServerPacket) o).getOrder(),
                                new Reply<String>("meme"));
                out2.writeObject(meme);
                //                }
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
