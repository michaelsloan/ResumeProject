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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author michaelsloan
 */
public class Symptom {
    
    
    private MySqlDbUtilities db;
    private String symptomID;
    private String symptomName;
    
    public Symptom(String nameOrID){
        db = new MySqlDbUtilities();
        if(StringUtilities.isValidGUID(nameOrID)){
            loadSymptom(nameOrID);
        }
        else{
            newSymptom(nameOrID);
        }
    }
            
    private void newSymptom(String symptomName){
        this.symptomID = UUID.randomUUID().toString();
        this.symptomName = symptomName;
        String sql = "INSERT INTO emr.symptoms (symptomID,symptomName) VALUES ";
        sql += "('" + this.symptomID + "', '" + StringUtilities.cleanMySqlInsert(this.symptomName) + "')";
        db.executeQuery(sql);
    }
    
    
    private void loadSymptom(String symptomID){
        String sql = "SELECT * FROM emr.symptoms WHERE symptomID = '" + symptomID + "'";
        try {
            ResultSet rs = db.getResultSet(sql);
            if(rs.next()){
                this.symptomID = rs.getString("symptomID");
                this.symptomName = rs.getString("symptomName");
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error had occured in loadSymptom() method of Symptom class. " + ex.getMessage());
            ErrorLogger.log(sql);
        }
    }
    
   public MySqlDbUtilities getDb() {
        return db;
    }
    
    public JSONObject getSymptomAsJSON(){
            JSONObject symptom = new JSONObject();
            
        try{    
            symptom.put("symptomID", this.symptomID);
            symptom.put("symptomName", this.symptomName);

        } catch (JSONException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return symptom;
    }
    
    public String getSymptomID(){
        return this.symptomID;
    }
    
    public String getSymptomName(){
        return this.symptomName;
    }
    
    public void setSymptomName(String symptomName){
        String sql = "UPDATE emr.symptoms SET symptomName = '" + StringUtilities.cleanMySqlInsert(symptomName) + "' ";
        sql += "WHERE symptomID = '" + this.symptomID + "';";
        db.executeQuery(sql);
        this.symptomName = symptomName;
    }
}
