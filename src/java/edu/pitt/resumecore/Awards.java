/*
* To change this license header, choose License Headers in Project
Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package edu.pitt.resumecore;

/**
*
* @author spackle
*/
public class Awards {

   private String awardID;
   private String awardName;
   private String description;



   public Awards(String awardID){

   }


   public Awards(String awardName, String description){

   }

   /**
    * @return the awardID
    */
   public String getAwardID() {
       return awardID;
   }

   /**
    * @return the awardName
    */
   public String getAwardName() {
       return awardName;
   }

   /**
    * @return the description
    */
   public String getDescription() {    // not sure if I should have this.
       return description;
   }

   /**
    * @param awardName the awardName to set
    */
   public void setAwardName(String awardName) {
       this.awardName = awardName;
   }



}