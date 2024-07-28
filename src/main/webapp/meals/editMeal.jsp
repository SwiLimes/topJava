<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<form action="meals" method="post">
    <c:set var="isEditForm" value="${param.action == 'edit'}"/>
    <c:choose>
        <c:when test="${isEditForm}">
            <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
            <h1 style="text-align: center">Edit meal <span
                    style="font-style: italic; color: darkseagreen">${meal.description}</span></h1>
            <input hidden type="text" name="id" value="${meal.id}">
        </c:when>
        <c:otherwise>
            <h1 style="text-align: center">Create meal</h1>
        </c:otherwise>
    </c:choose>
    <input style="width: 100%;" required type="datetime-local" name="dateTime"
           value="${isEditForm ? meal.dateTime : LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)}"/><br><br>
    <input style="width: 100%;" required type="text" name="description" value="${meal.description}"
           placeholder="Description"><br><br>
    <input style="width: 100%;" required type="number" name="calories" value="${meal.calories}"
           placeholder="Calories"><br><br>
    <input type="submit" value="Save meal">
    <button onclick="window.history.back()" type="button">Back</button>
</form>
</body>
</html>
