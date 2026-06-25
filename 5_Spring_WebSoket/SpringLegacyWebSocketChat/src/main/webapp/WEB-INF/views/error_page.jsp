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

  ```
  <meta
          name="viewport"
          content="width=device-width, initial-scale=1">

  <title>오류 발생</title>

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
          class="card border-0 shadow"
          style="width: 100%; max-width: 900px;">

    <div class="card-body p-5">

      <h1 class="text-danger fw-bold">
        오류가 발생했습니다.
      </h1>

      <p class="text-secondary mt-3">
        요청을 처리하는 중 문제가 발생했습니다.
      </p>

      <div class="alert alert-danger mt-4 mb-0">

        <c:out
                value="${exception.message}"
                default="처리 중 알 수 없는 오류가 발생했습니다."/>

      </div>

      <h2 class="h5 mt-4">
        오류 상세 내용
      </h2>

      <div
              class="mt-3 overflow-auto"
              style="max-height: 400px;">

        <ul class="list-group">

          <c:forEach
                  items="${exception.stackTrace}"
                  var="stack">

            <li class="list-group-item">

              <code>
                <c:out value="${stack}"/>
              </code>

            </li>

          </c:forEach>

        </ul>

      </div>

      <div class="text-center mt-4">

        <a
                href="${pageContext.request.contextPath}/"
                class="btn btn-warning fw-semibold">

          채팅방으로 이동

        </a>

      </div>

    </div>

  </div>
  ```

</main>

</body>

</html>
