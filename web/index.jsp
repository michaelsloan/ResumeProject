<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Login Screen</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
    </head>
    <body>
        <form name="frmLogin" id="frmLogin" method="post" action="login">
           <p>   
            Login name: <input type="text" id ="txtLoginName" name ="txtLoginName" value=""/>
            </p>
            
            <p>
            Password name: <input type = "password" id ="txtPassword" name = "txtPassword" value=""/>
            </p>    
            
            <p>
             <input type ="submit" id="btnLogin" name ="btnLogin" value ="Login"/>
            </p>    
        </form>      
    </body>
</html>
