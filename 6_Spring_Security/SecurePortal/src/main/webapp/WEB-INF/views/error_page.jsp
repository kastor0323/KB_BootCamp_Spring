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

  <meta
          name="viewport"
          content="width=device-width, initial-scale=1">

  <title>오류 발생</title>

  <link
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

  <link
          href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
</head>

<body>

<main
        class="container d-flex align-items-center justify-content-center py-5"
        style="min-height: 100vh;">

  <div
          class="card portal-card"
          style="width: 100%; max-width: 800px;">

    <div class="card-body p-4 p-md-5">

      <div class="display-3 fw-bold text-danger">
        500
      </div>

      <h1 class="h2 fw-bold mt-3">
        요청을 처리하는 중 오류가 발생했습니다.
      </h1>

      <p class="text-secondary mt-3">
        잠시 후 다시 시도해 주세요.
      </p>

      <c:if test="${not empty exception}">

        <div class="alert alert-danger mt-4">

          <div class="fw-bold">
            오류 메시지
          </div>

          <div class="small mt-2 text-break">
              ${exception.message}
          </div>

        </div>

      </c:if>

      <a
              href="${pageContext.request.contextPath}/"
              class="btn btn-dark">

        메인 페이지로 이동
      </a>

    </div>
  </div>

</main>

</body>
</html>