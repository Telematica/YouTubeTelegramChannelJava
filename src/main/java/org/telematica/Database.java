package org.telematica;

import java.sql.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Database {
    public static Connection connection;
    private static final String NO_CONNECTED_MESSAGE = "No connection is up, please connect().";
    private static final String CONNECTED_MESSAGE = "Connection to SQLite has been established.";

    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:" + System.getenv("JAVA_SQLITE_DB");
        Database.connection = DriverManager.getConnection(url);
        System.out.println(CONNECTED_MESSAGE);
    }

    public static void transactional(Runnable fn) throws SQLException {
        try {
            Database.connect();
            Database.connection.setAutoCommit(false);
            fn.run();
            Database.connection.commit();
        } catch (RuntimeException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            Database.connection.rollback();
            throw new RuntimeException(e);
        }
        Database.connection.close();
    }

    public static Map<String, String> getAllTiktokUsers() throws SQLException {
        if (connection == null) {
            throw new RuntimeException(Database.NO_CONNECTED_MESSAGE);
        }
        Statement statement = Database.connection.createStatement();
        // statement.setQueryTimeout(30);  // set timeout to 30 sec.
        ResultSet rs = statement.executeQuery("select * from tiktok_user");
        Map<String, String> results = new HashMap<>(Map.of());
        while(rs.next()) {
            results.put(rs.getString("unique_id"), rs.getString("nickname"));
        }
        return results;
    }

    public static Map<String, String[]> getAllYouTubeChannels() throws SQLException {
        if (connection == null) {
            throw new RuntimeException(Database.NO_CONNECTED_MESSAGE);
        }
        Statement statement = Database.connection.createStatement();
        // statement.setQueryTimeout(30);  // set timeout to 30 sec.
        ResultSet rs = statement.executeQuery("select * from channel");
        Map<String, String[]> results = new HashMap<>(Map.of());
        while(rs.next()) {
            results.put(
                    rs.getString("id"),
                    new String[]{rs.getString("name"), rs.getString("disable_notification")}
            );
        }
        return results;
    }

    public static String getYouTubeLiveById(String liveId) throws SQLException {
        if (connection == null) {
            throw new RuntimeException(Database.NO_CONNECTED_MESSAGE);
        }

        PreparedStatement statement = Database.connection.prepareStatement("SELECT vid FROM live WHERE vid = ?");
        statement.setString(1, liveId);
        return statement.executeQuery().getString("vid");
    }

    public static void createYouTubeLiveEntry(String... fields) throws SQLException {
        if (connection == null) {
            throw new RuntimeException(Database.NO_CONNECTED_MESSAGE);
        }
        DateTimeFormatter datetime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS ZZZZZ");
        String utcDateTimeString = ZonedDateTime.now().format(datetime);
        PreparedStatement statement = Database.connection.prepareStatement(
                "INSERT INTO live (vid,channel_id,live,title,thumbnail,"
                        + "scheduled_start_time,actual_start_time,view_count,live_since,created_at,updated_at) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?)"
        );
        statement.setString(1, fields[0]);
        statement.setString(2, fields[1]);
        statement.setString(3, fields[2]);
        statement.setString(4, fields[3]);
        statement.setString(5, fields[4]);
        statement.setString(6, fields[5]);
        statement.setString(7, fields[6]);
        statement.setString(8, fields[7]);
        statement.setString(9, fields[8]);
        statement.setString(10, utcDateTimeString);
        statement.setString(11, utcDateTimeString);
        statement.executeUpdate();
    }

    public static void createYouTubeLogEntry(String... fields) throws SQLException {
        if (connection == null) {
            throw new RuntimeException(Database.NO_CONNECTED_MESSAGE);
        }
        DateTimeFormatter datetime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS ZZZZZ");
        String utcDateTimeString = ZonedDateTime.now().format(datetime);
        PreparedStatement statement = Database.connection.prepareStatement(
                "INSERT INTO log_entry (log_status_id, channel_id, created_at, updated_at) VALUES(?,?,?,?)"
        );
        statement.setString(1, fields[0]);
        statement.setString(2, fields[1]);
        statement.setString(3, utcDateTimeString);
        statement.setString(4, utcDateTimeString);
        statement.executeUpdate();
    }
}
