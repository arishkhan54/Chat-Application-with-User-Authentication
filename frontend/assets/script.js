const API = "http://localhost:8080/api/auth";
let stompClient = null;

document.addEventListener("DOMContentLoaded", () => {

    if (document.getElementById("loginBtn"))
        document.getElementById("loginBtn").addEventListener("click", login);

    if (document.getElementById("registerBtn"))
        document.getElementById("registerBtn").addEventListener("click", registerUser);

    if (window.location.pathname.includes("chat.html")) {
        connectWebSocket();
        document.getElementById("sendBtn").addEventListener("click", sendMessage);
        document.getElementById("sendPrivateBtn").addEventListener("click", sendPrivateMessage);
    }
});


async function login() {
    const username = document.getElementById("loginUsername").value;
    const password = document.getElementById("loginPassword").value;

    const response = await fetch(`${API}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    });

    const token = await response.text();
    if (!response.ok) return alert("Invalid login");

    localStorage.setItem("token", token);
    localStorage.setItem("username", username);

    window.location.href = "chat.html";
}


async function registerUser() {
    const username = document.getElementById("regUsername").value;
    const password = document.getElementById("regPassword").value;

    const response = await fetch(`${API}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    });

    if (response.ok) window.location.href = "login.html";
    else alert("Register failed");
}


function connectWebSocket() {
    const socket = new SockJS("http://localhost:8080/ws");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {

        stompClient.subscribe("/topic/messages", msg => {
            const m = JSON.parse(msg.body);
            appendMessage(m.sender, m.content);
        });

        stompClient.subscribe("/user/" + localStorage.getItem("username") + "/queue/messages", msg => {
            const m = JSON.parse(msg.body);
            appendPrivateMessage(m.sender, m.content);
        });

        stompClient.subscribe("/topic/users", msg => {
            updateOnlineList(JSON.parse(msg.body));
        });

        stompClient.send("/app/addUser", {}, localStorage.getItem("username"));
    });
}


function updateOnlineList(users) {
    const list = document.getElementById("usersList");
    list.innerHTML = "";

    users.forEach(u => {
        const div = document.createElement("div");
        div.classList.add("user-item");
        div.innerText = u;

        div.onclick = () => {
            document.getElementById("privateReceiver").value = u;
        };

        list.appendChild(div);
    });
}


function appendMessage(sender, content) {
    const box = document.getElementById("chatBox");
    const div = document.createElement("div");

    div.classList.add("message");
    if (sender === localStorage.getItem("username")) div.classList.add("self");

    div.innerHTML = `<strong>${sender}:</strong> ${content}`;
    box.appendChild(div);
    box.scrollTop = box.scrollHeight;
}


function appendPrivateMessage(sender, content) {
    const box = document.getElementById("chatBox");
    const div = document.createElement("div");

    div.classList.add("message");
    div.style.background = "#ff0066";
    div.innerHTML = `<strong>PRIVATE from ${sender}:</strong> ${content}`;

    box.appendChild(div);
    box.scrollTop = box.scrollHeight;
}


function sendMessage() {
    const content = document.getElementById("messageInput").value.trim();
    const sender = localStorage.getItem("username");

    if (!content) return;

    stompClient.send("/app/chat", {}, JSON.stringify({ sender, receiver: "all", content }));
    appendMessage("You", content);

    document.getElementById("messageInput").value = "";
}


function sendPrivateMessage() {
    const receiver = document.getElementById("privateReceiver").value.trim();
    const content = document.getElementById("privateMessage").value.trim();
    const sender = localStorage.getItem("username");

    if (!receiver || !content) return;

    stompClient.send("/app/private-message", {}, JSON.stringify({ sender, receiver, content }));

    appendPrivateMessage("You → " + receiver, content);
    document.getElementById("privateMessage").value = "";
}
