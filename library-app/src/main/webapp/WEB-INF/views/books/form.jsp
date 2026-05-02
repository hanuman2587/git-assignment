<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"    uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${empty book.id ? 'Add Book' : 'Edit Book'} – Library</title>
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
        <h2>${empty book.id ? '➕ Add New Book' : '✏️ Edit Book'}</h2>

        <c:choose>
            <c:when test="${empty book.id}">
                <c:set var="actionUrl" value="${pageContext.request.contextPath}/books/save"/>
            </c:when>
            <c:otherwise>
                <c:set var="actionUrl" value="${pageContext.request.contextPath}/books/update/${book.id}"/>
            </c:otherwise>
        </c:choose>

        <form:form action="${actionUrl}" method="post" modelAttribute="book">

            <div class="form-group">
                <label for="title">Title *</label>
                <form:input path="title" id="title" placeholder="Enter book title"/>
                <form:errors path="title" cssClass="error-msg"/>
            </div>

            <div class="form-group">
                <label for="genre">Genre *</label>
                <form:input path="genre" id="genre" placeholder="e.g., Fiction, Mystery"/>
                <form:errors path="genre" cssClass="error-msg"/>
            </div>

            <div class="form-group">
                <label for="publishYear">Publication Year</label>
                <form:input path="publishYear" id="publishYear" type="number" placeholder="e.g., 2023"/>
            </div>

            <div class="form-group">
                <label for="isbn">ISBN</label>
                <form:input path="isbn" id="isbn" placeholder="e.g., 978-0000000000"/>
            </div>

            <div class="form-group">
                <label for="authorId">Author *</label>
                <select name="authorId" id="authorId" required>
                    <option value="">-- Select Author --</option>
                    <c:forEach var="author" items="${authors}">
                        <option value="${author.id}"
                            ${book.author != null && book.author.id == author.id ? 'selected' : ''}>
                            ${author.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    ${empty book.id ? 'Save Book' : 'Update Book'}
                </button>
                <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Cancel</a>
            </div>

        </form:form>
    </div>
</div>

<footer>Library Management System &copy; 2024</footer>
</body>
</html>
