package com.github.TeamRocketBalleBalle.Ricktionary.Resources.database;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class DbWork {
    static Connection c = null;
    static Statement stmt = null;
    static String url = "jdbc:sqlite:sqlite.db";

    public static void main(String[] args) {
        connect();
//        getListOfHashes();
//        getAnswer("264BAAB7EEC0F43BDC71");

    }
    public static void connect()
    {
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        try (Connection c = getConnection(url)) {
            if (c != null) {
                DatabaseMetaData meta = c.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//    public static void drop_and_create_table() throws SQLException {
//        c = getConnection(url);
//        stmt = c.createStatement();
//        String dropStatement = " SELECT * FROM sqlite_master where tbl_name = 'imagehash'";
//        System.out.println((stmt.executeUpdate(dropStatement)));
//
//
//        stmt.close();
//        c.close();
//    }
    public static ArrayList getListOfHashes(){
        ArrayList<String> HashList = new ArrayList<String>();
        try {
            c = getConnection(url);
            stmt = c.createStatement();
            String getQuery = "SELECT hash from imagehash";
            ResultSet rs = stmt.executeQuery(getQuery);

            while (rs.next()){
                HashList.add(rs.getString("hash"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return HashList;
//        System.out.println(HashList);
    }
    public static String  getAnswer(String h){
        String answer = "";
        String answer1 = "";
        PreparedStatement pstmt = null;
        try {
            c = getConnection(url);
            stmt = c.createStatement();
            answer = "SELECT answer from imagehash where hash == ?";
            pstmt = c.prepareStatement(answer);
            pstmt.setString(1,h);
            answer1 = pstmt.executeQuery().getString("answer");

            //            System.out.println("Output: - ");
//            String getQuery = "SELECT count(*) > 0 FROM sqlite_master where tbl_name = \"<table_name>\" and type=\"table\""
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return answer1;
    }
}
