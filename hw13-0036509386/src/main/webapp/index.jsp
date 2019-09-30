<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<html>
<head>
    <title>Home</title>
    <style>
        body {
        <core:if test="${sessionScope.pickedBgCol != null}">
            background-color: ${sessionScope.pickedBgCol}
        </core:if>
        }
    </style>
</head>
<body>
    <a href="colors.jsp">Background color chooser</a>
    <p><a href="<%= request.getContextPath()%>/trigonometric?a=0&b=90">
        Show angles between 0 and 90 degrees</a></p>
    <form action="trigonometric" method="GET">
        Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
        Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
        <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
    </form>
    <p><a href="stories/funny.jsp">See something funny</a></p>
    <p><a href="report.jsp">Get os usage report</a></p>
    <form action="powers" method="GET">
        a:<br><input type="number" name="a" min="-100" max="100" step="1" value="0"><br>
        b:<br><input type="number" name="b" min="-100" max="100" step="1" value="10"><br>
        n:<br><input type="number" name="n" min="1" max="5" step="1" value="1"><br>
        <input type="submit" value="Calculate"><input type="reset" value="Reset">
    </form>
    <p><a href="<%= request.getContextPath()%>/powers?a=1&b=100&n=3">
        Download Excel table of numbers 1-100 to the power of 1-3</a></p>
    <p><a href="<%= request.getContextPath()%>/appinfo.jsp">Time since server boot</a></p>
    <p><a href="<%= request.getContextPath()%>/glasanje">Voting</a></p>
</body>
</html>
