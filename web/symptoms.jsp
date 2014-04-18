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
      <title></title>
        <meta http-equiv="Content-Type" content = "text/html; charset=UTF-8">
        <script language = "javascript" src ="scripts/jquery-2.1.0.min.js"></script>
        <script language = "javascript">
            $(document).ready(function(){
                getJsonFromWebService("rest/symptomws");
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
                $combo = $("#cboSymptomList");
                for(var i=0; i<jsonDataObj.length;i++){
                    $combo.append('<option value= "'+jsonDataObj[i].symptomName+'">'+jsonDataObj[i].symptomName+'</option>');  
                }
            }
            
            function convertJsonToHtmlTable(jsonDataObj){
                $table = $("#tblSymptomList");
                for(var i=0; i<jsonDataObj.length;i++){
                    $row = $("<tr />");
                    $row.append("<td>"+jsonDataObj[i].symptomID+"</td>");
                    $row.append("<td>"+jsonDataObj[i].symptomName+"</td>");
                    $table.append($row);
                }
            }
        </script>    
    </head>
    <body>
        
        <select id ="cboSymptomList" name ="cboSymptomList" ></select>
        <hr />
        <table border= "1" id ="tblSymptomList" cellpadding= "3" cellspacing="0">
            <tr>
                <th>Symptom ID</th>
                <th>Symptom Name</th>
            </tr>
                
        </table>
    </body>
</html>
