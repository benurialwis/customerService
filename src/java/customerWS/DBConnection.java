/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customerWS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class DBConnection {

    public static Connection connectDB() {

        Connection conn = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            String db = "jdbc:mysql://localhost:3306/ebanksystem";
            String user = "root";
            String pwd = "";

            conn = DriverManager.getConnection(db, user, pwd);

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
