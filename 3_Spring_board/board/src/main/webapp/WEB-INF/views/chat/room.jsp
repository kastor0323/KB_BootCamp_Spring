<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layouts/header.jsp" %>
<div class="container my-5">
    <h2>채팅방: ${roomName}</h2>
    <div class="d-flex justify-content-between mb-3">
        <span>대화명: <strong id="senderName"></strong></span>
        <button class="btn btn-danger" type="button" onclick="quit()">방 나가기</button>
    </div>
    
    <div class="card mb-3">
        <div class="card-body" id="chatArea" style="height: 400px; overflow-y: auto;">
            <ul class="list-unstyled" id="messages"></ul>
        </div>
    </div>
    
    <div class="input-group">
        <input type="text" id="message" class="form-control" placeholder="메시지를 입력하세요" onkeypress="if(event.keyCode==13) {sendMessage();}">
        <button class="btn btn-primary" type="button" onclick="sendMessage()">보내기</button>
    </div>
</div>

<!-- SockJS and Stomp.js -->
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

<script>
    var roomId = '${roomId}';
    var sender = localStorage.getItem('wschat.sender');
    var sock = new SockJS("/chat-app");
    var ws = Stomp.over(sock);
    
    document.getElementById('senderName').innerText = sender;

    ws.connect({}, function(frame) {
        // Subscribe to the room's topic
        ws.subscribe("/topic/chat/room/" + roomId, function(message) {
            var recv = JSON.parse(message.body);
            recvMessage(recv);
        });
        
        // Send ENTER message
        ws.send("/app/chat/message", {}, JSON.stringify({type:'ENTER', roomId:roomId, sender:sender}));
    }, function(error) {
        alert("서버 연결에 실패 하였습니다. 다시 접속해 주십시요.");
        location.href="/chat/rooms";
    });

    function sendMessage() {
        var messageInput = document.getElementById('message');
        var msg = messageInput.value;
        if(msg.trim() === '') return;
        
        ws.send("/app/chat/message", {}, JSON.stringify({type:'TALK', roomId:roomId, sender:sender, message:msg}));
        messageInput.value = '';
    }

    function recvMessage(recv) {
        var messages = document.getElementById('messages');
        var li = document.createElement('li');
        
        if (recv.type === 'ENTER' || recv.type === 'LEAVE') {
            li.innerHTML = '<div class="text-center text-muted my-2"><small>' + recv.message + '</small></div>';
        } else {
            if (recv.sender === sender) {
                li.innerHTML = '<div class="d-flex justify-content-end mb-2"><div class="bg-primary text-white p-2 rounded">' + recv.message + '</div></div>';
            } else {
                li.innerHTML = '<div class="d-flex justify-content-start mb-2"><div class="bg-light p-2 rounded"><strong>' + recv.sender + ':</strong> ' + recv.message + '</div></div>';
            }
        }
        
        messages.appendChild(li);
        
        // Scroll to bottom
        var chatArea = document.getElementById('chatArea');
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    function quit() {
        if(confirm("정말 채팅방에서 나가시겠습니까?")) {
            ws.send("/app/chat/message", {}, JSON.stringify({type:'LEAVE', roomId:roomId, sender:sender}));
            ws.disconnect();
            location.href = "/chat/rooms";
        }
    }
</script>

<%@ include file="../layouts/footer.jsp" %>
