<%@ page import="java.util.List" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<html>
<head>
    <title>Trigonometric</title>
    <style>
        body {
        <core:if test="${sessionScope.pickedBgCol != null}">
            background-color: ${sessionScope.pickedBgCol}
        </core:if>
        }
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
    <h1>Sine and cosine values for angles between ${requestScope.a} and ${requestScope.b}</h1>
    <table>
        <tr><td>angle</td><td>sine</td><td>cosine</td></tr>
        <%
            @SuppressWarnings("unchecked") List<Double> sin = (List<Double>) request.getAttribute("sin");
            @SuppressWarnings("unchecked") List<Double> cos = (List<Double>) request.getAttribute("cos");
        %>
        <% for (int i = 0, a = (int) request.getAttribute("a"); i < sin.size(); i++) {%>
        <tr><td><%= i + a %></td><td><%= sin.get(i)%></td>
            <td><%= cos.get(i)%></td></tr>
        <%}%>
    </table>
    <p><a href="<%= request.getContextPath()%>/index.jsp">Go back</a></p>
</body>
</html>
