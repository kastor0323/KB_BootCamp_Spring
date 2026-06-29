
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

    <title>로그인 | Secure Portal</title>

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

    <link
            href="${pageContext.request.contextPath}/resources/css/common.css"
            rel="stylesheet">

    <style>
        .login-panel {
            width: 100%;
            max-width: 500px;
        }

        .portal-card {
            border-radius: 16px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
        }

        .portal-brand-mark {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 34px;
            height: 34px;
            border-radius: 10px;
            background-color: #0d6efd;
            color: #ffffff;
            font-weight: bold;
        }
    </style>
</head>

<body class="bg-light">

<main
        class="container d-flex align-items-center justify-content-center py-5"
        style="min-height: 100vh;">

    <div class="login-panel">

        <div class="text-center mb-4">

            <a
                    href="${pageContext.request.contextPath}/"
                    class="text-decoration-none d-inline-flex align-items-center gap-2">

                <span class="portal-brand-mark">
                    S
                </span>

                <span class="h4 fw-bold text-dark mb-0">
                    Secure Portal
                </span>

            </a>

        </div>

        <div class="card portal-card border-0">

            <div class="card-body p-4 p-md-5">

                <div class="mb-4">

                    <h1 class="h3 fw-bold mb-2">
                        로그인
                    </h1>

                    <p class="text-secondary mb-0">
                        등록된 계정으로 포털에 접속합니다.
                    </p>

                </div>

                <c:if test="${param.error != null}">

                    <div
                            class="alert alert-danger"
                            role="alert">

                        <div class="fw-bold">
                            로그인에 실패했습니다.
                        </div>

                        <div class="small mt-1">
                            아이디 또는 비밀번호를 확인해 주세요.
                        </div>

                    </div>

                </c:if>

                <c:if test="${param.logout != null}">

                    <div
                            class="alert alert-success"
                            role="alert">

                        정상적으로 로그아웃되었습니다.

                    </div>

                </c:if>

                <!-- 로그인 요청은 Spring Security가 처리합니다. -->
                <form
                        action="${pageContext.request.contextPath}/auth/login-process"
                        method="post">

                    <input
                            type="hidden"
                            name="${_csrf.parameterName}"
                            value="${_csrf.token}">

                    <div class="mb-3">

                        <label
                                for="username"
                                class="form-label fw-semibold">

                            아이디
                        </label>

                        <input
                                type="text"
                                id="username"
                                name="username"
                                class="form-control form-control-lg"
                                placeholder="아이디를 입력하세요"
                                autocomplete="username"
                                required
                                autofocus>

                    </div>

                    <div class="mb-4">

                        <label
                                for="password"
                                class="form-label fw-semibold">

                            비밀번호
                        </label>

                        <input
                                type="password"
                                id="password"
                                name="password"
                                class="form-control form-control-lg"
                                placeholder="비밀번호를 입력하세요"
                                autocomplete="current-password"
                                required>

                    </div>

                    <button
                            type="submit"
                            class="btn btn-primary btn-lg w-100">

                        로그인
                    </button>

                </form>

                <div class="border rounded-3 bg-light p-3 mt-4">

                    <div class="small text-secondary fw-semibold mb-2">
                        테스트 계정
                    </div>

                    <div class="small">
                        일반 회원:
                        <strong>member1 / 1234</strong>
                    </div>

                    <div class="small mt-1">
                        관리자:
                        <strong>admin1 / 1234</strong>
                    </div>

                </div>

                <div class="text-center mt-4">

                    <a
                            href="${pageContext.request.contextPath}/"
                            class="text-decoration-none">

                        메인 페이지로 돌아가기
                    </a>

                </div>

            </div>

        </div>

    </div>

</main>

</body>
</html>

