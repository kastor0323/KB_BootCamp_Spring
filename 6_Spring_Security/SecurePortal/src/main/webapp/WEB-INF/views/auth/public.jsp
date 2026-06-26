<%@ page
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">

    <meta
            name="viewport"
            content="width=device-width, initial-scale=1">

    <title>공개 페이지 | Secure Portal</title>

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

            <span class="badge text-bg-secondary px-3 py-2">
                permitAll
            </span>

            <h1 class="display-6 fw-bold mt-4">
                공개 페이지
            </h1>

            <p class="lead text-secondary">
                이 페이지는 로그인하지 않은 사용자도 접근할 수 있습니다.
            </p>

            <div class="alert alert-light border mt-4">
                SecurityConfig의
                <strong>permitAll()</strong>이 적용된 주소입니다.
            </div>

            <div class="d-flex flex-wrap gap-2 mt-4">

                <a
                        href="${pageContext.request.contextPath}/"
                        class="btn btn-dark">

                    메인으로
                </a>

                <a
                        href="${pageContext.request.contextPath}/auth/login"
                        class="btn btn-outline-primary">

                    로그인
                </a>

            </div>

        </div>
    </div>

</main>

</body>
</html>