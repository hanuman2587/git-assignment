<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Library – Authors</title>
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

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">${errorMessage}</div>
    </c:if>

    <div class="page-header">
        <h1>✍️ All Authors</h1>
        <a href="${pageContext.request.contextPath}/authors/new" class="btn btn-primary">+ Add Author</a>
    </div>

    <div class="card">
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Nationality</th>
                    <th>Birth Year</th>
                    <th>Books Count</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty authors}">
                        <tr><td colspan="6" style="text-align:center;padding:2rem;color:#999;">No authors found.</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="a" items="${authors}" varStatus="s">
                            <tr>
                                <td>${s.count}</td>
                                <td><strong>${a.name}</strong></td>
                                <td><span class="badge badge-national">${a.nationality}</span></td>
                                <td>${a.birthYear}</td>
                                <td>
                                    <span class="badge badge-genre">
                                        ${a.books.size()} book${a.books.size() != 1 ? 's' : ''}
                                    </span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/authors/edit/${a.id}"
                                       class="btn btn-warning btn-sm">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

</div>

<footer>Library Management System &copy; 2024</footer>
</body>
</html>
