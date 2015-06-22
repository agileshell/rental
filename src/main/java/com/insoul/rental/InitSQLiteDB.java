package com.insoul.rental;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.io.IOUtils;

public class InitSQLiteDB {

    public static void main(String[] args) {
        try {
            ClassLoader classLoader = InitSQLiteDB.class.getClassLoader();
            String sql = IOUtils.toString(classLoader.getResourceAsStream("schema.sql"));

            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:db/rental.db");
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
