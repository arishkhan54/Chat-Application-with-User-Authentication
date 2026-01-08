# 💬 Real-Time Chat Application with User Authentication

A secure, scalable, and real-time chat application built using **Java**, **Spring Boot**, **WebSockets**, **HTML**, **CSS**, and **JavaScript**.  
Users can register, log in, send private messages, join chat rooms, and chat in real time with authentication and authorization using **JWT**.

---

## 🚀 Features

### 🔐 User Authentication
- User registration & login
- JWT-based token authentication
- Password hashing & secure login
- Role-based access control (User/Admin)

### 💬 Real-Time Chat
- 1-to-1 private messaging
- Chat room functionality
- Real-time message delivery using WebSockets
- Online/offline status tracking

### 🖥️ Responsive Frontend
- Built with HTML, CSS, JavaScript
- Fully responsive UI
- Smooth chat interface
- Notification alert for new messages

### 🛠 Backend (Spring Boot)
- REST APIs for authentication & messaging
- WebSocket/STOMP for real-time communication
- Spring Security for authorization
- Service + Repository layered architecture

### 🗄 Database
- MySQL database
- JPA + Hibernate ORM
- User, Message, ChatRoom tables
- Stores chat history with timestamps

---

## 🛠️ Tech Stack

### Frontend
- HTML  
- CSS  
- JavaScript  

### Backend
- Java 17+  
- Spring Boot  
- Spring Security  
- Spring WebSocket  
- JWT Authentication  
- JPA + Hibernate  

### Database
- MySQL  

---

## 📡 API Endpoints

### 🔐 Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and get JWT token |

### 💬 Messaging
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/messages/{userId}` | Fetch chat history |
| POST | `/api/messages/send` | Send a private message |

---

## 🧩 WebSocket Endpoints

| Endpoint | Description |
|----------|-------------|
| `/ws` | WebSocket connection endpoint |
| `/topic/messages` | Broadcast messages to chat rooms |
| `/queue/messages` | Send private messages to specific user |

---
