<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"    uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${empty author.id ? 'Add Author' : 'Edit Author'} – Library</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav>
    <a class="brand" href="${pageContext.request.contextPath}/">📚 Library Manager</a>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/books">Books</a>
        <a href="${pageContext.request.contextPath}/authors">Authors</a>
    </div>
</nav>

<div class="container">
    <div class="form-card">
        <h2>${empty author.id ? '➕ Add New Author' : '✏️ Edit Author'}</h2>

        <c:choose>
            <c:when test="${empty author.id}">
                <c:set var="actionUrl" value="${pageContext.request.contextPath}/authors/save"/>
            </c:when>
            <c:otherwise>
                <c:set var="actionUrl" value="${pageContext.request.contextPath}/authors/update/${author.id}"/>
            </c:otherwise>
        </c:choose>

        <form:form action="${actionUrl}" method="post" modelAttribute="author">

            <div class="form-group">
                <label for="name">Full Name *</label>
                <form:input path="name" id="name" placeholder="Enter author's full name"/>
                <form:errors path="name" cssClass="error-msg"/>
            </div>

            <div class="form-group">
                <label for="nationality">Nationality *</label>
                <form:input path="nationality" id="nationality" placeholder="e.g., British, American"/>
                <form:errors path="nationality" cssClass="error-msg"/>
            </div>

            <div class="form-group">
                <label for="birthYear">Birth Year</label>
                <form:input path="birthYear" id="birthYear" type="number" placeholder="e.g., 1960"/>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    ${empty author.id ? 'Save Author' : 'Update Author'}
                </button>
                <a href="${pageContext.request.contextPath}/authors" class="btn btn-secondary">Cancel</a>
            </div>

        </form:form>
    </div>
</div>

<footer>Library Management System &copy; 2024</footer>
</body>
</html>
