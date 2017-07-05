/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringJoiner;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Victor
 */
@ManagedBean
@SessionScoped
public class Job extends Login {

    private String jobid;
    private String userid;
    private String title;
    private String desc;
    private String status;
    private String targetJobId;

    public Job() {

    }

    public Job(String Jobid, String Userid, String Title, String Desc) {
        jobid = Jobid;
        userid = Userid;
        title = Title;
        desc = Desc;
    }

    public Job(String Jobid, String Userid, String Status) {
        jobid = Jobid;
        userid = Userid;
        status = Status;
    }

    //post the job
    public String post() {
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

            int s = statement.executeUpdate("insert into lb_job(`creatorid`, `title`, `desc`) "
                    + "values ('" + userid + "','" + title + "','" + desc + "')");

            resultSet = statement.executeQuery("Select * from lb_job ORDER BY jobid DESC");
            if (resultSet.next()) {
                jobid = resultSet.getString("jobid");
            }

            int r = statement.executeUpdate("insert into lb_job_status "
                    + "values ('" + jobid + "', '" + userid + "', 'posted','" + DateAndTime.DateTime() + "')");
            return ("Job posted!");

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

    //get job posted
    public ArrayList<Job> getJobPosted(String inputId) {
        //load the driver
        ArrayList<Job> results = new ArrayList<Job>();
        int count = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection connection = null;
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

            //find all job posted
            resultSet = statement.executeQuery("Select DISTINCT * from lb_job_status where creatorid ='" + inputId + "' and status ='posted'");
            while (resultSet.next() && count < 5) //show limit 5 job posted
            {
                resultSet2 = statement2.executeQuery("Select DISTINCT * from lb_job where jobid ='" + resultSet.getString("jobid") + "'");
                if (resultSet2.next()) {
                    results.add(new Job(resultSet2.getString("jobid"), resultSet2.getString("creatorid"), resultSet2.getString("title"), resultSet2.getString("desc")));
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
                statement2.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    //who shared?
    public String whoShared(String jobId) {
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
            resultSet = statement.executeQuery("Select DISTINCT * from lb_job_status where jobid='" + jobId + "' AND status='shared' ORDER BY date DESC");

            while (resultSet.next()) {
                resultSet2 = statement2.executeQuery("Select * from lb_profile where id='" + resultSet.getString("creatorid") + "'");
                if (resultSet2.next() && count < 3)//limit show 3 sharers
                {
                    sj.add(resultSet2.getString("fname") + " " + resultSet2.getString("lname"));
                    count++;
                }
                count2++;
            }

            if (count > 0) {
                result = ("Recently shared by " + sj.toString());
            } else {
                result = ("Not recently shared by anyone");
            }
            if (count2 > 3) {
                result += " and " + (count2 - 3) + " other people";
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

    //who applied?
    public String whoApplied(String jobId) {
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
            resultSet = statement.executeQuery("Select DISTINCT * from lb_job_status where jobid='" + jobId + "' AND status='applied' ORDER BY date DESC");

            while (resultSet.next()) {
                resultSet2 = statement2.executeQuery("Select * from lb_profile where id='" + resultSet.getString("creatorid") + "'");
                if (resultSet2.next() && count < 3)//limit show 3 appliers
                {
                    sj.add(resultSet2.getString("fname") + " " + resultSet2.getString("lname"));
                    count++;
                }
                count2++;
            }

            if (count > 0) {
                result = ("Recently applied by " + sj.toString());
            } else {
                result = ("Not recently applied by anyone");
            }
            if (count2 > 3) {
                result += " and " + (count2 - 3) + " other people";
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

    //get available jobs
    public ArrayList<Job> getAvailableJobs(String inputId) {
        //load the driver
        ArrayList<Job> results = new ArrayList<Job>();
        ArrayList<String> available = new ArrayList<String>();
        ArrayList<String> ignore = new ArrayList<String>();
        int count = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection connection = null;
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

            ArrayList<String> friends = friendList(inputId); // get friend list

            //get all job posted and shared from friends
            for (int i = 0; i < friends.size(); i++) {
                resultSet = statement.executeQuery("Select DISTINCT * from lb_job_status where creatorid ='" + friends.get(i) + "' AND (status ='posted' OR status ='shared') ORDER BY date DESC");
                while (resultSet.next()) {
                    resultSet2 = statement2.executeQuery("Select DISTINCT * from lb_job where jobid ='" + resultSet.getString("jobid") + "'");
                    if (resultSet2.next()) {
                        available.add(resultSet2.getString("jobid"));
                    }
                }
            }

            //remove duplicates
            HashSet<String> set = new HashSet<>(available);
            ArrayList<String> availableDistinct = new ArrayList<>(set);

            //get all ignored jobs
            resultSet = statement.executeQuery("Select DISTINCT * from lb_job_status where creatorid ='" + inputId + "' AND (status ='ignored' OR status ='applied' OR status ='saved' OR status ='posted')");
            while (resultSet.next()) {
                ignore.add(resultSet.getString("jobid"));
            }

            //remove all ignore jobs
            availableDistinct.removeAll(ignore);

            for (int i = 0; i < availableDistinct.size(); i++) {
                resultSet2 = statement2.executeQuery("Select DISTINCT * from lb_job where jobid ='" + availableDistinct.get(i) + "'");
                while (resultSet2.next() && count < 5) //limit available job display most recent 5
                {
                    results.add(new Job(resultSet2.getString("jobid"), resultSet2.getString("creatorid"), resultSet2.getString("title"), resultSet2.getString("desc")));
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
                statement2.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    //save the job
    public String saveJob(String userId) {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("Internal Error! Please try again later.");
        }

        Connection connection = null;
        Statement statement = null;

        try {

            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            int r = statement.executeUpdate("insert into lb_job_status "
                    + "values ('" + targetJobId + "', '" + userId + "', 'saved','" + DateAndTime.DateTime() + "')");
            return ("Job saved!");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Internal Error! Please try again later.");

        } finally {
            try {
                statement.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    //get saved jobs
    public ArrayList<Job> getSavedJobs(String userId) {
        //load the driver
        ArrayList<Job> results = new ArrayList<Job>();
        ArrayList<String> saved = new ArrayList<String>();
        ArrayList<String> applied = new ArrayList<String>();

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection connection = null;
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

            //get all job saved
            resultSet = statement.executeQuery("Select DISTINCT * from lb_job_status where creatorid ='" + userId + "' AND status ='saved' ORDER BY date DESC");
            while (resultSet.next()) {
                saved.add(resultSet.getString("jobid"));
            }

            //get all applied jobs
            resultSet = statement.executeQuery("Select DISTINCT * from lb_job_status where creatorid ='" + userId + "' AND status ='applied'");
            while (resultSet.next()) {
                applied.add(resultSet.getString("jobid"));
            }

            //remove all ignore jobs
            saved.removeAll(applied);

            for (int i = 0; i < saved.size(); i++) {
                resultSet2 = statement2.executeQuery("Select DISTINCT * from lb_job where jobid ='" + saved.get(i) + "'");
                while (resultSet2.next()) //limit available job display to 5
                {
                    results.add(new Job(resultSet2.getString("jobid"), resultSet2.getString("creatorid"), resultSet2.getString("title"), resultSet2.getString("desc")));
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
                statement2.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    //get applied jobs
    public ArrayList<Job> getAppliedJobs(String userId) {
        //load the driver
        ArrayList<Job> results = new ArrayList<Job>();

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection connection = null;
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

            resultSet = statement.executeQuery("Select DISTINCT * from lb_job_status where creatorid ='" + userId + "' AND status ='applied' ORDER BY date DESC");
            while (resultSet.next()) {
                resultSet2 = statement2.executeQuery("Select DISTINCT * from lb_job where jobid ='" + resultSet.getString("jobid") + "'");
                while (resultSet2.next()) {
                    results.add(new Job(resultSet2.getString("jobid"), resultSet2.getString("creatorid"), resultSet2.getString("title"), resultSet2.getString("desc")));
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
                statement2.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    //check if job saved?
    public Boolean isJobSaved(String targetJobId, String userId) {
        //load the driver

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

            //get saved job
            resultSet = statement.executeQuery("Select DISTINCT * from lb_job_status where jobid ='" + targetJobId + "' AND creatorid ='" + userId + "' AND status ='saved'");
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    //apply for the job
    public String applyJob(String userId) {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("Internal Error! Please try again later.");
        }

        Connection connection = null;
        Statement statement = null;

        try {

            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            int r = statement.executeUpdate("insert into lb_job_status "
                    + "values ('" + targetJobId + "', '" + userId + "', 'applied','" + DateAndTime.DateTime() + "')");
            return ("Job applied!");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Internal Error! Please try again later.");

        } finally {
            try {
                statement.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    //check if job applied?
    public Boolean isJobApplied(String targetJobId, String userId) {
        //load the driver

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

            //get applied job
            resultSet = statement.executeQuery("Select DISTINCT * from lb_job_status where jobid ='" + targetJobId + "' AND creatorid ='" + userId + "' AND status ='applied'");
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    //share the job
    public String shareJob(String userId) {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("Internal Error! Please try again later.");
        }

        Connection connection = null;
        Statement statement = null;

        try {

            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            int r = statement.executeUpdate("insert into lb_job_status "
                    + "values ('" + targetJobId + "', '" + userId + "', 'shared','" + DateAndTime.DateTime() + "')");
            return ("Job shared!");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Internal Error! Please try again later.");

        } finally {
            try {
                statement.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    //check if job shared?
    public Boolean isJobShared(String targetJobId, String userId) {
        //load the driver

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

            //get shared job
            resultSet = statement.executeQuery("Select DISTINCT * from lb_job_status where jobid ='" + targetJobId + "' AND creatorid ='" + userId + "' AND status ='shared'");
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    //ignore the job
    public String ignoreJob(String userId) {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("Internal Error! Please try again later.");
        }

        Connection connection = null;
        Statement statement = null;

        try {

            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/led6728";

            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,
                    "led6728", "1201845");
            statement = connection.createStatement();
            int r = statement.executeUpdate("insert into lb_job_status "
                    + "values ('" + targetJobId + "', '" + userId + "', 'ignored','" + DateAndTime.DateTime() + "')");
            return ("Job ignored!");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Internal Error! Please try again later.");

        } finally {
            try {
                statement.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    public String getTargetJobId() {
        return targetJobId;
    }

    public void setTargetJobId(String targetJobId) {
        this.targetJobId = targetJobId;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
