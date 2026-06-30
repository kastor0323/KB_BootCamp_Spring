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

  <title>인증 사용자 정보</title>

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

    <button
            type="button"
            id="logoutButton"
            class="btn btn-outline-light">
      로그아웃
    </button>
  </div>
</nav>

<main class="container py-5">
  <div class="row justify-content-center">
    <div class="col-lg-8">

      <div
              id="loadingCard"
              class="card border-0 shadow">

        <div class="card-body p-5 text-center">
          <div
                  class="spinner-border text-primary"
                  role="status">
          </div>

          <p class="text-secondary mt-3 mb-0">
            JWT를 검증하고 있습니다.
          </p>
        </div>
      </div>

      <div
              id="profileCard"
              class="card border-0 shadow d-none">

        <div class="card-body p-5">
          <div class="text-center">
                        <span class="badge text-bg-success mb-3">
                            인증 성공
                        </span>

            <h1 class="h2 fw-bold">
              로그인 사용자 정보
            </h1>

            <p class="text-secondary">
              SecurityContext에 등록된 사용자입니다.
            </p>
          </div>

          <div class="list-group mt-4">
            <div
                    class="list-group-item d-flex justify-content-between">
                            <span class="fw-bold">
                                회원 번호
                            </span>

              <span id="memberId"></span>
            </div>

            <div
                    class="list-group-item d-flex justify-content-between">
                            <span class="fw-bold">
                                사용자 아이디
                            </span>

              <span id="username"></span>
            </div>

            <div
                    class="list-group-item d-flex justify-content-between">
                            <span class="fw-bold">
                                이름
                            </span>

              <span id="name"></span>
            </div>

            <div
                    class="list-group-item d-flex justify-content-between">
                            <span class="fw-bold">
                                권한
                            </span>

              <span
                      id="role"
                      class="badge text-bg-primary">
                            </span>
            </div>
          </div>

          <div class="alert alert-info mt-4 mb-0">
            Authorization 헤더의 JWT가 검증된 후
            사용자 정보 API가 실행되었습니다.
          </div>
        </div>
      </div>

      <div
              id="errorCard"
              class="card border-0 shadow d-none">

        <div class="card-body p-5 text-center">
          <div class="display-5 fw-bold text-danger">
            401
          </div>

          <h1 class="h3 mt-3">
            인증에 실패했습니다.
          </h1>

          <p
                  id="errorMessage"
                  class="text-secondary mt-3">
          </p>

          <a
                  href="${pageContext.request.contextPath}/auth/login"
                  class="btn btn-primary mt-3">
            다시 로그인
          </a>
        </div>
      </div>

    </div>
  </div>
</main>

<script>
  const contextPath = '${pageContext.request.contextPath}';

  // 로그인할 때 Session Storage에 저장한 JWT Access Token 조회
  const accessToken =
          sessionStorage.getItem('accessToken');

  document.querySelector('#logoutButton')
          .addEventListener(
                  'click',
                  function () {
                    // 로그아웃 시 브라우저에 저장된 JWT 등 모든 데이터 삭제
                    sessionStorage.clear();

                    location.href =
                            contextPath + '/auth/login';
                  }
          );

  async function loadProfile() {
    // 브라우저에 Access Token이 없으면 인증 요청을 보내지 않음
    if (!accessToken) {
      showError(
              '저장된 Access Token이 없습니다.'
      );

      return;
    }

    try {
      // JWT 인증이 필요한 사용자 정보 API 호출
      const response = await fetch(
              contextPath + '/api/auth/me',
              {
                method: 'GET',

                headers: {
                  // Access Token을 Authorization 요청 헤더에 Bearer 방식으로 전송
                  'Authorization':
                          'Bearer ' + accessToken
                }
              }
      );

      const result = await response.json();

      // JWT가 만료되었거나 유효하지 않으면 서버가 401 응답
      if (!response.ok) {
        throw new Error(
                result.message
                || '인증에 실패했습니다.'
        );
      }

      // JWT 인증 후 서버가 반환한 로그인 사용자 정보 출력
      document.querySelector('#memberId')
              .textContent = result.memberId;

      document.querySelector('#username')
              .textContent = result.username;

      document.querySelector('#name')
              .textContent = result.name;

      document.querySelector('#role')
              .textContent = result.role;

      document.querySelector('#loadingCard')
              .classList.add('d-none');

      document.querySelector('#profileCard')
              .classList.remove('d-none');

    } catch (error) {
      console.error(error);

      // 만료되었거나 유효하지 않은 Access Token 삭제
      sessionStorage.removeItem('accessToken');

      showError(error.message);
    }
  }

  function showError(message) {
    document.querySelector('#loadingCard')
            .classList.add('d-none');

    document.querySelector('#profileCard')
            .classList.add('d-none');

    document.querySelector('#errorCard')
            .classList.remove('d-none');

    document.querySelector('#errorMessage')
            .textContent = message;
  }

  // 페이지가 열리면 JWT를 이용해 현재 사용자 정보 요청
  loadProfile();
</script>

</body>

</html>