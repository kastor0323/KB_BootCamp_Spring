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

    <title>접근 권한 없음 | Secure Portal</title>

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
            style="width: 100%; max-width: 700px;">

        <div class="card-body p-4 p-md-5">

            <div class="display-3 fw-bold text-danger">
                403
            </div>

            <h1 class="h2 fw-bold mt-3">
                접근 권한이 없습니다.
            </h1>

            <p class="text-secondary mt-3">
                로그인은 완료되었지만 이 페이지에 필요한 권한이 없습니다.
            </p>

            <div class="alert alert-warning mt-4">

                <div>
                    사용자:
                    <strong>${username}</strong>
                </div>

                <div class="mt-1">
                    현재 권한:
                    <strong>${authorities}</strong>
                </div>

            </div>

            <div class="d-flex flex-wrap gap-2">

                <a
                        href="${pageContext.request.contextPath}/"
                        class="btn btn-dark">

                    메인으로 이동
                </a>

                <a
                        href="${pageContext.request.contextPath}/auth/member"
                        class="btn btn-outline-primary">

                    회원 페이지
                </a>

            </div>

        </div>
    </div>

</main>

</body>
</html>