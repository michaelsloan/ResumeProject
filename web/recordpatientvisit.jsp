<%@page import="edu.pitt.utilities.Security"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%
  if(Security.checkHijackedSession(session, request)){
      response.sendRedirect("index.jsp");
  }  
%>
<html>
<head>
        <title>Record Patient Visit</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
    </head>
    <body>
        <form name="frmRecordVisit" id="frmRecordVisit" method="post" action="recordpatientvisit">
            <p>   
            Complaints: <br> <textarea name="txtTextAreaComplaints" id="txtTextAreaComplaints"
                                  rows="5" cols="50"></textarea>
            </p>
            
            <br>
               
            <p>
            Notes: <br> <textarea name="txtTextAreaNotes" id="txtTextAreaNotes" rows="5" cols="50"></textarea>

            </p>    
            
            <p>
             <input type ="submit" id="btnRecordVisit" name ="btnRecordVisit" value ="Submit"/>
            </p>    
        </form>      
    </body>
</html>
