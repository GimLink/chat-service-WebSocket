const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/stomp/chats'
});

stompClient.onConnect = (frame) => {
  setConnected(true);
  showChatRooms();
  stompClient.subscribe('/sub/chats/news',
      (chatMessage) => {
        toggleNewMessageIcon(JSON.parse(chatMessage.body), true)
      });
  console.log('Connected: ' + frame);
  stompClient.subscribe('/sub/chats', (chatMessage) => {
    showMessage(JSON.parse(chatMessage.body));
  });
  stompClient.publish({
    destination: "/pub/chats",
    body: JSON.stringify(
        {'sender': $("#username").val(), 'message': "connected"})
  })
};

function toggleNewMessageIcon(chatRoomId, toggle) {
  if (toggle){
    $("#new_" + chatRoomId).show();
  }else{
    $("#new_" + chatRoomId).hide();
  }
}

stompClient.onWebSocketError = (error) => {
  console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
  console.error('Broker reported error: ' + frame.headers['message']);
  console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  $("#create").prop("disabled", !connected);
}

// if (connected) {
//   $("#conversation").show();
// } else {
//   $("#conversation").hide();
// }
// $("#messages").html("");

function connect() {
  stompClient.activate();
}

function disconnect() {
  stompClient.deactivate();
  setConnected(false);
  console.log("Disconnected");
}

function sendMessage() {
  let chatRoomId = $("#chatroom-id").val();
  stompClient.publish({
    destination: "/pub/chats/" + chatRoomId,
    body: JSON.stringify(
        {'message': $("#message").val()})
  });
  $("#message").val("")
}

function showMessage(chatMessage) {
  $("#messages").append(
      "<tr><td>" + chatMessage.sender + " : " + chatMessage.message
      + "</td></tr>");
}

function createChatRoom() {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: '/chats?title=' + $("#chatroom-title").val(),
    success: function(data) {
      console.log('data: ', data);
      showChatRooms();
      enterChatRoom(data.id, true);
    },
    error: function(request, status, error) {
      console.log('request: ', request);
      console.log('error: ', error);
    }
  })
}

function showChatRooms() {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: '/chats',
    success: function(data) {
      console.log('data: ', data);
      renderChatRoom(data);
    },
    error: function (request, status, error) {
      console.log('request: ', request);
      console.log('error: ', error);
    }
  })
}

function renderChatRoom(chatrooms) {
  $("#chatroom-list").html("");
  for (let i = 0; i < chatrooms.length; i++) {
    $("#chatroom-list").append(
        "<tr onclick= 'joinChatRoom(" + chatrooms[i].id + ")'><td>"
        +chatrooms[i].id + "</td><td>" + chatrooms[i].title + "<img src='new.png' id='new_" + chatrooms[i].id +"' style='display: "
        + getDisplayValue(chatrooms[i].hasNewMessage) + "'</td><td>"
        +chatrooms[i].memberCount + "</td><td>" + chatrooms[i].createdAt
        + "</td></tr>"
    );
  }
}

function getDisplayValue(hasNewMessage) {
  if (hasNewMessage) {
    return "inline";
  }

  return "none";
}

let subscription;

function enterChatRoom(chatRoomId, newMember) {
  $("#chatroom-id").val(chatRoomId);
  $("#messages").html("");
  showMessages(chatRoomId);
  $("#conversation").show();
  $("#send").prop("disabled", false);
  $("#leave").prop("disabled", false);
  toggleNewMessageIcon(chatRoomId, false);

  if (subscription != undefined) {
    subscription.unsubscribe();
  }

  subscription = stompClient.subscribe('/sub/chats/' + chatRoomId, (chatMessage) => {
    showMessage(JSON.parse(chatMessage.body));
  });

  if (newMember) {
    stompClient.publish({
      destination: "/pub/chats/" + chatRoomId,
      body: JSON.stringify(
          {'message': "님이 방에 들어왔습니다."}
      )
    })
  }
}

function showMessages(chatRoomId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: '/chats/' + chatRoomId + '/messages',
    success: function(data) {
      console.log('data: ', data);
      for (let i = 0; i < data.length; i++) {
        showMessage(data[i]);
      }
    },
    error: function(request, status, error) {
      console.log('request: ', request);
      console.log('error: ', error);
    }
  })
}

function joinChatRoom(chatRoomId) {
  let currentChatRoomId = $("#chatroom-id").val();

  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: '/chats/' + chatRoomId + getRequestParam(currentChatRoomId),
    success: function(data) {
      console.log('data: ', data)
      enterChatRoom(chatRoomId, data);
    },
    error: function (request, status, error) {
      console.log('request: ', request);
      console.log('error: ', error);
    }
  })
}

function getRequestParam(currentChatRoomId) {
  if (currentChatRoomId == "") {
    return "";
  }
  return "?currentChatRoomId=" + currentChatRoomId;
}
function leaveChatRoom() {
  let chatRoomId = $("#chatroom-id").val();
  $.ajax({
    type: 'DELETE',
    dataType: 'json',
    url: '/chats/' + chatRoomId,
    success: function(data){
      console.log('data: ', data);
      showChatRooms()
      exitChatRoom(chatRoomId);
    },
    error: function (request, status, error) {
      console.log('request: ', request);
      console.log('error: ', error);
    }
  })
}

function exitChatRoom(chatRoomId){
  $("#chatroom-id").val("");
  $("#conversation").hide();
  $("#send").prop("disabled", true);
  $("#leave").prop("disabled", true);

}

$(function () {
  $("form").on('submit', (e) => e.preventDefault());
  $("#connect").click(() => connect());
  $("#disconnect").click(() => disconnect());
  $("#create").click(() => createChatRoom());
  $("#leave").click(() => leaveChatRoom());
  $("#send").click(() => sendMessage());
});