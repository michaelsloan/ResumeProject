/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pitt.resumeservlets;

import edu.pitt.resumecore.Address;
import edu.pitt.resumecore.Diagnosis;
import edu.pitt.resumecore.Medication;
import edu.pitt.resumecore.PatientVisit;
import edu.pitt.resumecore.Prescription;
import edu.pitt.resumecore.Symptom;
import edu.pitt.resumecore.User;
import edu.pitt.utilities.Security;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author michaelsloan
 */
@WebServlet(name = "recordpatientvisit", urlPatterns = {"/recordpatientvisit"})
public class recordpatientvisit extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            HttpSession session = request.getSession(true);
            
            if(Security.checkHijackedSession(session, request)){
                response.sendRedirect("index.jsp");
            }  
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet recordpatientvisit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet recordpatientvisit at " + request.getContextPath() + "</h1>");
            
            User user = new User ("40c3365a-40a2-11e3-91ff-b8ca3a903bf4");
            PatientVisit patientvisit = new PatientVisit(user);

            out.print(patientvisit.getPatientVisitAsJSON());
            
            out.println("</body>");
            out.println("</html>");
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
