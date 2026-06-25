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

    <title>게시글 수정</title>

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
    <c:if test="${not empty message}">

        <div
                class="alert alert-success"
                role="alert">

            <c:out value="${message}"/>

        </div>

    </c:if>

    <div class="card content-card">

        <div class="card-body p-4 p-md-5">

            <div class="mb-4">

            <span
                    class="badge
                    ${board.boardType == 'GALLERY'
                    ? 'text-bg-success'
                    : 'text-bg-primary'}">

                <c:out value="${board.boardType}"/>

            </span>

                <h1 class="h2 fw-bold mt-3 mb-0">
                    게시글 수정
                </h1>

            </div>

            <form
                    action="${pageContext.request.contextPath}/board/edit"
                    method="post"
                    enctype="multipart/form-data">

                <input
                        type="hidden"
                        name="bno"
                        value="${board.bno}">

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
                            value="<c:out value='${board.title}'/>"
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
                            value="<c:out value='${board.writer}'/>"
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
                            required><c:out value="${board.content}"/></textarea>

                </div>

                <div class="mb-4">

                    <label
                            for="files"
                            class="form-label fw-semibold">

                        새로운 첨부파일 추가

                    </label>

                    <input
                            type="file"
                            id="files"
                            name="files"
                            class="form-control"
                            multiple
                    ${board.boardType == 'GALLERY'
                            ? 'accept="image/*"'
                            : ''}>

                    <div class="form-text">

                        <c:choose>

                            <c:when test="${board.boardType == 'GALLERY'}">
                                갤러리에는 이미지 파일만 추가할 수 있습니다.
                            </c:when>

                            <c:otherwise>
                                여러 파일을 한 번에 추가할 수 있습니다.
                            </c:otherwise>

                        </c:choose>

                    </div>

                </div>

                <div class="d-flex justify-content-between">

                    <a
                            href="${pageContext.request.contextPath}/board/read?bno=${board.bno}"
                            class="btn btn-outline-secondary">

                        취소

                    </a>

                    <button
                            type="submit"
                            class="btn
                        ${board.boardType == 'GALLERY'
                        ? 'btn-success'
                        : 'btn-primary'}">

                        수정 완료

                    </button>

                </div>

            </form>

            <c:if test="${not empty board.attachments}">

                <hr class="my-5">

                <h2 class="h5 fw-bold mb-3">
                    기존 첨파일
                </h2>

                <div class="list-group">

                    <c:forEach
                            items="${board.attachments}"
                            var="file">

                        <div
                                class="list-group-item
                                   d-flex flex-column flex-md-row
                                   justify-content-between
                                   align-items-md-center gap-3">

                            <div class="d-flex align-items-center gap-3">

                                <c:if test="${file.imageFile}">

                                    <img
                                            src="${pageContext.request.contextPath}/board/image/${file.ano}"
                                            alt="<c:out value='${file.originalName}'/>"
                                            class="rounded"
                                            style="width: 80px;
                                               height: 60px;
                                               object-fit: cover;">

                                </c:if>

                                <span class="file-name">
                                <c:out value="${file.originalName}"/>
                            </span>

                            </div>

                            <form
                                    action="${pageContext.request.contextPath}/board/file/delete"
                                    method="post"
                                    onsubmit="return confirm('첨부파일을 삭제하시겠습니까?');">

                                <input
                                        type="hidden"
                                        name="ano"
                                        value="${file.ano}">

                                <input
                                        type="hidden"
                                        name="bno"
                                        value="${board.bno}">

                                <button
                                        type="submit"
                                        class="btn btn-outline-danger btn-sm">

                                    파일 삭제

                                </button>

                            </form>

                        </div>

                    </c:forEach>

                </div>

            </c:if>

        </div>

    </div>
    ```

</main>

</body>

</html>
