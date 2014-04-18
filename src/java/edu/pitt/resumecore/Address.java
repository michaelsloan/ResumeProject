/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.resumecore;

import edu.pitt.utilities.MySqlDbUtilities;
import edu.pitt.utilities.ErrorLogger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.pitt.utilities.StringUtilities;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author dmitriyb
 */
public class Address {
    private String addressID;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String province;
    private String postalCode;
    private String country;

    private MySqlDbUtilities db;

    public Address(String addressID){
        this.addressID = addressID;
        this.addressLine1 = "";
        this.addressLine2 = "";
        this.city = "";
        this.state = "";
        this.province = "";
        this.postalCode = "";
        this.country = "";
        db = new MySqlDbUtilities();
        String sql = "SELECT * FROM resume.address WHERE addressID = '" + addressID + "'";
        try {
            ResultSet rs = db.getResultSet(sql);
            if(rs.next()){
                this.addressLine1 = rs.getString("addressLine1");
                this.addressLine2 = rs.getString("addressLine2");
                this.city = rs.getString("city");
                this.state = rs.getString("state");
                this.province = rs.getString("province");
                this.postalCode = rs.getString("postalCode");
                this.country = rs.getString("country");
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error had occured in Address(String addressID) constructor of Address class. " + ex.getMessage());
            ErrorLogger.log(sql);
        }
        finally{
            db.closeDbConnection();
        }
        
    }
    
    public Address(String addressLine1, String addressLine2, String city, String state, 
            String province, String postalCode, String country){
        this.addressID = UUID.randomUUID().toString();
        db = new MySqlDbUtilities();
        String sql = "INSERT INTO resume.address ";
        sql += "(addressId,addressLine1,addressLine2,city,state,";
        sql += "province,postalCode,country) VALUES (";
        sql += "'" + this.addressID + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(addressLine1) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(addressLine2) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(city) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(state) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(province) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(postalCode) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(country) + "') ";
        
        db.executeQuery(sql);
        
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
    }
    
    public Address(String addressID, String addressLine1, String addressLine2, String city, String state, 
            String province, String postalCode, String country){
        this.addressID = addressID;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
    }

    /**
     *
     * @return
     */
    public JSONObject getAddressAsJSON(){
        JSONObject address = new JSONObject();
      
        try {
            address.put("addressID",this.addressID);
            address.put("addressLine1",this.addressLine1);
            address.put("addressLine2",this.addressLine2);
            address.put("city",this.city);
            address.put("state",this.state);
            address.put("province",this.province);
            address.put("postalCode",this.postalCode);
            address.put("country",this.country);
            
            
         //   if()this
            
        } catch (JSONException ex) {
            Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return address;
    }
    
    
    /**
     * @return the addressID
     */
    public String getAddressID() {
        return addressID;
    }

    /**
     * @param addressID the addressID to set
     */
    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    /**
     * @return the addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * @param addressLine1 the addressLine1 to set
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * @return the addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * @param addressLine2 the addressLine2 to set
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
}
