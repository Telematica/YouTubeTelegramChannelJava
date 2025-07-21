package org.telematica;

import java.sql.*;
import java.util.Map;

public class Database {
    public static Connection connection;

    public static void connect() throws SQLException {
        String url = "jdbc:sqlite:/Users/dealersocket/YouTubeTelegramChannelJava/src/main/resources/database/db.sqlite";
        Database.connection = DriverManager.getConnection(url);
        System.out.println("Connection to SQLite has been established.");
    }

    public static Map<String, String> getAllYouTubeChannels() throws SQLException {
        Statement statement = Database.connection.createStatement();
        // statement.setQueryTimeout(30);  // set timeout to 30 sec.
        ResultSet rs = statement.executeQuery("select * from channel");
        Map<String, String> results = new java.util.HashMap<>(Map.of());
        while(rs.next()) {
            results.put(rs.getString("id"), rs.getString("name"));
        }
        return results;
    }

    public static Map<String, String> getAllTiktokUsers() throws SQLException {
        Statement statement = Database.connection.createStatement();
        // statement.setQueryTimeout(30);  // set timeout to 30 sec.
        ResultSet rs = statement.executeQuery("select * from tiktok_user");
        Map<String, String> results = new java.util.HashMap<>(Map.of());
        while(rs.next()) {
            results.put(rs.getString("unique_id"), rs.getString("nickname"));
        }
        return results;
    }
}
