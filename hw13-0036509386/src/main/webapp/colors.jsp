<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<html>
<head>
    <title>Colors</title>
    <style>
        body {
        <core:if test="${sessionScope.pickedBgCol != null}">
            background-color: ${sessionScope.pickedBgCol}
        </core:if>
        }
    </style>
</head>
<body>
    <p><a href="<%= request.getContextPath()%>/setcolor?selected=white">WHITE</a></p>
    <p><a href="<%= request.getContextPath()%>/setcolor?selected=red">RED</a></p>
    <p><a href="<%= request.getContextPath()%>/setcolor?selected=green">GREEN</a></p>
    <p><a href="<%= request.getContextPath()%>/setcolor?selected=cyan">CYAN</a></p>
    <p><a href="<%= request.getContextPath()%>/index.jsp">Go home</a></p>
</body>
</html>
