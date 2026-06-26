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

    <title>페이지를 찾을 수 없음</title>

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

        <div class="card-body p-4 p-md-5 text-center">

            <div class="display-2 fw-bold text-primary">
                404
            </div>

            <h1 class="h2 fw-bold mt-3">
                페이지를 찾을 수 없습니다.
            </h1>

            <p class="text-secondary mt-3">
                요청하신 주소가 존재하지 않거나 이동되었습니다.
            </p>

            <div class="alert alert-light border mt-4 text-break">
                요청 주소:
                <strong>${uri}</strong>
            </div>

            <a
                    href="${pageContext.request.contextPath}/"
                    class="btn btn-primary px-4">

                메인 페이지로 이동
            </a>

        </div>
    </div>

</main>

</body>
</html>