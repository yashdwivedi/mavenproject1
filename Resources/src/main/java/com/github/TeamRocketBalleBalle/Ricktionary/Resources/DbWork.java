package com.github.TeamRocketBalleBalle.Ricktionary.Resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbWork {

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:imageHash.db");
            statement = connection.createStatement();
            String query = "";
        } catch (Exception e) {
            System.out.print(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
        System.out.println("DataBase Opened SuccessFully");
    }
}
