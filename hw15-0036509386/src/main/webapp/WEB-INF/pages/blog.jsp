<%@ page import="hr.fer.zemris.java.hw15.model.BlogEntry" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Blog</title>
</head>
<body>
    <% if (request.getSession().getAttribute("current.user.id") != null) { %>
    <p>Hello, <%= request.getSession().getAttribute("current.user.fn") + " "
            + request.getSession().getAttribute("current.user.ln") %>!
        <a href="<%= request.getContextPath() %>/servleti/logout">logout</a></p>
    <% } else { %>
    <p>Anonymous</p>
    <% } %>

    <% String author = (String) request.getAttribute("author"); %>
    <% if (author.equals(request.getSession().getAttribute("current.user.nick"))) { %>
    <p><a href="<%= request.getContextPath() %>/servleti/author/<%= author %>/new">new entry</a></p>
    <% } %>

    <p>Here's a list of <%= author %>'s posts:<br></p>
    <% @SuppressWarnings("unchecked") List<BlogEntry> posts = (List<BlogEntry>) request.getAttribute("posts"); %>
    <% if (posts == null || posts.isEmpty()) { %>
    No entries
    <% } else {
        for (BlogEntry post : posts) { %>
            <a href="<%= request.getContextPath() %>/servleti/author/<%= author %>/<%= post.getId() %>"><%= post.getTitle() %></a><br>
        <% }
    } %>

    <p><a href="<%= request.getContextPath() %>/servleti/main">Go home</a></p>
</body>
</html>
