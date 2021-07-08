package com.github.TeamRocketBalleBalle.Ricktionary.Server;

/** Hello world! */
public class App {

    public static void main(String[] args) {
        Server server;
        if (args.length == 2) {
            server = new Server(args[0], Integer.parseInt(args[1]));
        } else if (args.length > 2) {
            System.out.println("wrong.");
            System.exit(1);
        }
        server = new Server();
        server.listen();
    }
}
