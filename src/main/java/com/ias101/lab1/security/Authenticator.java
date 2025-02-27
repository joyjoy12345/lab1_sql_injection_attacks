package com.ias101.lab1.security;

import com.ias101.lab1.database.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Authentication class for user validation
 */

public class Authenticator {


    private static String sanitize(String input){
        if (input == null){
            return "";
        }


        return input.replace("'", "''");
    }

    public static boolean authenticateUser(String username, String password) {

        username = sanitize(username);
        password = sanitize(password);

        try(var conn = DBUtil.connect("jdbc:sqlite:src/main/resources/database/sample.db",
                "root","root")) {
            try(var statement = conn.createStatement()) {
                var query = "SELECT * FROM user_data WHERE username = '" + username + "'AND password ='" + password + "'";
                System.out.println(query);
                ResultSet rs = statement.executeQuery(query);

                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    
}