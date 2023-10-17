package sistemasDistribuidos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Conexão estabelecida com o servidor.");

            // Aqui você pode adicionar lógica para lidar com mensagens do cliente

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
