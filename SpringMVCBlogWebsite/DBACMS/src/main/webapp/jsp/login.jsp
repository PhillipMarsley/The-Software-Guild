<%-- 
    Document   : login
    Created on : Mar 29, 2018, 3:08:18 PM
    Author     : Alex
--%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>DBAG Login Page</title>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/theme.css">
    </head>
    <body>
        <div class="container">
            <c:if test="${param.login_error == 1}">
                <h3>Wrong id or password!</h3>
            </c:if>
            <div class="row">
                <div class="col-md-6">
                    <form class="form-horizontal pull-left" 
                          role="form" 
                          method="post" 
                          action="j_spring_security_check">
                        <div class="form-group">
                            <label for="j_username" class="col-md-4 control-label">Username:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="j_username" placeholder="Username"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="j_password" class="col-md-4 control-label">Password:</label>
                            <div class="col-md-8">
                                <input type="password" class="form-control" name="j_password" placeholder="Password"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" id="search-button" value="Sign In"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
