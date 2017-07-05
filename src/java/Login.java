/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Victor
 */
@ManagedBean
@SessionScoped
//sessionScoped need to implement the interface Serializable
public class Login implements Serializable {

    //attributes
    protected String id;
    private String email;
    private String password;

    private String searchCat;
    private String searchWord;
    protected String searchid;

    private OnlineAccount theLoginAccount;
    protected Profile theProfile;
    private Profile viewprofile;

    //sign in
    public String login() {
        //load the Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";
        Connection connection = null;  //a connection to the database
        Statement statement = null;    //execution of a statement
        ResultSet resultSet = null;   //set of results

        try {
            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();

            resultSet = statement.executeQuery("Select * from lb_account "
                    + "where email = '"
                    + email + "'");

            if (resultSet.next()) {
                //id is found
                if (password.equals(resultSet.getString("psw"))) {
                    //password is good 
                    //display welcome.xhtml
                    //create an OnlineAccount object
                    id = resultSet.getString("id");

                    theLoginAccount = new OnlineAccount(id, email);

                    resultSet = statement.executeQuery("Select * from lb_profile "
                            + "where id = '"
                            + id + "'");

                    if (resultSet.next()) {

                        if (resultSet.getString("type").isEmpty()) {
                            theProfile = new Profile(resultSet.getString("id"), resultSet.getString("fname"), resultSet.getString("lname"), "", "", "", "", "", "", "", "", "", "");
                            return "welcome";
                        } else {
                            theProfile = new Profile(id, resultSet.getString("fname"), resultSet.getString("lname"), resultSet.getString("location"), resultSet.getString("company"), resultSet.getString("jobtitle"),
                                    resultSet.getString("school"), resultSet.getString("degree"), resultSet.getString("type"),
                                    resultSet.getString("gender"), resultSet.getString("skill1"), resultSet.getString("skill2"), resultSet.getString("skill3"));
                            return "suggestion";

                        }
                    } else {
                        email = "";
                        password = "";
                        return "loginNotOK";
                    }

                } else {
                    email = "";
                    password = "";
                    //display loginNotOK.xhtml
                    return "loginNotOK";
                }
            } else {
                email = "";
                password = "";
                //id is not found, display loginNotOK.xhtml
                return "loginNotOK";

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
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

    //show search results
    public ArrayList<Profile> searchResults() {
        //load the driver
        ArrayList<Profile> results = new ArrayList<Profile>();

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
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

            if (searchCat.equals("Companies")) {
                resultSet = statement.executeQuery("Select * from lb_profile where company like '%" + searchWord + "%'");
            } else if (searchCat.equals("Universities")) {
                resultSet = statement.executeQuery("Select * from lb_profile where school like '%" + searchWord + "%'");
            } else if (searchCat.equals("People")) {
                resultSet = statement.executeQuery("Select * from lb_profile where lname like '%" + searchWord + "%' OR fname like '%" + searchWord + "%'");
            } else if (searchCat.equals("All")) {
                resultSet = statement.executeQuery("Select * from lb_profile where school like '%" + searchWord + "%' OR company like '%" + searchWord + "%' OR lname like '%" + searchWord + "%' OR fname like '%" + searchWord + "%'");
            }

            while (resultSet.next()) {
                if (!id.equals(resultSet.getString("id"))) {
                    results.add(new Profile(resultSet.getString("id"), resultSet.getString("fname"), resultSet.getString("lname"), resultSet.getString("location"), resultSet.getString("company"), resultSet.getString("jobtitle"),
                            resultSet.getString("school"), resultSet.getString("degree"), resultSet.getString("type"),
                            resultSet.getString("gender"), resultSet.getString("skill1"), resultSet.getString("skill2"), resultSet.getString("skill3")));
                }
            }
            return results;

        } catch (SQLException e) {
            e.printStackTrace();
            return results;
        } finally {
            try {
                statement.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    //show profile
    public String viewProfile(String inputId) {

        //load the driver
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
            resultSet = statement.executeQuery("Select * from lb_profile where id ='" + inputId + "'");

            if (resultSet.next()) {
                viewprofile = (new Profile(resultSet.getString("id"), resultSet.getString("fname"), resultSet.getString("lname"), resultSet.getString("location"), resultSet.getString("company"), resultSet.getString("jobtitle"),
                        resultSet.getString("school"), resultSet.getString("degree"), resultSet.getString("type"),
                        resultSet.getString("gender"), resultSet.getString("skill1"), resultSet.getString("skill2"), resultSet.getString("skill3")));

            }
            return "viewprofile";

        } catch (SQLException e) {
            e.printStackTrace();
            return "internalError"; //updatefail page

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
                return "internalError";
            }
        }

    }

    //find all friends
    public ArrayList<String> friendList(String inputId) {
        //load the driver        
        ArrayList<String> friends = new ArrayList<String>();

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Connection connection = null;
        Statement statement = null;
        Statement statement2 = null;
        ResultSet resultSet = null;

        try {
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            statement2 = connection.createStatement();

            //find all friends
            resultSet = statement.executeQuery("Select DISTINCT * from lb_connection where id1 ='" + inputId + "' OR id2 ='" + inputId + "'");
            while (resultSet.next()) {
                if (resultSet.getString("id1").equals(inputId)) {
                    friends.add(resultSet.getString("id2"));
                } else if (resultSet.getString("id2").equals(inputId)) {
                    friends.add(resultSet.getString("id1"));
                }
            }

            return friends;

        } catch (SQLException e) {
            e.printStackTrace();
            return friends;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSearchCat() {
        return searchCat;
    }

    public void setSearchCat(String searchCat) {
        this.searchCat = searchCat;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getSearchid() {
        return searchid;
    }

    public void setSearchid(String searchid) {
        this.searchid = searchid;
    }

    public OnlineAccount getTheLoginAccount() {
        return theLoginAccount;
    }

    public void setTheLoginAccount(OnlineAccount theLoginAccount) {
        this.theLoginAccount = theLoginAccount;
    }

    public Profile getTheProfile() {
        return theProfile;
    }

    public void setTheProfile(Profile theProfile) {
        this.theProfile = theProfile;
    }

    public Profile getViewprofile() {
        return viewprofile;
    }

    public void setViewprofile(Profile viewprofile) {
        this.viewprofile = viewprofile;
    }
}
