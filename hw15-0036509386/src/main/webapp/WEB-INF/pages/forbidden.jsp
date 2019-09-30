<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Forbidden</title>
</head>
<body>
    <h1>I wouldn't do that if I were you...</h1>
    <p>You are not <%= request.getAttribute("author") %>, back off.</p>
    <p><a href="<%= request.getContextPath() %>/servleti/main">Go home</a></p>
</body>
</html>
