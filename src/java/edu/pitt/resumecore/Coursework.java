package edu.pitt.resumecore;

import edu.pitt.utilities.ErrorLogger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import edu.pitt.utilities.MySqlDbUtilities;
import edu.pitt.utilities.StringUtilities;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jimmy
 */
public class Coursework {
    private String courseworkID;
    private String coursework1;
    private String coursework2;
    private String coursework3;
    private String coursework4;
    private String coursework5;
    
    private MySqlDbUtilities db;
     
    public Coursework(String courseworkID){
                db = new MySqlDbUtilities();
                if(StringUtilities.isValidGUID(courseworkID)){
             String sql = "SELECT * FROM emr.coursework WHERE courseworkId = '" + courseworkID + "'";
        try {
            ResultSet rs = db.getResultSet(sql);
            if(rs.next()){
                this.coursework1 = rs.getString("coursework1");
                this.coursework2 = rs.getString("coursework2");
                this.coursework3 = rs.getString("coursework3");
                this.coursework4 = rs.getString("coursework4");
                this.coursework5 = rs.getString("coursework5");
                
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error occured in coursework class. " + ex.getMessage());
            ErrorLogger.log(sql);
        }
        }
    }
    public Coursework(String coursework1, String coursework2, String coursework3, String coursework4, String coursework5){
        this.courseworkID = UUID.randomUUID().toString();
        this.coursework1 = coursework1;
        this.coursework2 = coursework2;
        this.coursework3 = coursework3;
        this.coursework4 = coursework4;
        this.coursework5 = coursework5;
        String sql = "INSERT INTO resume.Coursework (courseworkID,coursework1, coursework2, coursework3, coursework4, coursework5)";
                sql += " VALUES "+ "('"+courseworkID+ "', '" + coursework1 + "', '" + coursework2 + "', '" + coursework3 + "', '" + coursework4 + "' , '"+coursework5+"');"; 
                
        db.executeQuery(sql);
    }

    /**
     * @return the courseworkID
     */
    public String getCourseworkID() {
        return courseworkID;
    }

    /**
     * @return the coursework1
     */
    public String getCoursework1() {
        return coursework1;
    }

    /**
     * @param coursework1 the coursework1 to set
     */
    public void setCoursework1(String coursework1) {
        this.coursework1 = coursework1;
    }

    /**
     * @return the coursework2
     */
    public String getCoursework2() {
        return coursework2;
    }

    /**
     * @param coursework2 the coursework2 to set
     */
    public void setCoursework2(String coursework2) {
        this.coursework2 = coursework2;
    }

    /**
     * @return the coursework3
     */
    public String getCoursework3() {
        return coursework3;
    }

    /**
     * @param coursework3 the coursework3 to set
     */
    public void setCoursework3(String coursework3) {
        this.coursework3 = coursework3;
    }

    /**
     * @return the coursework4
     */
    public String getCoursework4() {
        return coursework4;
    }

    /**
     * @param coursework4 the coursework4 to set
     */
    public void setCoursework4(String coursework4) {
        this.coursework4 = coursework4;
    }
}
