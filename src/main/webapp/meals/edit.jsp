<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<jsp:useBean id="meal" scope="request" class="ru.javawebinar.topjava.model.Meal"/>
<c:set var="form" value='<%=Objects.equals(request.getParameter("action"), "edit") ? "edit" : "create"%>'/>
<c:choose>
    <c:when test="${form == 'edit'}">
        <h1 style="text-align: center">Edit meal <span
                style="font-style: italic; color: darkseagreen">${meal.description}</span></h1>
    </c:when>
    <c:otherwise>
        <h1 style="text-align: center">Create meal</h1>
    </c:otherwise>
</c:choose>
<form action="meals" method="post">
    <input hidden type="text" name="id" value="${meal.id}">
    <label>
        <b>Date time</b>
        <input style="width: 100%;" required type="datetime-local" name="dateTime" value="${meal.dateTime}">
    </label><br><br>
    <label>
        <b>Description</b>
        <input style="width: 100%;" required type="text" name="description" value="${meal.description}"
               placeholder="Description">
    </label><br><br>
    <label>
        <b>Calories</b>
        <input style="width: 100%;" required type="number" name="calories" value="${meal.calories}"
               placeholder="Calories">
    </label><br><br>
    <input type="submit" value="Save meal">
    <button onclick="window.history.back()" type="button">Back</button>
</form>
</body>
</html>
