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

    <title>게시글 상세보기</title>

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

        <div class="alert alert-success">
            <c:out value="${message}"/>
        </div>

    </c:if>

    <div class="card content-card">

        <div class="card-body p-4 p-md-5">

            <div
                    class="d-flex flex-column flex-md-row
                       justify-content-between gap-3">

                <div>

                <span
                        class="badge
                        ${board.boardType == 'GALLERY'
                        ? 'text-bg-success'
                        : 'text-bg-primary'}">

                    <c:out value="${board.boardType}"/>

                </span>

                    <h1 class="h2 fw-bold mt-3 mb-0">
                        <c:out value="${board.title}"/>
                    </h1>

                </div>

                <div class="text-secondary small">

                    <div>
                        작성자:
                        <c:out value="${board.writer}"/>
                    </div>

                    <div>
                        조회수:
                        <c:out value="${board.viewCount}"/>
                    </div>

                    <div>
                        등록일:
                        <c:out value="${board.createdAt}"/>
                    </div>

                </div>

            </div>

            <hr class="my-4">

            <div class="board-content">
                <c:out value="${board.content}"/>
            </div>

            <c:if test="${not empty board.attachments}">

                <hr class="my-4">

                <h2 class="h5 fw-bold mb-3">
                    첨부파일
                </h2>

                <div class="row g-3">

                    <c:forEach
                            items="${board.attachments}"
                            var="file">

                        <c:if test="${file.imageFile}">

                            <div class="col-12 text-center">

                                <a
                                        href="${pageContext.request.contextPath}/board/image/${file.ano}"
                                        target="_blank">

                                    <img
                                            src="${pageContext.request.contextPath}/board/image/${file.ano}"
                                            class="preview-image"
                                            alt="<c:out value='${file.originalName}'/>">

                                </a>

                            </div>

                        </c:if>

                        <div class="col-12">

                            <div
                                    class="list-group-item
                                       border rounded-3 p-3
                                       d-flex flex-column flex-md-row
                                       justify-content-between
                                       align-items-md-center gap-3">

                            <span class="file-name">
                                <c:out value="${file.originalName}"/>
                            </span>

                                <a
                                        href="${pageContext.request.contextPath}/board/download/${file.ano}"
                                        class="btn btn-outline-primary btn-sm">

                                    다운로드

                                </a>

                            </div>

                        </div>

                    </c:forEach>

                </div>

            </c:if>

            <hr class="my-4">

            <div
                    class="d-flex flex-wrap
                       justify-content-between gap-2">

                <a
                        href="${pageContext.request.contextPath}/board/${board.boardType == 'GALLERY' ? 'gallery' : 'data'}"
                        class="btn btn-outline-secondary">

                    목록

                </a>

                <div class="d-flex gap-2">

                    <a
                            href="${pageContext.request.contextPath}/board/edit?bno=${board.bno}"
                            class="btn btn-warning">

                        수정

                    </a>

                    <form
                            action="${pageContext.request.contextPath}/board/delete"
                            method="post"
                            onsubmit="return confirm('게시글을 삭제하시겠습니까?');">

                        <input
                                type="hidden"
                                name="bno"
                                value="${board.bno}">

                        <input
                                type="hidden"
                                name="boardType"
                                value="${board.boardType}">

                        <button
                                type="submit"
                                class="btn btn-danger">

                            삭제

                        </button>

                    </form>

                </div>

            </div>

        </div>

    </div>
    ```

</main>

</body>

</html>
