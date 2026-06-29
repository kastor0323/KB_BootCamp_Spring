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

    <title>관리자 페이지 | Secure Portal</title>

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

            <span class="badge text-bg-danger px-3 py-2">
                ADMIN
            </span>

            <h1 class="display-6 fw-bold mt-4">
                관리자 전용 페이지
            </h1>

            <p class="lead text-secondary">
                ADMIN 권한을 가진 사용자만 접근할 수 있습니다.
            </p>

            <div class="alert alert-danger mt-4">

                <div class="fw-bold">
                    관리자 인증이 완료되었습니다.
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

            <div class="row g-3 mt-3">

                <div class="col-md-4">

                    <div class="border rounded-4 p-4 h-100">

                        <div class="fw-bold">
                            사용자 관리
                        </div>

                        <div class="small text-secondary mt-2">
                            회원 상태와 권한을 관리하는 영역입니다.
                        </div>

                    </div>

                </div>

                <div class="col-md-4">

                    <div class="border rounded-4 p-4 h-100">

                        <div class="fw-bold">
                            권한 관리
                        </div>

                        <div class="small text-secondary mt-2">
                            MEMBER와 ADMIN 권한을 관리하는 영역입니다.
                        </div>

                    </div>

                </div>

                <div class="col-md-4">

                    <div class="border rounded-4 p-4 h-100">

                        <div class="fw-bold">
                            접속 기록
                        </div>

                        <div class="small text-secondary mt-2">
                            사용자 로그인 기록을 확인하는 영역입니다.
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
                        href="${pageContext.request.contextPath}/auth/member"
                        class="btn btn-outline-primary">

                    회원 페이지
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
                            class="btn btn-outline-danger">

                        로그아웃
                    </button>

                </form>

            </div>

        </div>
    </div>

</main>

</body>
</html>