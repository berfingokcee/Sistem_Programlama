import socket

def start_client(port):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client_socket:
        client_socket.connect(("localhost", port))
        print("Bağlantı kuruldu. Komutlarınızı yazın:")
        while True:
            message = input("> ")
            client_socket.send(message.encode())
            response = client_socket.recv(1024).decode()
            print(response)

if __name__ == "__main__":
    start_client(8080)
