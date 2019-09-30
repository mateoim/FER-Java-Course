<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" session="true" %>

<html>
<head>
    <title>Time since creation</title>
    <style>
        body {
        <core:if test="${sessionScope.pickedBgCol != null}">
            background-color: ${sessionScope.pickedBgCol}
        </core:if>
        }
    </style>
</head>
<body>
    <p>Time since server boot:
        <%
            ServletContext sc = request.getServletContext();
            long bootTime = (long) sc.getAttribute("creationTime");
            long delta = System.currentTimeMillis() - bootTime;
            out.print(delta / 86400000 + " days ");
            delta %= 86400000;
            out.print(delta / 3600000 + " hours ");
            delta %= 3600000;
            out.print(delta / 60000 + " minutes ");
            delta %= 60000;
            out.print(delta / 1000 + " seconds " + delta % 1000 + " milliseconds");
        %></p>
    <p><a href="index.jsp">Go back</a></p>
</body>
</html>
