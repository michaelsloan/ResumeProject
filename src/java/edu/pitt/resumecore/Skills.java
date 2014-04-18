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
public class Skills {

   private String skillID;
   private String description;



   public Skills (String skillID){

   }
  // I think we should get rid of this one and just make the first one
   //SkillorID
    public Skills (String skillID, String description)
   {

   }

   /**
    * @return the skillID
    */
   public String getSkillID() {
       return skillID;
   }

   /**
    * @return the description
    */
   public String getDescription() {
       return description;
   }




}