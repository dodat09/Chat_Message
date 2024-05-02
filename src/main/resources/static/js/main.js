/**
 * 
 
'use strict';
var usernamePage = document.querySelector("#username-page");
var usernameForm = document.querySelector("#usernameForm");
var chatPage = document.querySelector("#chat-page");
var connectingElement = document.querySelector('.connecting');
var chatArea= document.querySelector("#chat-messages");
var messageForm = document.querySelector("#messageForm");
var logout = document.querySelector("#logout");
var messageInput = document.querySelector("message");
let stompClient = null;
let nickName = null;
let fullName = null;
let selectedUserId = null;

function connect(event){
	nickName = document.querySelector("#nickname").value.trim();
	fullName = document.querySelector("#fullname").value.trim();
	if(nickName && fullName){
		usernamePage.classList.add('hidden');
		chatPage.classList.remove('hidden');
		var sockJs = new SockJs("/ws");
		stompClient = Stomp.over(sockJs);
		stompClient.connect({},onconnected,errorConect);
	}
	event.preventDefault();
	}
function onconnected(){
	stompClient.subscribe("/user/${nickName}/queue/messages",onMessageRecieve);
	stompClient.subscribe("/user/public",onMessageRecieve);
	
	stompClient.send("/app/user.addUser",
	             {},
	             JSON.stringify({nickName : nickName,
	                               fullName : fullName,
	                               status : ONLINE}));
  findAndDipslayConnectedUsers().then();
}

async function findAndDisplayConnectedUsers(){
	var connectedUserResponse = await fetch("/users");
	let connectedUsers = connectedUserResponse.json();
	connectedUsers = connectedUsers.fillter(user => user.nickName != nickName);
	var connectedUserList = document.getElementById("connectedUsers");
	connectedUserList.innerHTML = '';
	connectedUsers.forEach(user =>{
		appendUserElement(user,connectedUserList);
		if(connectedUsers.indexOf(user) <connectedUsers.length-1){
			const separator = document.createElement('li');
		    separator.classList.add('separator');
			connectedUserList.appendChild(separator);
		}	 
	});}
function appendUserElement(user,connectedUserList){
	const listItem = document.createElement('li');
	listItem.classList.add('user-item');
	listItem.id = user.nickName;
	
	var image = document.createElement('img');
	image.src = " ";
	image.alt = user.fullName;
	
	var usernameSpan = document.createElemtn('span');
	usernameSpan.textContent = user.fullNaem;
	
	var recieveMgs = document.createElemtn('spam');
	recieveMgs.textContent='';
	recieveMgs.classList.add('nbr-msg','hidden');
	
	listItem.appendChild(image);
	listItem.appendChild(usernameSpan);
	listItem.appendChild(recieveMgs);
	
	listItem.addEventListener('click',userItemClick);
	connectedUserList.appendChild(listItem);
	
}	

function userItemClick(event){
	document.querySelectionAll('.user-item').forEach(item =>{
		item.classList.remove('active');
	})
	messageForm.classList.remove('hidden');
	var clickUser =event.currentTarget;
	clickUser.classList.add('active');
	
	selectedUserId = clickUser.getAttribute('id');
	fetchAndDisplayUserChat().then();
	
	var nbrMsg = clickUser.querySelector('.nbr-msg');
	nbrMsg.classList.add('hidden');
	nbrMsg.textContent = '0';
	
}

function displayMessage(senderId, content){
	var messageContainer = document.createElement('div');
	messageContainer.classList.add('mesage');
	if(nickName===senderId){
		messageContainer.classList.add('sender');
	}else{
		messageContainer.classList.add('reciver');
	}
	var message = document.createElement('p');
	message.textContent = content;
	messageContainer.appendChild(message);
	chatArea.appendChild(messageContainer);
}
async function fetchAnDisplayUserChat(){
	var listMessage = await fetch('/messages/${senderId}/${selectedUserId}');
	let chat = listMessage.json();
	chatArea.innerHTML='';
	chat.forEach(c =>{
		displayMessage(c.senderId,c.content);
	})
	chatArea.scrollTop = chatArea.scrollHeight;
	
}

function errorConect(){
	connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}
function sendMessage(event){
	var message = messageInput.value.trim();
	if(message && stompClient){
		var chatMessage = {
			senderId : nickName,
			recipientId : selectedUserId,
			content : messageInput.value.trim(),
			timeChat : new Date()
		};
	
	stomClient.send('/app/chat',{},JSON.stringtify(chatMessage));
	displayMessage(nickName,messageInput.value.trim());
	messageInput.innerHTML=' ';}
	chatArea.scrollTop = chatArea.scrollHeight;
	event.preventDefault();
}

async function onMessageRecieve(payload){
	await findAndDisplayConnectedUsers();
	var message = JSON.parse(payload.body);
	if(selectedUserId && selectedUserId === message.senderId){
		displayMessage(message.senderId,message.content);
		chatArea.scrollTop = chatArea.scrollHeight;
	}
	if(selectedUserId){
		document.querySelector('#${selectedUserId}').classList.add('active');
	}
	else{
		messageForm.classList.add('hidden');
	}
	var notifiedUser = document.querySelector('#${selectedUserId}').classList.add('active');
	if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
	
}

function onLogout() {
    stompClient.send("/app/user.disconnectUser",
        {},
        JSON.stringify({nickName: nickname, fullName: fullname, status: 'OFFLINE'})
    );
    window.location.reload();
}
usernameForm.addEventListener('submit', connect, true); // step 1
messageForm.addEventListener('submit', sendMessage, true);
logout.addEventListener('click', onLogout, true);*/


