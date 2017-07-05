/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.StringJoiner;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Victor
 */
@ManagedBean
@SessionScoped
public class Endorsement extends Login {

    public String endorse(String targetId, String userId, String skill) {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Connection connection = null;
        Statement statement = null;

        try {
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();

            int s = statement.executeUpdate("Insert into lb_endorsement values ('" + userId + "','" + targetId + "','" + skill + "','" + DateAndTime.DateTime() + "')");
            return "confirmationendorsement";
        } catch (SQLException e) {
            e.printStackTrace();
            return "internalError"; //updatefail page

        } finally {
            try {
                statement.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

    }

    //check if selected skill is endorsed?
    public Boolean thisSkillisEndorsed(String targetId, String userId, String skill) {
        //load the driver        
        Boolean result = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from lb_endorsement where id1 ='" + userId + "' AND id2='" + targetId + "' AND skill='" + skill + "'");

            if (resultSet.next()) {
                result = true;
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return result;
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

    //count how many time endorsed?
    public int countThisSkill(String targetId, String skill) {
        //load the driver        
        int count = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from lb_endorsement where id2='" + targetId + "' AND skill='" + skill + "'");

            while (resultSet.next()) {
                count++;
            }
            return count;

        } catch (SQLException e) {
            e.printStackTrace();
            return count;
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

    //who endorsed?
    public String whoEndorsed(String targetId, String skill) {
        //load the driver        
        int count = 0;
        int count2 = 0;
        String result = "";
        StringJoiner sj = new StringJoiner(", ");

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Statement statement2 = null;
        ResultSet resultSet2 = null;

        try {
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            statement2 = connection.createStatement();
            resultSet = statement.executeQuery("Select DISTINCT * from lb_endorsement where id2='" + targetId + "' AND skill='" + skill + "' ORDER BY date DESC");

            while (resultSet.next()) {
                resultSet2 = statement2.executeQuery("Select * from lb_profile where id='" + resultSet.getString("id1") + "'");
                if (resultSet2.next() && count < 3)//limit show 3 endorsers
                {
                    sj.add(resultSet2.getString("fname") + " " + resultSet2.getString("lname"));
                    count++;
                }
                count2++;
            }

            if (count > 0) {
                result = ("Endorsed by: " + sj.toString());
            }
            if (count2 > 3) {
                result += " and others...";
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            try {
                resultSet2.close();
                resultSet.close();
                statement.close();
                statement2.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    //recent endorsed?
    public ArrayList<String> recentEndorsed(String userId) {
        //load the driver        
        int count = 0;
        ArrayList<String> results = new ArrayList<String>();

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Statement statement2 = null;
        ResultSet resultSet2 = null;

        try {
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            statement2 = connection.createStatement();
            resultSet = statement.executeQuery("Select DISTINCT * from lb_endorsement where id2='" + userId + "' ORDER BY date DESC");

            while (resultSet.next()) {
                resultSet2 = statement2.executeQuery("Select * from lb_profile where id='" + resultSet.getString("id1") + "'");
                if (resultSet2.next() && count < 3)//limit show 3 recent endorsed
                {
                    results.add("- " + resultSet.getString("skill") + " endorsed by " + resultSet2.getString("fname") + " " + resultSet2.getString("lname"));
                    count++;
                }
            }

            return results;

        } catch (SQLException e) {
            e.printStackTrace();
            return results;
        } finally {
            try {
                resultSet2.close();
                resultSet.close();
                statement.close();
                statement2.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

}
