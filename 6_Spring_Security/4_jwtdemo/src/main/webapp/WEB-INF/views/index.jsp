<%@ page
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"
        session="false" %>

<!DOCTYPE html>

<html lang="ko">

<head>
    <meta charset="UTF-8">

    <meta
            name="viewport"
            content="width=device-width, initial-scale=1">

    <title>Spring Security JWT</title>

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>

<body class="bg-light">

<nav class="navbar navbar-dark bg-dark">
    <div class="container">
        <a
                class="navbar-brand fw-bold"
                href="${pageContext.request.contextPath}/">
            Spring Security JWT
        </a>
    </div>
</nav>

<main class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-10">
            <div class="card border-0 shadow">
                <div class="card-body p-5">

                    <div class="text-center">
                        <span class="badge text-bg-primary mb-3">
                            Spring Legacy
                        </span>

                        <h1 class="display-5 fw-bold">
                            DB 기반 JWT 로그인
                        </h1>

                        <p class="lead text-secondary mt-3">
                            Spring Security와 MySQL을 이용하여
                            로그인하고 JWT 인증 요청을 처리합니다.
                        </p>
                    </div>

                    <div class="row g-4 mt-4">
                        <div class="col-md-4">
                            <div class="border rounded p-4 h-100">
                                <h2 class="h5 fw-bold">
                                    1. DB 인증
                                </h2>

                                <p class="text-secondary mb-0">
                                    UserDetailsService가 MySQL에서
                                    사용자 정보를 조회합니다.
                                </p>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="border rounded p-4 h-100">
                                <h2 class="h5 fw-bold">
                                    2. JWT 발급
                                </h2>

                                <p class="text-secondary mb-0">
                                    로그인 인증 성공 후
                                    Access Token을 발급합니다.
                                </p>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="border rounded p-4 h-100">
                                <h2 class="h5 fw-bold">
                                    3. API 인증
                                </h2>

                                <p class="text-secondary mb-0">
                                    JWT를 검증하고 인증 사용자 정보를
                                    SecurityContext에 저장합니다.
                                </p>
                            </div>
                        </div>
                    </div>

                    <div class="text-center mt-5">
                        <a
                                href="${pageContext.request.contextPath}/auth/login"
                                class="btn btn-primary btn-lg px-5">
                            로그인 실습 시작
                        </a>
                    </div>

                </div>
            </div>
        </div>
    </div>
</main>

</body>

</html>