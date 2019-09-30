<%@ page import="hr.fer.zemris.java.hw15.model.BlogUser" %>
<%@ page import="java.util.List" %>
<%@ page import="hr.fer.zemris.java.hw15.forms.LoginForm" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
    <% if (request.getSession().getAttribute("current.user.id") != null) { %>
    <p>Hello, <%= request.getSession().getAttribute("current.user.fn") + " "
            + request.getSession().getAttribute("current.user.ln") %>!
        <a href="<%= request.getContextPath() %>/servleti/logout">logout</a> </p>
    <% } else { %>
    <p>Anonymous</p>
    <% } %>

    <h3>Enter your login information</h3>
    <form action="<%= request.getContextPath() %>/servleti/main" method="post">
        <% LoginForm form = (LoginForm) request.getAttribute("form"); %>
        <label>
            Nickname:
            <input type="text" placeholder="Enter your nickname" name="nick" value="<%= form.getUsername() %>"><br>
        </label>
        <label>
            Password:
            <input type="password" placeholder="Enter new password" name="password"><br>
        </label>
        <% if (form.hasErrors()) { %>
            Invalid username or password.<br>
        <% } %>
        <button type="submit">Login</button>
    </form>

    <p><a href="<%= request.getContextPath() %>/servleti/register">Create an account.</a></p>
    <p>
        <% @SuppressWarnings("unchecked") List<BlogUser> users = (List<BlogUser>) request.getAttribute("users"); %>
        <% if (users == null || users.isEmpty()) { %>
        No registered users
        <% } else { %>
        Currently registered authors:<br>
            <% for (BlogUser user : users) { %>
        <a href="<%= request.getContextPath() %>/servleti/author/<%= user.getNick() %>"><%= user.getNick() %></a><br>
           <% }
        } %>
    </p>
</body>
</html>
