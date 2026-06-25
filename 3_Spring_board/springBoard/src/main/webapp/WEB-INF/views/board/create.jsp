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

    <title>게시글 등록</title>

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

    <link
            href="${pageContext.request.contextPath}/resources/css/board.css"
            rel="stylesheet">
    ```

</head>

<body>

<main class="container py-5">

    ```
    <div class="card content-card">

        <div class="card-body p-4 p-md-5">

            <div class="mb-4">

            <span
                    class="badge
                    ${boardType == 'GALLERY'
                    ? 'text-bg-success'
                    : 'text-bg-primary'}">

                <c:out value="${boardType}"/>

            </span>

                <h1 class="h2 fw-bold mt-3">

                    <c:choose>

                        <c:when test="${boardType == 'GALLERY'}">
                            갤러리 등록
                        </c:when>

                        <c:otherwise>
                            자료 등록
                        </c:otherwise>

                    </c:choose>

                </h1>

            </div>

            <form
                    action="${pageContext.request.contextPath}/board/create"
                    method="post"
                    enctype="multipart/form-data">

                <input
                        type="hidden"
                        name="boardType"
                        value="${boardType}">

                <div class="mb-3">

                    <label
                            for="title"
                            class="form-label fw-semibold">

                        제목

                    </label>

                    <input
                            type="text"
                            id="title"
                            name="title"
                            class="form-control form-control-lg"
                            maxlength="200"
                            required>

                </div>

                <div class="mb-3">

                    <label
                            for="writer"
                            class="form-label fw-semibold">

                        작성자

                    </label>

                    <input
                            type="text"
                            id="writer"
                            name="writer"
                            class="form-control"
                            maxlength="100"
                            required>

                </div>

                <div class="mb-3">

                    <label
                            for="content"
                            class="form-label fw-semibold">

                        내용

                    </label>

                    <textarea
                            id="content"
                            name="content"
                            class="form-control"
                            rows="10"
                            required></textarea>

                </div>

                <div class="mb-4">

                    <label
                            for="files"
                            class="form-label fw-semibold">

                        첨부파일

                    </label>

                    <input
                            type="file"
                            id="files"
                            name="files"
                            class="form-control"
                            multiple
                    ${boardType == 'GALLERY'
                            ? 'accept="image/*"'
                            : ''}>

                    <div class="form-text">

                        <c:choose>

                            <c:when test="${boardType == 'GALLERY'}">
                                갤러리에는 이미지 파일만 등록할 수 있습니다.
                            </c:when>

                            <c:otherwise>
                                여러 파일을 한 번에 선택할 수 있습니다.
                            </c:otherwise>

                        </c:choose>

                    </div>

                </div>

                <div class="d-flex justify-content-between">

                    <a
                            href="${pageContext.request.contextPath}/board/${boardType == 'GALLERY' ? 'gallery' : 'data'}"
                            class="btn btn-outline-secondary">

                        목록

                    </a>

                    <button
                            type="submit"
                            class="btn
                        ${boardType == 'GALLERY'
                        ? 'btn-success'
                        : 'btn-primary'}">

                        등록

                    </button>

                </div>

            </form>

        </div>

    </div>

</main>

</body>

</html>
