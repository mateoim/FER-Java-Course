<%@ page import="hr.fer.zemris.java.hw15.forms.UserForm" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Registration</title>
</head>
<body>
    <% if (request.getSession().getAttribute("current.user.id") != null) { %>
    <p>Hello, <%= request.getSession().getAttribute("current.user.fn") + " "
            + request.getSession().getAttribute("current.user.ln") %>!
        <a href="<%= request.getContextPath() %>/servleti/logout">logout</a> </p>
    <% } else { %>
    <p>Anonymous</p>
    <% } %>

    <h2>Please fill out the form to register</h2>
    <form action="<%= request.getContextPath() %>/servleti/register" method="post">
        <% UserForm form = (UserForm) request.getAttribute("form"); %>
        <label>
            First name:
            <input type="text" placeholder="Enter your first name" name="fName" value="<%= form.getFirstName() %>"><br>
        </label>
        <% if (form.hasError("fName")) { %>
            <%= form.getError("fName") %><br>
        <% } %>
        <label>
            Last name:
            <input type="text" placeholder="Enter your last name" name="lName" value="<%= form.getLastName() %>"><br>
        </label>
        <% if (form.hasError("lName")) { %>
            <%= form.getError("lName") %><br>
        <% } %>
        <label>
            Nickname:
            <input type="text" placeholder="Enter your username" name="nick" value="<%= form.getNick() %>"><br>
        </label>
        <% if (form.hasError("nick")) { %>
            <%= form.getError("nick") %><br>
        <% } %>
        <label>
            E-mail:
            <input type="text" placeholder="Enter your e-mail" name="email" value="<%= form.getEmail() %>"><br>
        </label>
        <% if (form.hasError("email")) { %>
            <%= form.getError("email") %><br>
        <% } %>
        <label>
            Password:
            <input type="password" placeholder="Enter new password" name="password"><br>
        </label>
        <% if (form.hasError("password")) { %>
            <%= form.getError("password") %><br>
        <% } %>
        <button type="submit">Register</button>
    </form>
    <p><a href="<%= request.getContextPath() %>/servleti/main">Go home</a></p>
</body>
</html>
