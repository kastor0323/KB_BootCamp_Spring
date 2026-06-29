<%@ page
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<%@ taglib
        prefix="c"
        uri="http://java.sun.com/jsp/jstl/core" %>

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

    <title>Secure Portal</title>

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

    <link
            href="${pageContext.request.contextPath}/resources/css/common.css"
            rel="stylesheet">
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-dark portal-navbar">
    <div class="container py-2">

        <a
                href="${pageContext.request.contextPath}/"
                class="navbar-brand d-flex align-items-center gap-2">

            <span class="portal-brand-mark">
                S
            </span>

            <span class="fw-bold">
                Secure Portal
            </span>
        </a>

        <div class="d-flex align-items-center gap-2">

            <sec:authorize access="isAnonymous()">
                <a
                        href="${pageContext.request.contextPath}/auth/login"
                        class="btn btn-outline-light">

                    로그인
                </a>
            </sec:authorize>

            <sec:authorize access="isAuthenticated()">

                <span class="text-white-50 d-none d-md-inline">
                    <sec:authentication
                            property="principal.username"/>
                    님
                </span>

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
                            class="btn btn-outline-warning">

                        로그아웃
                    </button>
                </form>

            </sec:authorize>

        </div>
    </div>
</nav>

<main class="container page-section">

    <div class="portal-card bg-white p-4 p-md-5">

        <div class="row align-items-center g-5">

            <div class="col-lg-7">

                <span class="badge text-bg-primary px-3 py-2">
                    Spring Security 5.8
                </span>

                <h1 class="display-5 fw-bold mt-4">
                    로그인과 권한 관리를 위한
                    <span class="text-primary">
                        Secure Portal
                    </span>
                </h1>

                <p class="lead text-secondary mt-4">
                    Spring MVC, MyBatis, MySQL,
                    Spring Security를 연결한 로그인·로그아웃 예제입니다.
                </p>

                <sec:authorize access="isAuthenticated()">

                    <div class="alert alert-success mt-4 mb-0">

                        <div class="fw-bold">
                            로그인 상태입니다.
                        </div>

                        <div class="mt-2">
                            사용자:
                            <strong>
                                <sec:authentication
                                        property="principal.username"/>
                            </strong>
                        </div>

                        <div>
                            권한:
                            <strong>
                                <sec:authentication
                                        property="authorities"/>
                            </strong>
                        </div>

                    </div>

                </sec:authorize>

                <sec:authorize access="isAnonymous()">

                    <div class="d-flex flex-wrap gap-2 mt-4">

                        <a
                                href="${pageContext.request.contextPath}/auth/login"
                                class="btn btn-primary btn-lg px-4">

                            로그인 시작
                        </a>

                        <a
                                href="${pageContext.request.contextPath}/auth/public"
                                class="btn btn-outline-secondary btn-lg px-4">

                            공개 페이지
                        </a>

                    </div>

                </sec:authorize>

            </div>

            <div class="col-lg-5">

                <div class="bg-light rounded-4 p-4">

                    <h2 class="h5 fw-bold mb-4">
                        테스트 계정
                    </h2>

                    <div class="border rounded-3 bg-white p-3 mb-3">
                        <div class="small text-secondary">
                            일반 회원
                        </div>

                        <div class="fw-bold mt-1">
                            member1 / 1234
                        </div>

                        <div class="small text-primary mt-1">
                            ROLE_MEMBER
                        </div>
                    </div>

                    <div class="border rounded-3 bg-white p-3">
                        <div class="small text-secondary">
                            관리자
                        </div>

                        <div class="fw-bold mt-1">
                            admin1 / 1234
                        </div>

                        <div class="small text-danger mt-1">
                            ROLE_MEMBER, ROLE_ADMIN
                        </div>
                    </div>

                </div>

            </div>
        </div>

    </div>

    <div class="row g-4 mt-2">

        <div class="col-md-4">

            <div class="card portal-card permission-card h-100">
                <div class="card-body p-4">

                    <div class="portal-icon-box">
                        P
                    </div>

                    <h2 class="h5 fw-bold mt-4">
                        공개 페이지
                    </h2>

                    <p class="text-secondary">
                        로그인하지 않은 사용자도 접근할 수 있습니다.
                    </p>

                    <a
                            href="${pageContext.request.contextPath}/auth/public"
                            class="btn btn-outline-secondary">

                        페이지 열기
                    </a>

                </div>
            </div>

        </div>

        <div class="col-md-4">

            <div class="card portal-card permission-card h-100">
                <div class="card-body p-4">

                    <div class="portal-icon-box">
                        M
                    </div>

                    <h2 class="h5 fw-bold mt-4">
                        회원 페이지
                    </h2>

                    <p class="text-secondary">
                        MEMBER 또는 ADMIN 권한이 필요합니다.
                    </p>

                    <a
                            href="${pageContext.request.contextPath}/auth/member"
                            class="btn btn-primary">

                        페이지 열기
                    </a>

                </div>
            </div>

        </div>

        <div class="col-md-4">

            <div class="card portal-card permission-card h-100">
                <div class="card-body p-4">

                    <div class="portal-icon-box">
                        A
                    </div>

                    <h2 class="h5 fw-bold mt-4">
                        관리자 페이지
                    </h2>

                    <p class="text-secondary">
                        ADMIN 권한을 가진 사용자만 접근할 수 있습니다.
                    </p>

                    <a
                            href="${pageContext.request.contextPath}/auth/admin"
                            class="btn btn-danger">

                        페이지 열기
                    </a>

                </div>
            </div>

        </div>

    </div>

</main>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>