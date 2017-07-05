/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Victor
 */
@ManagedBean
@RequestScoped
public class Registration {

    private String id;
    private String fname;
    private String lname;
    private String email;
    private String password;

    public String register() {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("Internal Error! Please try again later.");
        }

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            //to search an onlineaccount based on email
            resultSet = statement.executeQuery("Select * from lb_account "
                    + "where email = '"
                    + email + "'");

            if (resultSet.next()) {
                return ("Either you have an online account already "
                        + "or your online ID is not available to register.");
            } else {
                int r = statement.executeUpdate("insert into lb_account(email, psw) "
                        + "values ('" + email + "', '" + password + "')");

                resultSet = statement.executeQuery("Select * from lb_account where email='" + email + "'");
                if (resultSet.next()) {
                    id = resultSet.getString("id");
                }

                int s = statement.executeUpdate("insert into lb_profile "
                        + "values ('" + id + "','" + fname + "','" + lname + "','','','','','','','','','','')");

                return ("Registration Successful! Please return to "
                        + "sign in to your account.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ("Internal Error! Please try again later.");

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
