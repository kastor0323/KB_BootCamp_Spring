<%@ page
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<%@ taglib
        prefix="c"
        uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="ko">

<head>
    <meta charset="UTF-8">

    ```
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1">

    <title>갤러리</title>

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

    <link
            href="${pageContext.request.contextPath}/resources/css/board.css"
            rel="stylesheet">
    ```

</head>

<body>

<nav class="navbar navbar-expand-lg bg-white shadow-sm">

    ```
    <div class="container">

        <a
                class="navbar-brand text-primary"
                href="${pageContext.request.contextPath}/">

            Spring Board

        </a>

        <div class="navbar-nav ms-auto">

            <a
                    class="nav-link"
                    href="${pageContext.request.contextPath}/board/data">

                자료실

            </a>

            <a
                    class="nav-link active"
                    href="${pageContext.request.contextPath}/board/gallery">

                갤러리

            </a>

        </div>

    </div>
    ```

</nav>

<main class="container py-5">

    ```
    <div
            class="d-flex flex-column flex-md-row
               justify-content-between
               align-items-md-center gap-3 mb-4">

        <div>

            <h1 class="fw-bold mb-2">
                갤러리
            </h1>

            <p class="text-secondary mb-0">
                업로드된 이미지를 카드 형태로 확인할 수 있습니다.
            </p>

        </div>

        <a
                href="${pageContext.request.contextPath}/board/create?boardType=GALLERY"
                class="btn btn-success">

            이미지 등록

        </a>

    </div>

    <c:if test="${not empty message}">

        <div
                class="alert alert-success alert-dismissible fade show"
                role="alert">

            <c:out value="${message}"/>

            <button
                    type="button"
                    class="btn-close"
                    data-bs-dismiss="alert"
                    aria-label="닫기">
            </button>

        </div>

    </c:if>

    <div class="row g-4">

        <c:forEach
                items="${list}"
                var="board">

            <div class="col-sm-6 col-lg-4">

                <div class="card gallery-card">

                    <c:choose>

                        <c:when test="${not empty board.thumbnail}">

                            <img
                                    src="${pageContext.request.contextPath}/board/image/${board.thumbnail.ano}"
                                    class="gallery-image"
                                    alt="대표 이미지">

                        </c:when>

                        <c:otherwise>

                            <div class="gallery-empty">
                                등록된 이미지가 없습니다.
                            </div>

                        </c:otherwise>

                    </c:choose>

                    <div class="card-body p-4">

                        <h2 class="h5 fw-bold">

                            <a
                                    class="board-title-link"
                                    href="${pageContext.request.contextPath}/board/read?bno=${board.bno}">

                                <c:out value="${board.title}"/>

                            </a>

                        </h2>

                        <div
                                class="d-flex justify-content-between
                                   text-secondary small mt-3">

                        <span>
                            <c:out value="${board.writer}"/>
                        </span>

                            <span>
                            조회 <c:out value="${board.viewCount}"/>
                        </span>

                        </div>

                    </div>

                </div>

            </div>

        </c:forEach>

        <c:if test="${empty list}">

            <div class="col-12">

                <div
                        class="card content-card
                           text-center text-secondary py-5">

                    등록된 갤러리 게시글이 없습니다.

                </div>

            </div>

        </c:if>

    </div>
    ```

</main>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>

</html>
