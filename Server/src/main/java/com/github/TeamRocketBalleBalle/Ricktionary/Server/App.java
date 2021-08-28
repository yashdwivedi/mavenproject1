package com.github.TeamRocketBalleBalle.Ricktionary.Server;

/** Hello world! */
public class App {

    public static void main(String[] args) {
        Server server = null;
        if (args.length == 1) {
            server = new Server(Integer.parseInt(args[0]));
        } else if (args.length == 2) {
            server = new Server(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } else if (args.length > 2) {
            System.out.println("wrong.");
            System.exit(1);
        } else {
            server = new Server();
        }
        server.listen();
    }
}
