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
                getJsonFromWebService("rest/diagnosisws");
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
                     console.log(data);
                 },
                 error:function(status,jqXhR){
                     console.log(status);
                 }
             });   
            }
            
            function convertJsonToComboBox(jsonDataObj){
                $combo = $("#cboDiagnosisList");
                for(var i=0; i<jsonDataObj.length;i++){
                    $combo.append('<option value= "'+jsonDataObj[i].diagnosisName+'">'+jsonDataObj[i].diagnosisName+'</option>');  
                }
            }
            
            function convertJsonToHtmlTable(jsonDataObj){
                $table = $("#tblDiagnosisList");
                for(var i=0; i<jsonDataObj.length;i++){
                    $row = $("<tr />");
                    $row.append("<td>"+jsonDataObj[i].diagnosisID+"</td>");
                    $row.append("<td>"+jsonDataObj[i].diagnosisName+"</td>");;
                    $table.append($row);
                }
            }
        </script>    
    </head>
    <body>
        
        <select id ="cboDiagnosisList" name ="cbodiagnosisList" ></select>
        <hr />
        <table border= "1" id ="tblDiagnosisList" cellpadding= "3" cellspacing="0">
            <tr>
                <th>Diagnosis ID</th>
                <th>Diagnosis Name</th>
            </tr>
                
        </table>
    </body>
</html>
