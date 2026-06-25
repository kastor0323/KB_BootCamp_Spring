<%@ page
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<%@ taglib
        prefix="c"
        uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html> <html lang="ko"> <head> <meta charset="UTF-8">
  <meta
          name="viewport"
          content="width=device-width, initial-scale=1">

  <title>자료실</title>

  <link
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

  <link
          href="${pageContext.request.contextPath}/resources/css/board.css"
          rel="stylesheet">
</head> <body> <nav class="navbar navbar-expand-lg bg-white shadow-sm">
  <div class="container">

    <a
            class="navbar-brand text-primary"
            href="${pageContext.request.contextPath}/">

      Spring Board

    </a>

    <div class="navbar-nav ms-auto">

      <a
              class="nav-link active"
              href="${pageContext.request.contextPath}/board/data">

        자료실

      </a>

      <a
              class="nav-link"
              href="${pageContext.request.contextPath}/board/gallery">

        갤러리

      </a>

    </div>

  </div>
</nav> <main class="container py-5">
  <div
          class="d-flex flex-column flex-md-row
               justify-content-between
               align-items-md-center gap-3 mb-4">

    <div>

      <h1 class="fw-bold mb-2">
        자료실
      </h1>

      <p class="text-secondary mb-0">
        필요한 자료를 등록하고 내려받을 수 있습니다.
      </p>

    </div>

    <a
            href="${pageContext.request.contextPath}/board/create?boardType=DATA"
            class="btn btn-primary">

      자료 등록

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

  <div class="card content-card">

    <div class="card-body p-0">

      <div class="table-responsive">

        <table
                class="table table-hover
                           align-middle board-table mb-0">

          <thead class="table-light">

          <tr>
            <th class="text-center">번호</th>
            <th>제목</th>
            <th class="text-center">작성자</th>
            <th class="text-center">조회수</th>
            <th class="text-center">등록일</th>
          </tr>

          </thead>

          <tbody>

          <c:forEach
                  items="${list}"
                  var="board">

            <tr>

              <td class="text-center">
                <c:out value="${board.bno}"/>
              </td>

              <td>

                <a
                        class="board-title-link"
                        href="${pageContext.request.contextPath}/board/read?bno=${board.bno}">

                  <c:out value="${board.title}"/>

                </a>

              </td>

              <td class="text-center">
                <c:out value="${board.writer}"/>
              </td>

              <td class="text-center">
                <c:out value="${board.viewCount}"/>
              </td>

              <td class="text-center">
                <c:out value="${board.createdAt}"/>
              </td>

            </tr>

          </c:forEach>

          <c:if test="${empty list}">

            <tr>

              <td
                      colspan="5"
                      class="text-center text-secondary py-5">

                등록된 자료가 없습니다.

              </td>

            </tr>

          </c:if>

          </tbody>

        </table>

      </div>

    </div>

  </div>
</main> <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"> </script> </body> </html>