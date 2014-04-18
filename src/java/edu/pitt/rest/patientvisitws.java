/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pitt.rest;
 
import edu.pitt.resumecore.Diagnosis;
import edu.pitt.resumecore.Medication;
import edu.pitt.resumecore.PatientVisit;
import edu.pitt.resumecore.Prescription;
import edu.pitt.resumecore.Symptom;
import edu.pitt.resumecore.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.pitt.utilities.MySqlDbUtilities;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author michaelsloan
 */
@WebServlet(name = "patientvisitws", urlPatterns = {"/rest/patientvisitws"})
public class patientvisitws extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        try{
            String examId = "";
            String key1 = request.getParameter("devKey").toString();
            String key2 = request.getParameter("appKey").toString();       
            String auth = key1+":"+key2;
            
            if(isValidUser(auth)){
                out.println("Login successful");
                
                if(request.getParameter("examId")!=null){
                    examId = request.getParameter("examId").toString();
                }
                MySqlDbUtilities db = new MySqlDbUtilities();
                String sql = "SELECT * FROM patientvisitrecord ";
                if(!examId.equals("")){
                    sql+= "WHERE examId = '" + examId + "' ";
                }
                sql+= "LIMIT 20;";
                JSONArray fullExamList = new JSONArray();
                ResultSet rs = db.getResultSet(sql);            
            
                while(rs.next()){
                    PatientVisit patient = new PatientVisit(rs.getString("examId"));
                    fullExamList.put(patient.getPatientVisitAsJSON());
                }
            
                StringWriter sw = new StringWriter();
                fullExamList.write(sw);
                out.print(sw.toString());
            
          }
          else{
               out.println("Unable to authenticate user"); 
          }
        }
        catch (SQLException ex) {
            Logger.getLogger(patientvisitws.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(patientvisitws.class.getName()).log(Level.SEVERE, null, ex);
        }     

        finally{
            out.close();
        }
    }
    
    protected boolean isValidUser(String auth){
        String devKey = "93356a66-aafe-11e3-9f98-3c5703081e7f";
        String appKey = "a5bd2fa2-aafe-11e3-9f98-3c5703081e7f";
        
        if (auth == null) {  
            return false;  // no auth  
        }  
        
        String userCredentials = auth;
        if(userCredentials.equals(devKey + ":" + appKey)){
            return true;
        }
        else{
            return false;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
