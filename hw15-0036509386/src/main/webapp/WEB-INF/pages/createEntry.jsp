<%@ page import="hr.fer.zemris.java.hw15.forms.EntryForm" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>New Entry</title>
</head>
<body>
<h1>Create a new post</h1>
<% if (request.getSession().getAttribute("current.user.id") != null) { %>
<p>Hello, <%= request.getSession().getAttribute("current.user.fn") + " "
        + request.getSession().getAttribute("current.user.ln") %>!
    <a href="<%= request.getContextPath() %>/servleti/logout">logout</a> </p>
<% } else { %>
<p>Anonymous</p>
<% } %>

<% EntryForm form = (EntryForm) request.getAttribute("form"); %>
<% String id = form.getExistingID() == null ? "" : "?id=" + form.getExistingID(); %>

<form action="<%= request.getContextPath() %>
/servleti/author/<%= request.getSession().getAttribute("current.user.nick") %>/new<%= id %>" method="post">
    <label>
        Title:
        <input type="text" placeholder="Choose a title" name="title" value="<%= form.getTitle() %>"><br>
    </label>
    <% if (form.hasError("title")) { %>
    <%= form.getError("title") %><br>
    <% } %>
    <label>
        <textarea placeholder="Say something..." name="text"><%= form.getText() %></textarea><br>
    </label>
    <% if (form.hasError("text")) { %>
    <%= form.getError("text") %><br>
    <% } %>
    <button type="submit">Post</button>
</form>
<p><a href="<%= request.getContextPath() %>/servleti/main">Go home</a></p>
</body>
</html>
