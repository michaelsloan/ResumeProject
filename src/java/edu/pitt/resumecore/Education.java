/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pitt.resumecore;

import edu.pitt.utilities.MySqlDbUtilities;
/**
 *
 * @author Alex
 */
public class Education {
    private String eduID;
    private String eduLevel;
    private String schoolName;
    private Double gpa;

    private MySqlDbUtilities db;
    
public Education(String eduID){
        db = new MySqlDbUtilities();
        
        
        
 }
    /**
     * @return the eduID
     */
    public String getEduID() {
        return eduID;
    }

    /**
     * @param eduID the eduID to set
     */
    public void setEduID(String eduID) {
        this.eduID = eduID;
    }

    /**
     * @return the eduLevel
     */
    public String getEduLevel() {
        return eduLevel;
    }

    /**
     * @param eduLevel the eduLevel to set
     */
    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    /**
     * @return the schoolName
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * @param schoolName the schoolName to set
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * @return the gpa
     */
    public Double getGpa() {
        return gpa;
    }

    /**
     * @param gpa the gpa to set
     */
    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}
