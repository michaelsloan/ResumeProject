/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.resumecore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.pitt.utilities.MySqlDbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.StringUtilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Diagnosis {
    private MySqlDbUtilities db;
    private String diagnosisID;
    private String diagnosisName;
    
    public Diagnosis(String nameOrID){
        db = new MySqlDbUtilities();
        if(StringUtilities.isValidGUID(nameOrID)){
            loadDiagnosis(nameOrID);
        }
        else{
            newDiagnosis(nameOrID);
        }
        db.closeDbConnection();
        
    }
    
    public MySqlDbUtilities getDb() {
        return db;
    }
            
    private void newDiagnosis(String diagnosisName){
        this.setDiagnosisID(UUID.randomUUID().toString());
        this.setDiagnosisName(diagnosisName);
        String sql = "INSERT INTO emr.diagnoses (diagnosisId,diagnosisName) VALUES ";
        sql += "('" + this.getDiagnosisID() + "', '" + StringUtilities.cleanMySqlInsert(this.getDiagnosisName()) + "')";
        db.executeQuery(sql);
    }
    
    
    private void loadDiagnosis(String diagnosisID){
        String sql = "SELECT * FROM emr.diagnoses WHERE diagnosisId = '" + diagnosisID + "'";
        try {
            ResultSet rs = db.getResultSet(sql);
            if(rs.next()){
                this.diagnosisID=(rs.getString("diagnosisId"));
                this.diagnosisName=(rs.getString("diagnosisName"));
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error had occured in loadDiagnosisById() method of Diagnoses class. " + ex.getMessage());
            ErrorLogger.log(sql);
        }
    }
    
    public JSONObject getDiagnosisAsJSON(){
            JSONObject  diagnosis = new JSONObject();
        try{    
            diagnosis.put("diagnosisID", this.diagnosisID);
            diagnosis.put("diagnosisName", this.diagnosisName);
            
        } catch (JSONException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return diagnosis;
    }
    
    public String getDiagnosisID(){
        return this.diagnosisID;
    }
    
    public String getDiagnosisName(){
        return this.diagnosisName;
    }
    
    public void setDiagnosisName(String diagnosisName){
        String sql = "UPDATE emr.diagnoses SET diagnosisName = '" + StringUtilities.cleanMySqlInsert(diagnosisName) + "' ";
        sql += "WHERE diagnosisId = '" + this.getDiagnosisID() + "';";
        db.executeQuery(sql);
        this.diagnosisName = diagnosisName;
    }

    /**
     * @param diagnosisID the diagnosisID to set
     */
    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }
    
    
    
}
