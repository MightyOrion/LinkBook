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
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Victor
 */
@ManagedBean
@SessionScoped
public class Profile {

    private String id;
    private String email;
    private String fname;
    private String lname;
    private String location;
    private String company;
    private String jobtitle;
    private String school;
    private String degree;
    private String type;
    private String gender;
    private String skill1;
    private String skill2;
    private String skill3;

    public Profile() {

    }

    public Profile(String Id, String Fname, String Lname, String Location,
            String Company, String Jobtitle, String School, String Degree,
            String Type, String Gender, String Skill1, String Skill2, String Skill3) {
        id = Id;
        fname = Fname;
        lname = Lname;
        location = Location;
        company = Company;
        jobtitle = Jobtitle;
        school = School;
        degree = Degree;
        type = Type;
        gender = Gender;
        skill1 = Skill1;
        skill2 = Skill2;
        skill3 = Skill3;
    }

    public String edit(String Id, String Email,
            String Fname, String Lname, String Location,
            String Company, String Jobtitle, String School,
            String Degree, String Type, String Gender,
            String Skill1, String Skill2, String Skill3) {

        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("internalError");
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
            int s = statement.executeUpdate("Update lb_profile "
                    + "set fname = '" + Fname + "', lname = '" + Lname + "', location = '" + Location + "', company = '" + Company + "', jobtitle = '" + Jobtitle
                    + "', school = '" + School + "', degree = '" + Degree
                    + "',type = '" + Type + "',gender = '" + Gender + "', skill1 = '" + Skill1
                    + "', skill2 = '" + Skill2 + "', skill3 = '" + Skill3 + "' where id = '" + Id + "'");
            int t = statement.executeUpdate("Update lb_account "
                    + "set email = '" + Email + "' where id = '" + Id + "'");
            return ("suggestion");     //updateconfirmation page       

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError"); //updatefail page

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSkill1() {
        return skill1;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public String getSkill3() {
        return skill3;
    }

    public void setSkill3(String skill3) {
        this.skill3 = skill3;
    }

}
