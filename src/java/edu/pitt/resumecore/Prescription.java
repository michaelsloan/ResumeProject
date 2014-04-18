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
import java.util.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author michaelsloan
 */
public class Prescription {

    private String prescriptionId;
    private Date prescriptionDate;
    private Date expires;
    private PatientVisit patientVisit;
    private User patient;
    private ArrayList<Medication> medicationList = new ArrayList<Medication>();

    private MySqlDbUtilities db;

    public Prescription(PatientVisit patientVisit, User patient) {
        db = new MySqlDbUtilities();

        String sql = "SELECT * FROM emr.prescriptions JOIN emr.prescription_medications "
                + "ON prescriptionId=fk_prescriptionId "
                + "JOIN emr.medications ON medId=fk_medicationId ";
        sql += "WHERE patientVisitId = '" + patientVisit.getExamID() + "' AND patientId = '" + patient.getUserID() + "';";

        try {
            ResultSet rs = db.getResultSet(sql);
            if (rs.next()) {
                this.prescriptionId = rs.getString("prescriptionId");
                this.prescriptionDate = rs.getDate("prescriptionDate");
                this.expires = rs.getDate("expires");
                this.patientVisit = patientVisit; //new PatientVisit(rs.getString("patientVisitId"));
                this.patient = patient; //new User(rs.getString("patientId"));
                Medication med = new Medication(rs.getString("medId"));
                this.getMedicationList().add(med);
            }

        } catch (SQLException ex) {
            ErrorLogger.log("An error had occured in Prescription(PatientVisit patientVisit, User patient) constructor of Medication class" + ex.getMessage());
            ErrorLogger.log(sql);
        }
        finally{
            db.closeDbConnection();
        }
    }

    public JSONObject getPrescriptionAsJSON() {
        JSONObject prescription = new JSONObject();
        JSONArray patientMedicationList = new JSONArray();
        try {
            prescription.put("prescriptionId", this.prescriptionId);
            prescription.put("prescriptionDate", this.prescriptionDate);
            prescription.put("expires", this.expires);
            prescription.put("patientVisit", this.patientVisit);
            prescription.put("patient", this.patient);

            for (Medication medication : medicationList) {
                patientMedicationList.put(medication.getMedicationAsJSON());
            }

        } catch (JSONException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prescription;
    }

    public Medication addMedication(Medication medication) {
        Medication med = medication;
        this.getMedicationList().add(med);

        return med;
    }

    public ArrayList<Medication> getMedicationList() {
        return medicationList;
    }

    /**
     * @return the prescriptionId
     */
    public String getPrescriptionId() {
        return prescriptionId;
    }

    /**
     * @return the prescriptionDate
     */
    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    /**
     * @param prescriptionDate the prescriptionDate to set
     */
    public void setPrescriptionDate(Date prescriptionDate) {
        String sql = "UPDATE emr.prescriptions SET prescriptionDate = '" + StringUtilities.cleanMySqlInsert(prescriptionDate.toString()) + "' ";
        sql += "WHERE prescriptionDate = '" + this.prescriptionDate + "';";
        db.executeQuery(sql);

        this.prescriptionDate = prescriptionDate;
    }

    /**
     * @return the expires
     */
    public Date getExpires() {
        return expires;
    }

    /**
     * @param expires the expires to set
     */
    public void setExpires(Date expires) {
        String sql = "UPDATE emr.prescriptions SET expires = '" + StringUtilities.cleanMySqlInsert(expires.toString()) + "' ";
        sql += "WHERE prescriptionId = '" + this.prescriptionId + "';";
        db.executeQuery(sql);

        this.expires = expires;
    }

    /**
     * @return the patientVisit
     */
    public PatientVisit getPatientVisit() {
        return patientVisit;
    }

    /**
     * @param patientVisit the patientVisit to set
     */
    public void setPatientVisit(PatientVisit patientVisit) {
        String sql = "UPDATE emr.prescriptions SET patientVisitId = '" + StringUtilities.cleanMySqlInsert(patientVisit.toString()) + "' ";
        sql += "WHERE patientId = '" + this.patient + "';";
        db.executeQuery(sql);

        this.patientVisit = patientVisit;
    }

    /**
     * @return the patient
     */
    public User getPatient() {
        return patient;
    }

    /**
     * @param patient the patient to set
     */
    public void setPatient(User patient) {
        String sql = "UPDATE emr.prescriptions SET patientId = '" + StringUtilities.cleanMySqlInsert(patient.toString()) + "' ";
        sql += "WHERE patientId = '" + this.patient.toString() + "';";
        db.executeQuery(sql);

        this.patient = patient;
    }

}
