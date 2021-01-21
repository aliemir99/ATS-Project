/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jber5
 */
public class ConnectionFactory {
        private static final String url = "jdbc:mysql://localhost:3306/ATS";
    private static final String userName = "";//User name
    private static final String pass = ""; //Password
    
    public static Connection getConnection() throws SQLException{
        DriverManager.registerDriver(new Driver());
        return DriverManager.getConnection(url, userName, pass);
    }
}
