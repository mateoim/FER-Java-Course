<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" session="true" %>

<html>
<head>
    <title>Error</title>
    <style>
        body {
        <core:if test="${sessionScope.pickedBgCol != null}">
            background-color: ${sessionScope.pickedBgCol}
        </core:if>
        }
    </style>
</head>
<body>
    <h1>Invalid parameters!</h1>
<p><a href="<%= request.getContextPath()%>/index.jsp">Go back</a></p>
</body>
</html>
