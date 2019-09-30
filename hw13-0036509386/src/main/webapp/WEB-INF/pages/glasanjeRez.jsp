<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="hr.fer.zemris.java.servlets.voting.util.VotingResult" %>
<%@ page import="java.util.Set" %>
<%@ page import="hr.fer.zemris.java.servlets.voting.util.BandInfo" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Rezultati glasanja</title>
    <style type="text/css">
        table.rez td {text-align: center;}
         body {
         <core:if test="${sessionScope.pickedBgCol != null}">
             background-color: ${sessionScope.pickedBgCol}
         </core:if>
         }
    </style>
</head>
<body>
    <h1>Rezultati glasanja</h1>
    <p>Ovo su rezultati glasanja.</p>
    <table border="1" cellspacing="0" class="rez">
        <thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
        <tbody>
        <% @SuppressWarnings("unchecked") Set<VotingResult> votes = (Set<VotingResult>) request.getAttribute("votes"); %>
        <% for (VotingResult vote : votes) {%>
        <tr><td><%= vote.getBand().getName()%></td><td><%= vote.getNumberOfVotes()%></td></tr>
        <%}%>
    </table>
    <h2>Grafički prikaz rezultata</h2>
    <img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" /> <h2>Rezultati u XLS formatu</h2>
    <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
    <h2>Razno</h2>
    <p>Primjeri pjesama pobjedničkih bendova:</p>
    <ul>
        <% @SuppressWarnings("unchecked") Set<BandInfo> winners = (Set<BandInfo>) request.getAttribute("winners"); %>
        <% for (BandInfo winner : winners) {%>
        <li><a href="<%= winner.getExampleSong()%>" target="_blank"><%= winner.getName()%></a></li>
        <%}%>
    </ul>
</body>
</html>
