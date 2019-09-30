<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" session="true" %>

<html>
<head>
    <title>OS report</title>
    <style>
        body {
        <core:if test="${sessionScope.pickedBgCol != null}">
            background-color: ${sessionScope.pickedBgCol}
        </core:if>
        }
    </style>
</head>
<body>
    <h1>OS usage</h1>
    <p>Here are the results of OS usage in survey that we completed.</p>
    <img src="reportImage">
    <p><a href="index.jsp">Go back</a></p>
</body>
</html>
