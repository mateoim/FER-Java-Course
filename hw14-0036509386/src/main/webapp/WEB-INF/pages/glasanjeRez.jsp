<%@ page import="hr.fer.zemris.java.hw14.voting.util.PollOptionInfo" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Rezultati glasanja</title>
    <style type="text/css">
        table.rez td {text-align: center;}
    </style>
</head>
<body>
    <h1>Rezultati glasanja</h1>
    <p>Ovo su rezultati glasanja.</p>
    <table border="1" cellspacing="0" class="rez">
        <thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
        <tbody>
        <% @SuppressWarnings("unchecked") Set<PollOptionInfo> votes = (Set<PollOptionInfo>) request.getAttribute("votes"); %>
        <% for (PollOptionInfo vote : votes) {%>
        <tr><td><%= vote.getTitle()%></td><td><%= vote.getNumberOfVotes()%></td></tr>
        <%}%>
    </table>
    <h2>Grafički prikaz rezultata</h2>
    <img alt="Pie-chart" src="<%= request.getContextPath() %>/servleti/glasanje-grafika" width="400" height="400" />
    <h2>Rezultati u XLS formatu</h2>
    <p>Rezultati u XLS formatu dostupni su <a href="<%= request.getContextPath() %>/servleti/glasanje-xls">ovdje</a></p>
    <h2>Razno</h2>
    <p>Primjeri pjesama pobjedničkih bendova:</p>
    <ul>
        <% @SuppressWarnings("unchecked") Set<PollOptionInfo> winners = (Set<PollOptionInfo>) request.getAttribute("winners"); %>
        <% for (PollOptionInfo winner : winners) {%>
        <li><a href="<%= winner.getLink()%>" target="_blank"><%= winner.getTitle()%></a></li>
        <%}%>
    </ul>
</body>
</html>
