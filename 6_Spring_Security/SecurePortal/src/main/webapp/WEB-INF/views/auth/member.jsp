<%@ page
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<%@ taglib
        prefix="sec"
        uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8">

  <meta
          name="viewport"
          content="width=device-width, initial-scale=1">

  <title>회원 페이지 | Secure Portal</title>

  <link
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

  <link
          href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
</head>

<body>

<main class="container page-section">

  <div class="card portal-card">

    <div class="card-body p-4 p-md-5">

            <span class="badge text-bg-primary px-3 py-2">
                MEMBER
            </span>

      <h1 class="display-6 fw-bold mt-4">
        회원 전용 페이지
      </h1>

      <p class="lead text-secondary">
        MEMBER 또는 ADMIN 권한을 가진 사용자만 접근할 수 있습니다.
      </p>

      <div class="row g-3 mt-4">

        <div class="col-md-6">

          <div class="border rounded-4 p-4 h-100">

            <div class="small text-secondary">
              로그인 사용자
            </div>

            <div class="h5 fw-bold mt-2 mb-0">
              <sec:authentication
                      property="principal.username"/>
            </div>

          </div>

        </div>

        <div class="col-md-6">

          <div class="border rounded-4 p-4 h-100">

            <div class="small text-secondary">
              보유 권한
            </div>

            <div class="h6 fw-bold mt-2 mb-0">
              <sec:authentication
                      property="authorities"/>
            </div>

          </div>

        </div>

      </div>

      <div class="d-flex flex-wrap gap-2 mt-4">

        <a
                href="${pageContext.request.contextPath}/"
                class="btn btn-dark">

          메인으로
        </a>

        <a
                href="${pageContext.request.contextPath}/auth/admin"
                class="btn btn-outline-danger">

          관리자 페이지 확인
        </a>

        <form
                action="${pageContext.request.contextPath}/auth/logout"
                method="post"
                class="m-0">

          <input
                  type="hidden"
                  name="${_csrf.parameterName}"
                  value="${_csrf.token}">

          <button
                  type="submit"
                  class="btn btn-outline-secondary">

            로그아웃
          </button>

        </form>

      </div>

    </div>
  </div>

</main>

</body>
</html>