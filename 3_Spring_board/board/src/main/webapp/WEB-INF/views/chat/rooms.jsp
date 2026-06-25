<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layouts/header.jsp" %>
<div class="container my-5">
    <h2>채팅방 목록</h2>
    
    <div class="input-group mb-3">
        <input type="text" id="room_name" class="form-control" placeholder="채팅방 이름">
        <button class="btn btn-primary" type="button" onclick="createRoom()">채팅방 개설</button>
    </div>

    <ul class="list-group" id="room_list">
        <!-- 목록이 여기에 표시됩니다 -->
    </ul>
</div>

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        findAllRooms();
    });

    function findAllRooms() {
        axios.get('/chat/rooms/list').then(response => {
            let roomList = document.getElementById('room_list');
            roomList.innerHTML = '';
            response.data.forEach(room => {
                let li = document.createElement('li');
                li.className = 'list-group-item list-group-item-action d-flex justify-content-between align-items-center';
                li.style.cursor = 'pointer';
                li.onclick = function() {
                    enterRoom(room.roomId);
                };
                li.innerText = room.name;
                roomList.appendChild(li);
            });
        });
    }

    function createRoom() {
        let name = document.getElementById('room_name').value;
        if(name === "") {
            alert("방 제목을 입력해 주십시요.");
            return;
        }
        
        let params = new URLSearchParams();
        params.append('name', name);
        
        axios.post('/chat/room', params)
            .then(response => {
                alert(response.data.name + "방 개설에 성공하였습니다.");
                document.getElementById('room_name').value = '';
                findAllRooms();
            })
            .catch(response => {
                alert("채팅방 개설에 실패하였습니다.");
            });
    }

    function enterRoom(roomId) {
        let sender = prompt('대화명을 입력해 주세요.');
        if(sender !== null && sender.trim() !== '') {
            localStorage.setItem('wschat.sender', sender);
            localStorage.setItem('wschat.roomId', roomId);
            location.href = "/chat/room/enter/" + roomId;
        }
    }
</script>

<%@ include file="../layouts/footer.jsp" %>
