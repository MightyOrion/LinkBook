/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Victor
 */
@ManagedBean
@SessionScoped
public class Connection extends Login {

    private String id1;
    private String id2;

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    //constructor
    public Connection() {

    }

    public Connection(String Id1, String Id2) {
        id1 = Id1;
        id2 = Id2;
    }

    public String sendRequest(String targetId, String userId) {
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

            int s = statement.executeUpdate("Insert into lb_connection_request values ('" + userId + "','" + targetId + "')");
            return "confirmnationrequest";
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

    //ignore connection request and redirect page
    public String ignoreRequest(String targetId, String userId) {

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
            int r = statement.executeUpdate("Delete from lb_connection_request where id1='" + targetId + "' AND id2 ='" + userId + "'");

            return "requestignore";

        } catch (SQLException e) {
            e.printStackTrace();
            return "internalError"; //updatefail page

        } finally {
            try {

                statement.close();
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
                return "internalError";
            }
        }

    }

    //accept connection request and redirect page
    public String acceptRequest(String targetId, String userId) {

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
            int r = statement.executeUpdate("Delete from lb_connection_request where id1='" + targetId + "' AND id2 ='" + userId + "'");
            int t = statement.executeUpdate("Insert into lb_connection values ('" + userId + "','" + targetId + "','" + DateAndTime.DateTime() + "')");

            return "requestaccept";

        } catch (SQLException e) {
            e.printStackTrace();
            return "internalError"; //updatefail page

        } finally {
            try {

                statement.close();
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
                return "internalError";
            }
        }
    }

    //check if selected id is connected?
    public Boolean thisIDisConnected(String targetId, String userId) {
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
            resultSet = statement.executeQuery("Select * from lb_connection where (id1 ='" + userId + "' AND id2='" + targetId + "') OR (id1 ='" + targetId + "' AND id2='" + userId + "')");

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

    //check if selected id is on receiving pending request?
    public Boolean thisIDisPendingReceived(String targetId, String userId) {
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
            resultSet = statement.executeQuery("Select * from lb_connection_request where id1 ='" + targetId + "' AND id2='" + userId + "'");

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

    //check if selected id is on sending pending request?
    public Boolean thisIDisPendingSent(String targetId, String userId) {
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
            resultSet = statement.executeQuery("Select * from lb_connection_request where id1 ='" + userId + "' AND id2='" + targetId + "'");

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

    //get pending requests
    public ArrayList<Profile> pendingRequest(String userId) {
        //load the driver
        ArrayList<Profile> results = new ArrayList<Profile>();

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Connection connection = null;
        Statement statement = null;
        Statement statement2 = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;

        try {
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            statement2 = connection.createStatement();

            resultSet = statement.executeQuery("Select DISTINCT * from lb_connection_request where id2 ='" + userId + "'");

            while (resultSet.next()) {
                resultSet2 = statement2.executeQuery("Select * from lb_profile where id ='" + resultSet.getString("id1") + "'");
                if (resultSet2.next()) {
                    results.add(new Profile(resultSet2.getString("id"), resultSet2.getString("fname"), resultSet2.getString("lname"), resultSet2.getString("location"), resultSet2.getString("company"), resultSet2.getString("jobtitle"),
                            resultSet2.getString("school"), resultSet2.getString("degree"), resultSet2.getString("type"),
                            resultSet2.getString("gender"), resultSet2.getString("skill1"), resultSet2.getString("skill2"), resultSet2.getString("skill3")));
                }
            }
            return results;

        } catch (SQLException e) {
            e.printStackTrace();
            return results;
        } finally {
            try {
                resultSet.close();
                resultSet2.close();
                statement.close();
                statement2.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    //get all connected
    public ArrayList<Profile> connectedConnections(String inputId) {
        //load the driver
        ArrayList<Profile> results = new ArrayList<Profile>();

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

            ArrayList<String> friends = friendList(inputId); // get friend list

            //get profile of friends
            for (int i = 0; i < friends.size(); i++) {
                resultSet = statement.executeQuery("Select * from lb_profile where id ='" + friends.get(i) + "'");
                while (resultSet.next() && count < 10) //limit friend list to 10
                {
                    results.add(new Profile(resultSet.getString("id"), resultSet.getString("fname"), resultSet.getString("lname"), resultSet.getString("location"), resultSet.getString("company"), resultSet.getString("jobtitle"),
                            resultSet.getString("school"), resultSet.getString("degree"), resultSet.getString("type"),
                            resultSet.getString("gender"), resultSet.getString("skill1"), resultSet.getString("skill2"), resultSet.getString("skill3")));
                    count++;
                }
            }
            return results;

        } catch (SQLException e) {
            e.printStackTrace();
            return results;
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

    //get suggest connections 
    public ArrayList<Profile> suggestConnections(String userId) {
        //load the driver
        ArrayList<Profile> results = new ArrayList<Profile>();
        ArrayList<String> suggests = new ArrayList<String>();
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

            //get the login profile
            resultSet = statement.executeQuery("Select DISTINCT * from lb_profile where id ='" + userId + "'");
            if (resultSet.next()) {
                theProfile = new Profile(resultSet.getString("id"), resultSet.getString("fname"), resultSet.getString("lname"), resultSet.getString("location"), resultSet.getString("company"), resultSet.getString("jobtitle"),
                        resultSet.getString("school"), resultSet.getString("degree"), resultSet.getString("type"),
                        resultSet.getString("gender"), resultSet.getString("skill1"), resultSet.getString("skill2"), resultSet.getString("skill3"));
            }

            //get user with same school/company
            resultSet = statement.executeQuery("Select DISTINCT * from lb_profile where school ='" + theProfile.getSchool() + "' OR company ='" + theProfile.getCompany() + "'");
            while (resultSet.next() && !resultSet.getString("id").equals(userId)) {
                suggests.add(resultSet.getString("id"));
            }

            ArrayList<String> friends = friendList(userId); // get friend list

            //find connection of each friend
            for (int i = 0; i < friends.size(); i++) {
                resultSet = statement.executeQuery("Select * from lb_connection where id1 ='" + friends.get(i) + "' OR id2 ='" + friends.get(i) + "'");
                while (resultSet.next()) {
                    if (resultSet.getString("id1").equals(friends.get(i))) {
                        suggests.add(resultSet.getString("id2"));
                    } else if (resultSet.getString("id2").equals(friends.get(i))) {
                        suggests.add(resultSet.getString("id1"));
                    }
                }
            }

            //remove duplicates
            Set<String> hs = new HashSet<>();
            hs.addAll(suggests);
            suggests.clear();
            suggests.addAll(hs);

            //remove suggests that already connected or myself            
            for (int i = 0; i < suggests.size(); i++) {
                for (int j = 0; j < friends.size(); j++) {
                    if (suggests.get(i).equals(friends.get(j)) || suggests.get(i).equals(userId)) {
                        suggests.set(i, "");
                    }
                }
            }

            //get profile of suggests
            for (int i = 0; i < suggests.size(); i++) {
                resultSet = statement.executeQuery("Select * from lb_profile where id ='" + suggests.get(i) + "'");
                while (resultSet.next() && count < 5) //limit suggestion list to 5
                {
                    results.add(new Profile(resultSet.getString("id"), resultSet.getString("fname"), resultSet.getString("lname"), resultSet.getString("location"), resultSet.getString("company"), resultSet.getString("jobtitle"),
                            resultSet.getString("school"), resultSet.getString("degree"), resultSet.getString("type"),
                            resultSet.getString("gender"), resultSet.getString("skill1"), resultSet.getString("skill2"), resultSet.getString("skill3")));
                    count++;
                }
            }

            return results;

        } catch (SQLException e) {
            e.printStackTrace();
            return results;
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

    //recent connections
    public ArrayList<String> recentConnections(String inputId) {
        //load the driver        
        ArrayList<String> results = new ArrayList<String>();
        int count = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Connection connection = null;
        Statement statement = null;
        Statement statement2 = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;

        try {
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            statement2 = connection.createStatement();

            //find all friends
            resultSet = statement.executeQuery("Select DISTINCT * from lb_connection where id1 ='" + inputId + "' OR id2 ='" + inputId + "' ORDER BY date DESC");
            while (resultSet.next() && count < 5)//limit show recents 5 connections
            {
                if (resultSet.getString("id1").equals(inputId)) {
                    resultSet2 = statement2.executeQuery("Select DISTINCT * from lb_profile where id ='" + resultSet.getString("id2") + "'");
                    if (resultSet2.next()) {
                        results.add("- " + resultSet2.getString("fname") + " " + resultSet2.getString("lname") + " added you to "
                                + (resultSet2.getString("gender").equals("Male") ? "his" : "her") + " connections");
                    }
                } else if (resultSet.getString("id2").equals(inputId)) {
                    resultSet2 = statement2.executeQuery("Select DISTINCT * from lb_profile where id ='" + resultSet.getString("id1") + "'");
                    if (resultSet2.next()) {
                        results.add("- " + resultSet2.getString("fname") + " " + resultSet2.getString("lname") + " added you to "
                                + (resultSet2.getString("gender").equals("Male") ? "his" : "her") + " connections");
                    }
                }
                count++;
            }

            return results;

        } catch (SQLException e) {
            e.printStackTrace();
            return results;
        } finally {
            try {
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
