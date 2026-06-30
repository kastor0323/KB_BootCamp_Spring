
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

  <title>JWT 로그인</title>

  <link
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-light">

<main
        class="container d-flex align-items-center justify-content-center py-5"
        style="min-height: 100vh;">

  <div
          class="card border-0 shadow"
          style="width: 100%; max-width: 500px;">

    <div class="card-body p-5">

      <div class="text-center mb-4">
                <span class="badge text-bg-primary mb-3">
                    Spring Security + JWT
                </span>

        <h1 class="h3 fw-bold">
          로그인
        </h1>

        <p class="text-secondary mb-0">
          MySQL 회원 정보로 로그인합니다.
        </p>
      </div>

      <div
              id="alertBox"
              class="alert d-none"
              role="alert">
      </div>

      <form id="loginForm">

        <div class="mb-3">
          <label
                  for="username"
                  class="form-label">
            아이디
          </label>

          <input
                  type="text"
                  id="username"
                  class="form-control form-control-lg"
                  value="admin1"
                  autocomplete="username"
                  required>
        </div>

        <div class="mb-4">
          <label
                  for="password"
                  class="form-label">
            비밀번호
          </label>

          <input
                  type="password"
                  id="password"
                  class="form-control form-control-lg"
                  value="1234"
                  autocomplete="current-password"
                  required>
        </div>

        <button
                type="submit"
                id="loginButton"
                class="btn btn-primary btn-lg w-100">
          로그인
        </button>

      </form>

      <div class="alert alert-secondary mt-4 mb-0">
        <div class="fw-bold mb-2">
          테스트 계정
        </div>

        <div>
          일반 회원: member1 / 1234
        </div>

        <div>
          관리자: admin1 / 1234
        </div>
      </div>

      <div class="text-center mt-4">
        <a
                href="${pageContext.request.contextPath}/"
                class="text-decoration-none">
          홈으로 돌아가기
        </a>
      </div>

    </div>
  </div>

</main>

<script>
  const contextPath = '${pageContext.request.contextPath}';

  const loginForm =
          document.querySelector('#loginForm');

  const loginButton =
          document.querySelector('#loginButton');

  const alertBox =
          document.querySelector('#alertBox');

  loginForm.addEventListener(
          'submit',
          async function (event) {
            event.preventDefault();

            hideAlert();

            const username =
                    document.querySelector('#username')
                            .value
                            .trim();

            const password =
                    document.querySelector('#password')
                            .value;

            if (!username || !password) {
              showAlert(
                      '아이디와 비밀번호를 입력하세요.',
                      'danger'
              );

              return;
            }

            loginButton.disabled = true;
            loginButton.textContent = '로그인 처리 중...';

            try {
              const response = await fetch(
                      contextPath + '/api/auth/login',
                      {
                        method: 'POST',

                        headers: {
                          'Content-Type': 'application/json'
                        },

                        body: JSON.stringify({
                          username: username,
                          password: password
                        })
                      }
              );

              const result = await response.json();

              if (!response.ok) {
                throw new Error(
                        result.message
                        || '로그인에 실패했습니다.'
                );
              }

              sessionStorage.setItem(
                      'accessToken',
                      result.accessToken
              );

              sessionStorage.setItem(
                      'tokenType',
                      result.tokenType
              );

              showAlert(
                      result.message,
                      'success'
              );

              setTimeout(function () {
                location.href =
                        contextPath + '/auth/profile';
              }, 500);

            } catch (error) {
              console.error(error);

              showAlert(
                      error.message,
                      'danger'
              );

            } finally {
              loginButton.disabled = false;
              loginButton.textContent = '로그인';
            }
          }
  );

  function showAlert(message, type) {
    alertBox.textContent = message;
    alertBox.className = 'alert alert-' + type;
  }

  function hideAlert() {
    alertBox.textContent = '';
    alertBox.className = 'alert d-none';
  }
</script>

</body>

</html>
