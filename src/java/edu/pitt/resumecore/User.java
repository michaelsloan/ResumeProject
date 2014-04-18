/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.resumecore;

import edu.pitt.utilities.MySqlDbUtilities;
import edu.pitt.utilities.ErrorLogger;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


public class User {
    private MySqlDbUtilities db;
    private String userID;
    private String lastName;
    private String firstName;
    private Date dateOfBirth;
    private String phone1;
    private String phone2;
    private String email;
    private String loginName;
    private String password;

    public User(String userID){
            db = new MySqlDbUtilities();
            String sql = "SELECT * FROM resume.userinfo JOIN resume.userinfo_address ON userId = fk_userId ";
            sql += "JOIN resume.address ON fk_addressId = addressId ";
            sql += "WHERE userID = '" + userID + "'";
            setAllUserProperties(sql);
            db.closeDbConnection();
    }
    
    public User(String loginName, String password){
            db = new MySqlDbUtilities();
            String sql = "SELECT * FROM resume.userinfo JOIN resume.userinfo_address ON userID = fk_userId ";
            sql += "JOIN resume.address ON fk_addressId = addressId ";
            sql += "WHERE loginName = '" + loginName + "' AND password = '" + password + "'";
            setAllUserProperties(sql);
            db.closeDbConnection();
    }
    
    private void setAllUserProperties(String sql){
        try {
                this.userID = "";
                this.lastName = "";
                this.firstName = "";
                this.phone1 = "";
                this.phone2 = "";
                this.email = "";
                this.loginName = "";
                this.password = "";            
            
            ResultSet rs = this.getDb().getResultSet(sql);
            while(rs.next()){
                this.userID = rs.getString("userID");
                this.lastName = rs.getString("lastName");
                this.firstName = rs.getString("firstName");
                this.dateOfBirth = rs.getDate("dateOfBirth");
                this.phone1 = rs.getString("phone1");
                this.phone2 = rs.getString("phone2");
                this.email = rs.getString("email");
                this.loginName = rs.getString("loginName");
                this.password = rs.getString("password");
                Address address = new Address(rs.getString("addressId"), rs.getString("addressLine1"), 
                        rs.getString("addressLine2"), rs.getString("city"), 
                        rs.getString("state"), rs.getString("province"), 
                        rs.getString("postalCode"), rs.getString("country"));
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error had occured in setAllUserProperties() method of User class. " + ex.getMessage());
            ErrorLogger.log(sql);
        }
    }

    /**
     * @return the db
     */
    public MySqlDbUtilities getDb() {
        return db;
    }
    
    public JSONObject getUserInfoAsJSON(){
            JSONObject  user = new JSONObject();
            JSONArray userAddressList = new JSONArray();
        try{    
            user.put("userID", this.userID);
            user.put("lastName", this.lastName);
            user.put("firstName", this.firstName);
            if(this.dateOfBirth!=null){    
                user.put("dateOfBirth", this.dateOfBirth);
            }
            else{
                user.put("dateOfBirth","");
            }
            user.put("phone1", this.phone1);
            user.put("phone2", this.phone2);   
            user.put("email", this.email);
            user.put("loginName", this.loginName);
            user.put("password", this.password);
            
        } catch (JSONException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the dateOfBirth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @return the phone1
     */
    public String getPhone1() {
        return phone1;
    }

    /**
     * @return the phone2
     */
    public String getPhone2() {
        return phone2;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the addressList
     */
    
    public String toString(){
        return getUserID();
    }
}
