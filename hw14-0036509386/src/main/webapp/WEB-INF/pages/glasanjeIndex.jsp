<%@ page import="java.util.List" %>
<%@ page import="hr.fer.zemris.java.hw14.voting.util.PollOptionInfo" %>
<%@ page import="hr.fer.zemris.java.hw14.voting.util.PollInfo" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Glasanje</title>
</head>
<body>
<% PollInfo selected = (PollInfo) request.getAttribute("poll"); %>
<h1><%= selected.getTitle() %></h1>
<p><%= selected.getMessage() %></p>
<ol>
    <%
        @SuppressWarnings("unchecked") List<PollOptionInfo> options = (List<PollOptionInfo>) request.getAttribute("options");
        for (PollOptionInfo option : options) {%>
            <li><a href="<%= request.getContextPath() %>/servleti/glasanje-glasaj?id=<%= option.getId() %>">
                <%= option.getTitle() %></a></li>
        <%}%>
</ol>
</body>
</html>
