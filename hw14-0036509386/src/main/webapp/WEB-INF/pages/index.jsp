<%@ page import="hr.fer.zemris.java.hw14.voting.util.PollInfo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Polls</title>
</head>
<body>
    <h1>Please select a poll in which you would like to vote</h1>
    <% @SuppressWarnings("unchecked") List<PollInfo> polls = (List<PollInfo>) request.getAttribute("polls"); %>
    <% for (PollInfo poll : polls) { %>
    <p><a href="<%= request.getContextPath() %>/servleti/glasanje?pollID=<%= poll.getId() %>"><%= poll.getTitle() %></a></p>
    <%}%>
</body>
</html>
