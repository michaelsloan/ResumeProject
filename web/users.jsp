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
        <title></title>
        <meta http-equiv="Content-Type" content = "text/html; charset=UTF-8">
        <script language = "javascript" src ="scripts/jquery-2.1.0.min.js"></script>
        <script language = "javascript">
            $(document).ready(function(){
                getJsonFromWebService("rest/userlistws");
            });
            
            function getJsonFromWebService(targetUrl){
                jQuery.ajax({
                 type: "GET",
                 url: targetUrl,
                 contentType: "application/json; charset=UTF-8",
                 dataType: "json",
                 success: function(data,status,jqXhR){
                     convertJsonToHtmlTable(data);
                     convertJsonToComboBox(data);
                 },
                 error:function(status,jqXhR){
                     console.log(status);
                 }
             });   
            }
            
            function convertJsonToComboBox(jsonDataObj){
                $combo = $("#cboUserList");
                for(var i=0; i<jsonDataObj.length;i++){
                    $combo.append('<option value= "'+jsonDataObj[i].lastName+'">'+jsonDataObj[i].lastName+'</option>');  
                }
            }
            
            function convertJsonToHtmlTable(jsonDataObj){
                $table = $("#tblUserList");
                for(var i=0; i<jsonDataObj.length;i++){
                    $row = $("<tr />");
                    $row.append("<td>"+jsonDataObj[i].userID+"</td>");
                    $row.append("<td>"+jsonDataObj[i].lastName+"</td>");
                    $row.append("<td>"+jsonDataObj[i].firstName+"</td>");
                    $row.append("<td>"+jsonDataObj[i].phone1+"</td>");
                    $table.append($row);
                }
            }
        </script>    
    </head>
    <body>
        
        <select id ="cboUserList" name ="cboUserList" ></select>
        <hr />
        <table border= "1" id ="tblUserList" cellpadding= "3" cellspacing="0">
            <tr>
                <th>User ID</th>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Phone Number</th>
            </tr>
                
        </table>
    </body>
</html>