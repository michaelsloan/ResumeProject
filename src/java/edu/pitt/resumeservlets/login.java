/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pitt.resumeservlets;

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
@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {

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
            String loginName = "";
            String password = "";
            User user;
            
            HttpSession session = request.getSession(true);
            
            if(Security.checkHijackedSession(session, request)){
                response.sendRedirect("index.jsp");
            }  
            
            if(!request.getParameter("txtLoginName").equals("") && !request.getParameter("txtPassword").equals("")){
                
                loginName = request.getParameter("txtLoginName");
                password = request.getParameter("txtPassword");
                
                out.println("LOGIN NAME: "+ loginName + "<br />");
                out.println("PASSWORD: "+ password +"<br />");
                
                user = new User(loginName,password);
                out.println(user.getLoginName());
                
                if(user.getLoginName().equals("null")){
                  out.println("Invalid credentials");
                }
                
                
                if(isValidUser(user)){
                    session.setAttribute("authenticatedUser", user);
                    session.setAttribute("authenticatedUserIP",request.getRemoteAddr());
                    session.setAttribute("authenticatedUserBrowser", request.getHeader("User-Agent"));
               
                    response.sendRedirect("menu.jsp");
                }
                else{
                    response.sendRedirect("index.jsp");
                }
                
            }
            else{
                out.println("You must provide both a username and password in order to login");
            }
            
            
        }
    }
    
    public static boolean isValidUser(User user){
        if(!user.getUserID().equals("")){
            return true;
        }
        else {
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
