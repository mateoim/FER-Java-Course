<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Random" %>
<%@ page contentType="text/html;charset=UTF-8" session="true" %>

<html>
<head>
    <title>Funny</title>
    <style>
        body {
            <%Random rand = new Random(System.currentTimeMillis());%>
            color: rgb(<%= rand.nextInt(256)%>, <%= rand.nextInt(256)%>, <%= rand.nextInt(256)%>);

            <core:if test="${sessionScope.pickedBgCol != null}">
                background-color: ${sessionScope.pickedBgCol}
            </core:if>
        }
    </style>
</head>
<body>
    <p>"Upiši javu, isplatit će se!"</p>
    <p><a href="<%= request.getContextPath()%>/index.jsp">Go back</a></p>
</body>
</html>
