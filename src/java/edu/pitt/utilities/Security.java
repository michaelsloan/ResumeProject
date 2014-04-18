/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pitt.utilities;

import edu.pitt.resumecore.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author michaelsloan
 */
public class Security {
    
    public static boolean isValidSession(HttpSession session) {
        if (session != null) {
            if (session.getAttribute("userID") != null) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean checkHijackedSession(HttpSession session, HttpServletRequest request){
        
        if(isValidSession(session)){
            if(!session.getAttribute("authenticatedUserIP").toString().equalsIgnoreCase(request.getRemoteAddr()) 
               && request.getHeader("User-Agent").indexOf("Mobile")!= -1)
            {      
               return true;
            }

        }
        return false;
    }
    
}
