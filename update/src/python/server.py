import socket
import threading
from threading import Lock

# Kullanıcı veritabanı ve kilit
data_lock = Lock()
users = []

def process_message(message):
    if message.startswith("KAYIT:"):
        user_info = message[6:].strip()
        with data_lock:
            users.append(user_info)
        return f"Kullanıcı başarıyla kaydedildi: {user_info}"
    elif message == "GÖRÜNTÜLE":
        with data_lock:
            return "Kayıtlı kullanıcılar: " + ", ".join(users)
    else:
        return "Geçersiz komut."

def handle_client(client_socket):
    with client_socket:
        while True:
            message = client_socket.recv(1024).decode()
            if not message:
                break
            response = process_message(message)
            client_socket.send(response.encode())

def start_server(port):
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(("localhost", port))
    server_socket.listen()
    print(f"Sunucu {port} portunda başlatıldı...")

    while True:
        client_socket, _ = server_socket.accept()
        threading.Thread(target=handle_client, args=(client_socket,)).start()

if __name__ == "__main__":
    start_server(8080)
