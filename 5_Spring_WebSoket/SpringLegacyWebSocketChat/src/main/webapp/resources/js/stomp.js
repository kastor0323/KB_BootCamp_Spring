(() => {
    'use strict';

    /*
     * JSP에서 전달받은 Context Path
     *
     * 예:
     * /SpringLegacyWebSocketChat
     *
     * 루트 Context로 배포한 경우:
     * 빈 문자열
     */
    const contextPath =
        window.CHAT_CONTEXT_PATH || '';

    /*
     * 현재 페이지의 프로토콜에 따라
     * WebSocket 프로토콜을 결정합니다.
     *
     * http  → ws
     * https → wss
     */
    const protocol =
        location.protocol === 'https:'
            ? 'wss'
            : 'ws';

    /*
     * 최종 WebSocket 연결 주소
     *
     * 예:
     * ws://localhost:8080/SpringLegacyWebSocketChat/chat-app
     */
    const brokerURL =
        `${protocol}://${location.host}` +
        `${contextPath}/chat-app`;

    /*
     * HTML 요소 조회
     */
    const nameInput =
        document.getElementById('name');

    const contentInput =
        document.getElementById('content');

    const connectButton =
        document.getElementById('connect');

    const disconnectButton =
        document.getElementById('disconnect');

    const sendButton =
        document.getElementById('send');

    const connectionForm =
        document.getElementById('connectionForm');

    const messageForm =
        document.getElementById('messageForm');

    const messages =
        document.getElementById('chatMessages');

    const emptyMessage =
        document.getElementById('emptyMessage');

    const connectionBadge =
        document.getElementById('connectionBadge');

    /*
     * 현재 브라우저 사용자의 채팅 이름
     */
    let currentName = '';

    /*
     * 사용자가 직접 연결 끊기 버튼을 눌렀는지 구분합니다.
     *
     * 수동으로 연결을 끊은 경우에는
     * 자동 재연결을 원하지 않으므로 사용합니다.
     */
    let manualDisconnect = false;

    /*
     * STOMP Client 생성
     */
    const stompClient =
        new StompJs.Client({

            /*
             * WebSocket 연결 URL
             */
            brokerURL: brokerURL,

            /*
             * 연결이 비정상적으로 종료되면
             * 3초 후 자동 재연결합니다.
             */
            reconnectDelay: 3000,

            /*
             * 서버에서 클라이언트로 들어오는 Heartbeat 간격
             */
            heartbeatIncoming: 10000,

            /*
             * 클라이언트에서 서버로 보내는 Heartbeat 간격
             */
            heartbeatOutgoing: 10000
        });

    /*
     * WebSocket 및 STOMP 연결 성공 시 실행됩니다.
     */
    stompClient.onConnect = () => {

        /*
         * 연결 상태에 맞게 화면을 변경합니다.
         */
        setConnected(true);

        /*
         * 입장 메시지 토픽 구독
         *
         * 서버의 다음 주소와 연결됩니다.
         *
         * @SendTo("/topic/greetings")
         */
        stompClient.subscribe(
            '/topic/greetings',
            frame => {

                /*
                 * STOMP 메시지 Body는 문자열이므로
                 * JSON 객체로 변환합니다.
                 */
                const greeting =
                    JSON.parse(frame.body);

                showSystemMessage(
                    `${greeting.name}님이 입장했습니다.`
                );
            }
        );

        /*
         * 일반 채팅 메시지 토픽 구독
         *
         * 서버의 다음 주소와 연결됩니다.
         *
         * @SendTo("/topic/chat")
         */
        stompClient.subscribe(
            '/topic/chat',
            frame => {

                const message =
                    JSON.parse(frame.body);

                showChatMessage(message);
            }
        );

        /*
         * 서버로 입장 메시지 전송
         *
         * 서버의 다음 메서드가 실행됩니다.
         *
         * @MessageMapping("/hello")
         */
        stompClient.publish({

            destination: '/app/hello',

            body: JSON.stringify({
                name: currentName
            })
        });

        /*
         * 연결 후 메시지 입력창으로 포커스를 이동합니다.
         */
        contentInput.focus();
    };

    /*
     * WebSocket 연결 오류가 발생한 경우 실행됩니다.
     */
    stompClient.onWebSocketError =
        error => {

            console.error(
                'WebSocket 오류',
                error
            );

            /*
             * 연결 실패 시 버튼을 다시 사용할 수 있도록
             * 연결되지 않은 상태로 변경합니다.
             */
            setConnected(false);

            showSystemMessage(
                'WebSocket 연결 중 오류가 발생했습니다.'
            );
        };

    /*
     * STOMP 처리 오류가 발생한 경우 실행됩니다.
     */
    stompClient.onStompError =
        frame => {

            console.error(
                'STOMP 오류',
                frame.headers['message'],
                frame.body
            );

            showSystemMessage(
                'STOMP 메시지 처리 중 오류가 발생했습니다.'
            );
        };

    /*
     * WebSocket 연결이 종료되었을 때 실행됩니다.
     */
    stompClient.onWebSocketClose = () => {

        setConnected(false);

        /*
         * 사용자가 직접 연결을 끊은 것이 아니라면
         * 서버 연결이 비정상적으로 종료된 상황입니다.
         */
        if (!manualDisconnect) {

            showSystemMessage(
                '서버 연결이 종료되었습니다. 재연결을 시도합니다.'
            );
        }
    };

    /*
     * 채팅 서버 연결
     */
    function connect() {

        const name =
            nameInput.value.trim();

        /*
         * 이름 입력 여부 검사
         */
        if (!name) {

            nameInput.classList.add(
                'is-invalid'
            );

            nameInput.focus();

            return;
        }

        /*
         * 이미 활성화되었거나 연결된 상태라면
         * activate()를 중복 호출하지 않습니다.
         */
        if (
            stompClient.active ||
            stompClient.connected
        ) {
            return;
        }

        /*
         * 입력 오류 표시 제거
         */
        nameInput.classList.remove(
            'is-invalid'
        );

        currentName = name;

        manualDisconnect = false;

        /*
         * 연결을 시도하는 동안 버튼을 비활성화하여
         * 사용자가 여러 번 클릭하는 것을 방지합니다.
         */
        connectButton.disabled = true;
        nameInput.disabled = true;

        connectionBadge.textContent =
            '연결 중';

        connectionBadge.className =
            'badge rounded-pill text-bg-warning';

        /*
         * STOMP 연결 시작
         */
        stompClient.activate();
    }

    /*
     * 사용자가 연결 끊기 버튼을 눌렀을 때 실행됩니다.
     */
    async function disconnect() {

        manualDisconnect = true;

        /*
         * STOMP Client가 활성화된 경우 연결을 종료합니다.
         */
        if (stompClient.active) {

            await stompClient.deactivate();
        }

        setConnected(false);

        showSystemMessage(
            '채팅 연결을 종료했습니다.'
        );
    }

    /*
     * 채팅 메시지 전송
     */
    function sendMessage() {

        const content =
            contentInput.value.trim();

        /*
         * 메시지가 비어 있거나
         * STOMP 연결이 없는 경우 전송하지 않습니다.
         */
        if (
            !content ||
            !stompClient.connected
        ) {
            return;
        }

        /*
         * 서버로 일반 채팅 메시지 전송
         *
         * 서버의 다음 메서드가 실행됩니다.
         *
         * @MessageMapping("/chat")
         */
        stompClient.publish({

            destination: '/app/chat',

            body: JSON.stringify({

                name: currentName,

                content: content
            })
        });

        /*
         * 메시지 전송 후 입력창 초기화
         */
        contentInput.value = '';

        contentInput.focus();
    }

    /*
     * 연결 상태에 따라 입력 요소와 버튼의 상태를 변경합니다.
     */
    function setConnected(connected) {

        /*
         * 연결된 상태에서는 연결 버튼과 이름 입력을 비활성화합니다.
         */
        connectButton.disabled =
            connected;

        nameInput.disabled =
            connected;

        /*
         * 연결된 상태에서만 연결 끊기 버튼을 활성화합니다.
         */
        disconnectButton.disabled =
            !connected;

        /*
         * 연결된 상태에서만 메시지 입력과 전송을 허용합니다.
         */
        contentInput.disabled =
            !connected;

        sendButton.disabled =
            !connected;

        /*
         * 연결 상태 배지 변경
         */
        connectionBadge.textContent =
            connected
                ? '연결됨'
                : '연결 안 됨';

        connectionBadge.className =
            connected
                ? 'badge rounded-pill text-bg-success'
                : 'badge rounded-pill text-bg-secondary';
    }

    /*
     * 입장, 연결 종료 등의 시스템 메시지를 화면에 표시합니다.
     */
    function showSystemMessage(text) {

        removeEmptyMessage();

        const item =
            document.createElement('div');

        item.className =
            'system-message';

        /*
         * innerHTML이 아닌 textContent를 사용하여
         * HTML 및 스크립트 삽입을 방지합니다.
         */
        item.textContent =
            text;

        messages.appendChild(item);

        scrollToBottom();
    }

    /*
     * 일반 채팅 메시지를 화면에 표시합니다.
     */
    function showChatMessage(message) {

        /*
         * 메시지 내용이 없으면 화면에 표시하지 않습니다.
         */
        if (
            !message ||
            !message.content
        ) {
            return;
        }

        removeEmptyMessage();

        const row =
            document.createElement('div');

        /*
         * 현재 사용자 이름과 메시지 작성자 이름이 같으면
         * 내가 보낸 메시지로 판단합니다.
         *
         * 수업용 간단한 구현입니다.
         * 실제 서비스에서는 사용자 고유 ID를 사용하는 것이 좋습니다.
         */
        const isMine =
            message.name === currentName;

        row.className =
            `message-row${isMine ? ' mine' : ''}`;

        /*
         * 메시지 말풍선
         */
        const bubble =
            document.createElement('div');

        bubble.className =
            'message-bubble';

        /*
         * 작성자 이름
         */
        const name =
            document.createElement('div');

        name.className =
            'message-name';

        name.textContent =
            message.name || '이름 없는 사용자';

        /*
         * 메시지 내용
         */
        const content =
            document.createElement('div');

        /*
         * textContent를 사용하여
         * 채팅 메시지에 포함된 HTML과 JavaScript가
         * 실행되지 않도록 합니다.
         */
        content.textContent =
            message.content;

        /*
         * 메시지 전송 시각
         */
        const meta =
            document.createElement('div');

        meta.className =
            'message-meta';

        meta.textContent =
            message.sentAt || '';

        /*
         * 말풍선 내부 요소 구성
         */
        bubble.append(
            name,
            content,
            meta
        );

        row.appendChild(bubble);

        messages.appendChild(row);

        scrollToBottom();
    }

    /*
     * 최초 안내 문구를 제거합니다.
     */
    function removeEmptyMessage() {

        if (
            emptyMessage &&
            emptyMessage.isConnected
        ) {
            emptyMessage.remove();
        }
    }

    /*
     * 가장 최근 메시지가 보이도록
     * 채팅 영역을 아래로 스크롤합니다.
     */
    function scrollToBottom() {

        messages.scrollTop =
            messages.scrollHeight;
    }

    /*
     * 연결 폼 제출 이벤트
     *
     * 채팅 연결 버튼을 누르거나
     * 이름 입력창에서 Enter를 누르면 실행됩니다.
     */
    connectionForm.addEventListener(
        'submit',
        event => {

            /*
             * 일반 form 제출에 의한 페이지 새로고침을 방지합니다.
             */
            event.preventDefault();

            connect();
        }
    );

    /*
     * 연결 끊기 버튼 이벤트
     */
    disconnectButton.addEventListener(
        'click',
        disconnect
    );

    /*
     * 메시지 전송 폼 이벤트
     *
     * 전송 버튼 또는 Enter 키로 실행됩니다.
     */
    messageForm.addEventListener(
        'submit',
        event => {

            event.preventDefault();

            sendMessage();
        }
    );

    /*
     * 이름을 다시 입력하면 오류 표시를 제거합니다.
     */
    nameInput.addEventListener(
        'input',
        () => {

            if (nameInput.value.trim()) {

                nameInput.classList.remove(
                    'is-invalid'
                );
            }
        }
    );

    /*
     * 브라우저 종료 또는 새로고침 시
     * WebSocket 연결을 정리합니다.
     */
    window.addEventListener(
        'beforeunload',
        () => {

            manualDisconnect = true;

            if (stompClient.active) {

                /*
                 * beforeunload에서는 비동기 완료를
                 * 기다리기 어렵지만 연결 종료를 요청합니다.
                 */
                stompClient.deactivate();
            }
        }
    );
})();