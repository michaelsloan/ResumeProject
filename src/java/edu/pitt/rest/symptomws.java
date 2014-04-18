/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pitt.rest;

import edu.pitt.resumecore.Symptom;
import edu.pitt.resumecore.User;
import edu.pitt.utilities.MySqlDbUtilities;
import edu.pitt.utilities.Security;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author michaelsloan
 */
@WebServlet(name = "symptomws", urlPatterns = {"/rest/symptomws"})
public class symptomws extends HttpServlet {

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
            
            HttpSession session = request.getSession(true);
            
            if(Security.checkHijackedSession(session, request)){
                response.sendRedirect("index.jsp");
            }  
            
            String symptom = "";
            if(request.getParameter("symptom")!=null){
               symptom = request.getParameter("symptom").toString();
            }
            MySqlDbUtilities db = new MySqlDbUtilities();
            String sql = "SELECT * FROM symptoms ";
            if(!symptom.equals("")){
                sql+= "WHERE symptomName LIKE '" + symptom +"%"+ "' ";
            }
            sql+= "LIMIT 20;";
            JSONArray fullSymptomList = new JSONArray();
            ResultSet rs = db.getResultSet(sql);            
 
            while(rs.next()){
                Symptom symp = new Symptom(rs.getString("symptomId"));
                fullSymptomList.put(symp.getSymptomAsJSON());
                symp.getDb().closeDbConnection();
            }
            
            StringWriter sw = new StringWriter();
            fullSymptomList.write(sw);
            out.print(sw.toString());
        }
        catch (SQLException ex) {
            Logger.getLogger(patientvisitws.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (JSONException ex) {
            Logger.getLogger(patientvisitws.class.getName()).log(Level.SEVERE, null, ex);
        }     

        finally{
            out.close();
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
