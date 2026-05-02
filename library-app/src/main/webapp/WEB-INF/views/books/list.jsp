<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Library – Books</title>
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
        <h1>📖 All Books</h1>
        <a href="${pageContext.request.contextPath}/books/new" class="btn btn-primary">+ Add Book</a>
    </div>

    <div class="card">
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Title</th>
                    <th>Genre</th>
                    <th>Year</th>
                    <th>ISBN</th>
                    <th>Author</th>
                    <th>Nationality</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty bookDetails}">
                        <tr><td colspan="8" style="text-align:center;padding:2rem;color:#999;">No books found.</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="b" items="${bookDetails}" varStatus="s">
                            <tr>
                                <td>${s.count}</td>
                                <td><strong>${b.bookTitle}</strong></td>
                                <td><span class="badge badge-genre">${b.genre}</span></td>
                                <td>${b.publishYear}</td>
                                <td style="font-size:.82rem;color:#666;">${b.isbn}</td>
                                <td>${b.authorName}</td>
                                <td><span class="badge badge-national">${b.nationality}</span></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/books/edit/${b.bookId}"
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