'use strict';

const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector('#chat-messages');
const logout = document.querySelector('#logout');

let stompClient = null;
let nickname = null;
let fullname = null;
let selectedUserId = null;
let nameTopic = null;

function connect(event) {
    nickname = document.querySelector('#nickname').value.trim();
    fullname = document.querySelector('#fullname').value.trim();

    if (nickname && fullname) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    stompClient.subscribe(`/user/${nickname}`, onMessageReceived);
   
    stompClient.subscribe(`/user/public`, onMessageReceived);

    // register the connected user
    stompClient.send("/app/user.addUser",
        {},
        JSON.stringify({nickName: nickname, fullName: fullname, status: 'ONLINE'})
    );
    document.querySelector('#connected-user-fullname').textContent = fullname;
    findAndDisplayConnectedUsers().then();
}

async function findAndDisplayConnectedUsers() {
    const connectedUsersResponse = await fetch('/connected');
    let connectedUsers = await connectedUsersResponse.json();
  //  connectedUsers = connectedUsers.filter(user => user.nickName !== nickname);
    const connectedUsersList = document.getElementById('connectedUsers');
    connectedUsersList.innerHTML = '';

    connectedUsers.forEach(user => {
        appendUserElement(user, connectedUsersList);
        if (connectedUsers.indexOf(user) < connectedUsers.length - 1) {
            const separator = document.createElement('li');
            separator.classList.add('separator');
            connectedUsersList.appendChild(separator);
        }
    });
}

function appendUserElement(user, connectedUsersList) {
    const listItem = document.createElement('li');
    listItem.classList.add('user-item');
   // listItem.id = user.nickName;
    if(user.chatId==null){
		listItem.id= user.nameTopic;
	}else{
		listItem.id=user.chatId;
	}


    const userImage = document.createElement('img');
    userImage.src = '../img/user_icon.png';
   // userImage.alt = user.fullName;
    if(user.recipientId==null){
		userImage.alt=user.nameTopic;
	}else{
		userImage.alt=user.recipientId;
	}

    const usernameSpan = document.createElement('span');
    //usernameSpan.textContent = user.fullName;
    if(user.recipientId==null){
		userSpan.alt=user.nameTopic;
	}else{
		userSpan.alt=user.recipientId;
	}
    const receivedMsgs = document.createElement('span');
    receivedMsgs.textContent = '0';
    receivedMsgs.classList.add('nbr-msg', 'hidden');

    listItem.appendChild(userImage);
    listItem.appendChild(usernameSpan);
    listItem.appendChild(receivedMsgs);

    listItem.addEventListener('click', userItemClick);

    connectedUsersList.appendChild(listItem);
}

function userItemClick(event) {
    document.querySelectorAll('.user-item').forEach(item => {
        item.classList.remove('active');
    });
    messageForm.classList.remove('hidden');

    const clickedUser = event.currentTarget;
    clickedUser.classList.add('active');

    chatId = clickedUser.getAttribute('id');
    fetchAndDisplayUserChat().then();

    const nbrMsg = clickedUser.querySelector('.nbr-msg');
    nbrMsg.classList.add('hidden');
    nbrMsg.textContent = '0';

}

function displayMessage(senderId, content) {
    const messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    if (senderId === nickname) {
        messageContainer.classList.add('sender');
    } else {
        messageContainer.classList.add('receiver');
    }
    const message = document.createElement('p');
    message.textContent = content;
    messageContainer.appendChild(message);
    chatArea.appendChild(messageContainer);
}

async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`/messages/${chatId}`);
    const userChat = await userChatResponse.json();
    chatArea.innerHTML = '';
    userChat.forEach(chat => {
		// kiêm tra xem chatId là chữ hay số nếu là chữ thì là chatIdTopic
		//tương đương với chatMessageTopic
		if(isDigit(chatId)){
			displayMessage(chat.userId, chat.content);
		}else{// ngược lại thì là chatIdRoom tương đương với chatMessage
		
			displayMesssage(chat.senderId,chat.content);
		}
        
    });
    chatArea.scrollTop = chatArea.scrollHeight;
}


function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            senderId: nickname,
            recipientId: selectedUserId,
            content: messageInput.value.trim(),
            timeChat: new Date()
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        displayMessage(nickname, messageInput.value.trim());
        messageInput.value = '';
    }
    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}


async function onMessageReceived(payload) {
    await findAndDisplayConnectedUsers();
    console.log('Message received', payload);
    const message = JSON.parse(payload.body);
    if (selectedUserId && selectedUserId === message.senderId) {
        displayMessage(message.senderId, message.content);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    if (selectedUserId) {
        document.querySelector(`#${selectedUserId}`).classList.add('active');
    } else {
        messageForm.classList.add('hidden');
    }

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
}

function onLogout() {
    stompClient.send("/app/user.disconnectUser",
        {},
        JSON.stringify({nickName: nickname, fullName: fullname, status: 'OFFLINE'})
    );
    window.location.reload();
}

usernameForm.addEventListener('submit', connect, true); // step 1
messageForm.addEventListener('submit', sendMessage, true);
logout.addEventListener('click', onLogout, true);
window.onbeforeunload = () => onLogout();
