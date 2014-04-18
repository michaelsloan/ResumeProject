/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pitt.resumecore;

import edu.pitt.utilities.MySqlDbUtilities;
import edu.pitt.utilities.StringUtilities;

/**
 *
 * @author Alex
 */
public class Employment {
    private String employmentID;
    private String employerName;
    private String responsibilities;
    private String timeEmployed;
    
    private MySqlDbUtilities db;
    
 public Employment(String employmentID){
        db = new MySqlDbUtilities();

        
 }       

    /**
     * @return the employmentID
     */
    public String getEmploymentID() {
        return employmentID;
    }

    /**
     * @param employmentID the employmentID to set
     */
    public void setEmploymentID(String employmentID) {
        this.employmentID = employmentID;
    }

    /**
     * @return the employerName
     */
    public String getEmployerName() {
        return employerName;
    }

    /**
     * @param employerName the employerName to set
     */
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    /**
     * @return the responsibilities
     */
    public String getResponsibilities() {
        return responsibilities;
    }

    /**
     * @param responsibilities the responsibilities to set
     */
    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    /**
     * @return the timeEmployed
     */
    public String getTimeEmployed() {
        return timeEmployed;
    }

    /**
     * @param timeEmployed the timeEmployed to set
     */
    public void setTimeEmployed(String timeEmployed) {
        this.timeEmployed = timeEmployed;
    }

}    