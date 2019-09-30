<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="hr.fer.zemris.java.servlets.voting.util.BandInfo" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Glasanje</title>
    <style>
        body {
        <core:if test="${sessionScope.pickedBgCol != null}">
            background-color: ${sessionScope.pickedBgCol}
        </core:if>
        }
    </style>
</head>
<body>
<h1>Glasanje za omiljeni bend:</h1>
<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
    glasali!</p>
<ol>
    <%
        @SuppressWarnings("unchecked") Set<BandInfo> bands = (Set<BandInfo>) request.getSession().getAttribute("bands");
        for (BandInfo band : bands) {%>
            <li><a href="glasanje-glasaj?id=<%= band.getId()%>"><%= band.getName()%></a></li>
        <%}%>
</ol>
</body>
</html>
