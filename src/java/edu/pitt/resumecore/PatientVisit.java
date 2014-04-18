/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pitt.resumecore;

import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlDbUtilities;
import edu.pitt.utilities.StringUtilities;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class PatientVisit {
    
    private String examID;
    private String complaints;
    private String notes;
    private Date dateOfVisit;
    private ArrayList<Diagnosis> diagnosisList = new ArrayList<Diagnosis>();
    private ArrayList<Symptom> symptomList = new ArrayList<Symptom>();
    private ArrayList<Prescription> prescriptionList = new ArrayList<Prescription>();
    
    private MySqlDbUtilities db;
    
    
    
    public PatientVisit(User patient){
        db = new MySqlDbUtilities();
        this.examID = UUID.randomUUID().toString();
        
        String sql = "INSERT INTO emr.patientvisitrecord ";
        sql += "(examId,patientId,complaints,notes,dateOfVisit)";
        sql += " VALUES (";
        sql += "'" + this.getExamID() + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(patient.getUserID()) + "', ";
        sql += "'null', ";
        sql += "'null', ";
        sql += "DATE_FORMAT(NOW(),'%Y-%m-%d %h:%i:%s'));";
     
        db.executeQuery(sql);
    }
    
    public PatientVisit(String examID){
        db = new MySqlDbUtilities();
        
        String sql = "SELECT * FROM emr.patientvisitrecord a "
                + "JOIN emr.patientvisitrecord_diagnoses b ON examId = b.fk_examId "
                + "JOIN emr.diagnoses ON diagnosisId = fk_diagnosisId "
                + "JOIN emr.patientvisitrecord_symptoms ON examId = b.fk_examId "
                + "JOIN emr.symptoms ON symptomId = fk_symptomId "
                + "JOIN emr.prescriptions c ON a.patientId = c.patientId ";
        sql+= "WHERE a.examId = '"+examID+"';";
        
        setAllUserProperties(sql);
        db.closeDbConnection();
        
    }
    
    public void setAllUserProperties(String sql){
                    this.examID = "";
                    this.complaints= "";
                    this.notes = "";
        
        try {
            ResultSet rs = db.getResultSet(sql);
                if(rs.next()){
                    this.examID = rs.getString("examId");
                    this.complaints = rs.getString("complaints");
                    this.notes = rs.getString("notes");
                    this.dateOfVisit = rs.getDate("dateOfVisit");
                    
                    Diagnosis diagnosis = new Diagnosis(rs.getString("diagnosisId"));
                    this.diagnosisList.add(diagnosis);
                    
                    Symptom symptom = new Symptom(rs.getString("symptomId"));
                    this.symptomList.add(symptom);
                    
                    User patient = new User(rs.getString("patientId"));
                    
                    Prescription prescription = new Prescription(this,patient);
                    this.prescriptionList.add(prescription);
                }
            
        } catch (SQLException ex) {
            
            ErrorLogger.log("An error had occured in PatientVisit(String examID) constructor of the PatientVisit class. " + ex.getMessage());
            ErrorLogger.log(sql);
        }
    }
    
    public JSONObject getPatientVisitAsJSON(){
            JSONObject  patientvisit = new JSONObject();
            JSONArray patientDiagnosisList = new JSONArray();
            JSONArray patientSymptomList = new JSONArray();
            JSONArray patientPrescriptionList = new JSONArray();
        try{    
            patientvisit.put("examId", this.examID);
            if(this.dateOfVisit!=null){
                 patientvisit.put("dateOfVisit", this.dateOfVisit);
            }
            else{
                 patientvisit.put("dateOfVisit", "");
            }
            patientvisit.put("complaints", this.complaints);
            patientvisit.put("notes", this.notes);
           
            
            for(Diagnosis diagnosis: diagnosisList){
                patientDiagnosisList.put(diagnosis.getDiagnosisAsJSON());
            }
            
            for(Symptom symptom: symptomList){
                patientSymptomList.put(symptom.getSymptomAsJSON());
            }
            
            for(Prescription prescription: prescriptionList){
                patientPrescriptionList.put(prescription.getPrescriptionAsJSON());
            }
            
            patientvisit.put("diagnosisList", patientDiagnosisList);
            patientvisit.put("symptomList", patientSymptomList);
            patientvisit.put("prescriptionList", patientPrescriptionList);
            
        } catch (JSONException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return patientvisit;
    }
    
    public void addSymptom(String symptomNameOrID){
        Symptom symptom = new Symptom(symptomNameOrID);
        this.getSymptomList().add(symptom);
    }
    
    public void addPrescription(PatientVisit patientVisit, User patient){
        Prescription prescription = new Prescription(patientVisit,patient);
        this.getPrescriptionList().add(prescription);
    }
    
    public void addDiagnosis(String diagnosisNameOrID){
        Diagnosis diagnosis = new Diagnosis(diagnosisNameOrID);
        this.getDiagnosisList().add(diagnosis);
    }
    
    public ArrayList<Diagnosis> getDiagnosisList(){
        return diagnosisList;
    }
    
    public ArrayList<Symptom> getSymptomList(){
        return symptomList;
    }
    
    public ArrayList<Prescription> getPrescriptionList(){
        return prescriptionList;
    }
    
    public String toString(){
        return this.getExamID();
    }

    /**
     * @return the examID
     */
    public String getExamID() {
        return examID;
    }

    /**
     * @param examID the examID to set
     */
    public void setExamID(String examID) {
        
        this.examID = examID;
    }

    /**
     * @return the complaints
     */
    public String getComplaints() {
        return complaints;
    }

    /**
     * @param complaints the complaints to set
     */
    public void setComplaints(String complaint) {
        String sql = "UPDATE emr.patientvisitrecord SET complaints = '" + StringUtilities.cleanMySqlInsert(complaint)+ "'  ";
        sql += "WHERE examId = '" + this.examID + "';";
        db.executeQuery(sql);
        
        this.complaints = complaint;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        String sql = "UPDATE emr.patientvisitrecord SET notes = '" + StringUtilities.cleanMySqlInsert(notes)+ "'  ";
        sql += "WHERE examId = '" + this.examID + "';";
        db.executeQuery(sql);
        
        
        this.notes = notes;
    }

    /**
     * @return the dateOfVisit
     */
    public Date getDateOfVisit() {
        return dateOfVisit;
    }

    /**
     * @param dateOfVisit the dateOfVisit to set
     */
    public void setDateOfVisit(Date dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }
}
