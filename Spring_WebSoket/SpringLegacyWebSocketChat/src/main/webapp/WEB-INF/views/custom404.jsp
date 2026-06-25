<%@ page
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"
        session="false" %>

<%@ taglib
        uri="http://java.sun.com/jsp/jstl/core"
        prefix="c" %>

<!DOCTYPE html>

<html lang="ko">

<head>
    <meta charset="UTF-8">


    <meta
            name="viewport"
            content="width=device-width, initial-scale=1">

    <title>페이지를 찾을 수 없습니다.</title>

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">


</head>

<body class="bg-light">

<main
        class="container d-flex
               align-items-center
               justify-content-center py-5"
        style="min-height: 100vh;">


    <div
            class="card border-0 shadow text-center"
            style="width: 100%; max-width: 700px;">

        <div class="card-body p-5">

            <div class="display-1 fw-bold text-danger">
                404
            </div>

            <h1 class="h3 mt-3">
                페이지를 찾을 수 없습니다.
            </h1>

            <p class="text-secondary mt-3">
                요청하신 주소가 존재하지 않거나 변경되었습니다.
            </p>

            <div class="alert alert-secondary mt-4 mb-0">

                요청 주소:

                <strong>
                    <c:out
                            value="${uri}"
                            default="확인할 수 없음"/>
                </strong>

            </div>

            <a
                    href="${pageContext.request.contextPath}/"
                    class="btn btn-warning fw-semibold mt-4">

                채팅방으로 이동

            </a>

        </div>

    </div>


</main>

</body>

</html>
