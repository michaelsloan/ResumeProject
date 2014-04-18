/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pitt.rest;

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
@WebServlet(name = "userlistws", urlPatterns = {"/rest/userlistws"})
public class userlistws extends HttpServlet {

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
            
            String lastname = "";
            if(request.getParameter("lastname")!=null){
               lastname = request.getParameter("lastname").toString();
            }
            
            MySqlDbUtilities db = new MySqlDbUtilities();
            String sql = "SELECT * FROM userInfo ";
            if(!lastname.equals("")){
                sql+= "WHERE lastName LIKE '" + lastname +"%"+ "' ";
            }
            sql+= "LIMIT 20;";
            JSONArray fullUserList = new JSONArray();
            ResultSet rs = db.getResultSet(sql);            
       
            /*
            Had way too many connections, flooded the mysql server. Needed to
            terminate connections so, manually get the db and close the connection
            changed closedbconnection() to public.
            
            Repeated steps in diagnosisws and symptomws.
            
            Mysql: show processlist (shows all connections)
            */
            while(rs.next()){
                User user = new User(rs.getString("userId"));
                fullUserList.put(user.getUserInfoAsJSON());
                user.getDb().closeDbConnection();
            }
            
            StringWriter sw = new StringWriter();
            fullUserList.write(sw);
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
