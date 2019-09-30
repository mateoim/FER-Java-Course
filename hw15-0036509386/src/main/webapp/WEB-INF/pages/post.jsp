<%@ page import="hr.fer.zemris.java.hw15.model.BlogComment" %>
<%@ page import="hr.fer.zemris.java.hw15.model.BlogEntry" %>
<%@ page import="java.util.List" %>
<%@ page import="hr.fer.zemris.java.hw15.forms.CommentForm" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Post</title>
</head>
<body>
<% if (request.getSession().getAttribute("current.user.id") != null) { %>
<p>Hello, <%= request.getSession().getAttribute("current.user.fn") + " "
        + request.getSession().getAttribute("current.user.ln") %>!
    <a href="<%= request.getContextPath() %>/servleti/logout">logout</a> </p>
<% } else { %>
<p>Anonymous</p>
<% } %>

<% BlogEntry post = (BlogEntry) request.getAttribute("post"); %>
<h1><%= post.getTitle() %></h1>
<p>
    <%= post.getCreator().getNick() %> on <%= post.getCreatedAt() %>:<br>
    <%= post.getText() %><br>

    <% if (post.getLastModifiedAt() != null) { %>
        Last edited: <%= post.getLastModifiedAt() %>
    <% } %>
    <% String author = (String) request.getAttribute("author"); %>
    <% if (author.equals(request.getSession().getAttribute("current.user.nick"))) { %>
        <a href="<%= request.getContextPath() %>/servleti/author/<%= author %>/edit?id=<%= post.getId() %>">edit</a><br>
    <% } %>
</p>

<h3>Add a comment</h3>
<form action="<%= request.getContextPath() %>
/servleti/author/<%= author %>/<%= post.getId() %>" method="post">
    <% CommentForm form = (CommentForm) request.getAttribute("form"); %>
    <% if (request.getSession().getAttribute("current.user.id") == null) { %>
    <label>
        E-mail:
        <input type="text" placeholder="Enter your e-mail to comment" name="email" value="<%= form.getEmail() %>" size="30"><br>
    </label>
    <% if (form.hasError("email")) { %>
    <%= form.getError("email") %><br>
    <% }
    } %>
    <label>
        <textarea placeholder="Say something..." name="text"><%= form.getComment() %></textarea><br>
    </label>
    <% if (form.hasError("text")) { %>
    <%= form.getError("text") %><br>
    <% } %>
    <button type="submit">Comment</button>
</form>
<h3>
    Comments:
</h3>
<p>
    <% List<BlogComment> comments = post.getComments(); %>
    <% if (comments == null || comments.isEmpty()) { %>
        No comments.<br>
    <% } else {
     for (BlogComment comment : comments) { %>
            <%= comment.getUsersEMail() %> on <%= comment.getPostedOn() %>:<br>
            <%= comment.getMessage() %><br><br>
        <% }
        } %>
</p>
<p><a href="<%= request.getContextPath() %>/servleti/main">Go home</a></p>
</body>
</html>
