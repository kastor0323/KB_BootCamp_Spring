<%@ page
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"
        session="false" %>

<!DOCTYPE html>

<html lang="ko">

<head>
    <meta charset="UTF-8">

    ```
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1">

    <title>Spring STOMP 실시간 채팅</title>

    <!-- Bootstrap 5 CSS -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

    <!-- 채팅 화면 전용 CSS -->
    <link
            href="${pageContext.request.contextPath}/resources/css/chat.css"
            rel="stylesheet">

    <!--
        STOMP.js UMD 번들

        로딩 후 JavaScript에서 다음 전역 객체를 사용할 수 있습니다.

        StompJs.Client
    -->
    <script
            src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.0.0/bundles/stomp.umd.min.js">
    </script>
    ```

</head>

<body>

<div class="chat-shell container py-4 py-lg-5">

    ```
    <div class="row justify-content-center">

        <div class="col-12 col-xl-10">

            <section class="chat-card shadow-lg overflow-hidden">

                <!-- 채팅 헤더 -->
                <header
                        class="chat-header px-4 py-3
                           d-flex justify-content-between
                           align-items-center">

                    <div>

                        <p class="small mb-1 opacity-75">
                            Spring WebSocket · STOMP
                        </p>

                        <h1 class="h4 fw-bold mb-0">
                            실시간 채팅방
                        </h1>

                    </div>

                    <!-- WebSocket 연결 상태 -->
                    <span
                            id="connectionBadge"
                            class="badge rounded-pill text-bg-secondary">

                    연결 안 됨

                </span>

                </header>

                <div class="row g-0">

                    <!-- 왼쪽 접속 설정 영역 -->
                    <aside
                            class="col-lg-4 border-end
                               bg-body-tertiary p-4">

                        <h2 class="h6 fw-bold mb-3">
                            접속 설정
                        </h2>

                        <!-- 연결 폼 -->
                        <form id="connectionForm">

                            <label
                                    for="name"
                                    class="form-label">

                                이름

                            </label>

                            <input
                                    id="name"
                                    type="text"
                                    class="form-control form-control-lg"
                                    maxlength="20"
                                    placeholder="채팅 이름을 입력하세요"
                                    autocomplete="off">

                            <!--
                                Bootstrap validation 오류 메시지

                                JavaScript에서 이름이 비어 있으면
                                input에 is-invalid 클래스를 추가합니다.
                            -->
                            <div class="invalid-feedback">
                                이름을 입력해 주세요.
                            </div>

                            <div class="d-grid gap-2 mt-3">

                                <button
                                        id="connect"
                                        type="submit"
                                        class="btn btn-warning fw-semibold">

                                    채팅 연결

                                </button>

                                <button
                                        id="disconnect"
                                        type="button"
                                        class="btn btn-outline-secondary"
                                        disabled>

                                    연결 끊기

                                </button>

                            </div>

                        </form>

                        <hr class="my-4">

                        <!-- WebSocket 및 STOMP 주소 안내 -->
                        <div class="small text-secondary lh-lg">

                            <div>
                                <strong>연결 엔드포인트</strong>
                            </div>

                            <code>/chat-app</code>

                            <div class="mt-2">
                                <strong>발행 목적지</strong>
                            </div>

                            <code>/app/chat</code>

                            <div class="mt-2">
                                <strong>구독 토픽</strong>
                            </div>

                            <code>/topic/chat</code>

                        </div>

                    </aside>

                    <!-- 오른쪽 채팅 영역 -->
                    <main
                            class="col-lg-8 d-flex
                               flex-column bg-white">

                        <!-- 메시지가 출력되는 영역 -->
                        <div
                                id="chatMessages"
                                class="chat-messages p-4"
                                aria-live="polite">

                            <!-- 채팅 시작 전 안내 문구 -->
                            <div
                                    id="emptyMessage"
                                    class="empty-state
                                       text-center text-secondary">

                                <div class="display-6 mb-3">
                                    대화를 시작해 보세요
                                </div>

                                <p class="mb-0">
                                    이름을 입력하고 채팅 연결 버튼을 누르세요.
                                </p>

                            </div>

                        </div>

                        <!-- 메시지 입력 폼 -->
                        <form
                                id="messageForm"
                                class="message-form border-top p-3 p-md-4">

                            <div class="input-group input-group-lg">

                                <input
                                        id="content"
                                        type="text"
                                        class="form-control"
                                        maxlength="300"
                                        placeholder="메시지를 입력하세요"
                                        autocomplete="off"
                                        disabled>

                                <button
                                        id="send"
                                        class="btn btn-warning
                                           fw-semibold px-4"
                                        type="submit"
                                        disabled>

                                    전송

                                </button>

                            </div>

                            <div class="form-text">
                                Enter 키로 전송할 수 있습니다.
                            </div>

                        </form>

                    </main>

                </div>

            </section>

        </div>

    </div>
    ```

</div>

<!--
    현재 애플리케이션의 Context Path를 JavaScript로 전달합니다.

    예:
    /SpringLegacyWebSocketChat

    루트 Context로 배포한 경우에는 빈 문자열입니다.
-->

<script>
    window.CHAT_CONTEXT_PATH =
        '${pageContext.request.contextPath}';
</script>

<!-- 채팅 기능 JavaScript -->

<script
        src="${pageContext.request.contextPath}/resources/js/stomp.js">
</script>

</body>

</html>
