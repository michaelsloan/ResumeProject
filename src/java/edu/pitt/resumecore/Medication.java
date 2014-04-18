/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pitt.resumecore;

import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlDbUtilities;
import edu.pitt.utilities.StringUtilities;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author michaelsloan
 */
public class Medication {
    private String medID;
    private String medicationName;
    private String categoryID;
    private String categoryName;
    
    private MySqlDbUtilities db;
    
    public Medication(String medID){
        db = new MySqlDbUtilities();
        
        String sql = "SELECT * FROM emr.medications a, emr.medicationcategories b"
                + " WHERE medID = '" + medID + "' AND a.categoryId = b.categoryId;";
       
        try {
            ResultSet rs = db.getResultSet(sql);
                if(rs.next()){
                    this.medID = rs.getString("medID");
                    this.categoryID = rs.getString("categoryID");
                    this.medicationName = rs.getString("medication");
                    this.categoryName = rs.getString("categoryName");
                }
            
            }catch (SQLException ex) {
            ErrorLogger.log("An error had occured in Medication(String medID) constructor of Medication class"+ ex.getMessage());
            ErrorLogger.log(sql);
           }
        finally{
            db.closeDbConnection();
        }
    }
    
    public JSONObject getMedicationAsJSON(){
        JSONObject  med = new JSONObject();
            
        try{    
            med.put("medID", this.medID);
            med.put("categoryID", this.categoryID);
            med.put("medicationName", this.medicationName);     
            med.put("categoryName", this.categoryName);
           
        } catch (JSONException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return med;  
    } 

    /**
     * @return the medID
     */
    public String getMedID() {
        return medID;
    }

    /**
     * @param medID the medID to set
     */
    public void setMedID(String medID) {
        String sql = "UPDATE emr.medications SET medId = '" + StringUtilities.cleanMySqlInsert(medID) + "' ";
        sql += "WHERE medId = '" + this.medID + "';";
        db.executeQuery(sql);
        this.medID = medID;
    }

    /**
     * @return the medicationName
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * @param medicationName the medicationName to set
     */
    public void setMedicationName(String medicationName) {
        String sql = "UPDATE emr.medications SET medication = '" + StringUtilities.cleanMySqlInsert(medicationName) + "' ";
        sql += "WHERE medication = '" + this.medicationName + "';";
        db.executeQuery(sql);
        
        this.medicationName = medicationName;
    }

    /**
     * @return the categoryID
     */
    public String getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(String categoryID) {
        String sql = "UPDATE emr.medications SET categoryId = '" + StringUtilities.cleanMySqlInsert(categoryID) + "' ";
        sql += "WHERE categoryId = '" + this.categoryID + "';";
        db.executeQuery(sql);
        
        this.categoryID = categoryID;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        String sql = "UPDATE emr.medicationcategories SET categoryName = '" + StringUtilities.cleanMySqlInsert(categoryName) + "' ";
        sql += "WHERE categoryName = '" + this.categoryName + "';";
        db.executeQuery(sql);
        
        this.categoryName = categoryName;
    }
           
    
    
}
