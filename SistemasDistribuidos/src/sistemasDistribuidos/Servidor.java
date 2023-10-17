package sistemasDistribuidos;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Servidor {
	private static List<User> users = new ArrayList<>();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Informe a porta do servidor: ");
		int port = scanner.nextInt();

		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Servidor aguardando conexões na porta " + port);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

				Thread clientThread = new Thread(new ClientHandler(clientSocket));
				clientThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class ClientHandler implements Runnable {
		private Socket clientSocket;

		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		@Override
		public void run() {
			try {
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				out.println("Conexão estabelecida com o servidor.");

				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				String clientChoice = in.readLine();

				switch (clientChoice) {
				case "1":
					out.println("Opção de cadastro escolhida.");
					String email = in.readLine();
					String password = in.readLine();
					User newUser = new User(email, password);
					users.add(newUser);
					saveUsersToFile();
					out.println("Cadastro concluído com sucesso!");
					break;
				case "2":
					out.println("Opção de login escolhida.");
					String loginEmail = in.readLine();
					String loginPassword = in.readLine();

					if (authenticateUser(loginEmail, loginPassword)) {
						out.println("Login bem-sucedido. Cliente conectado!");
					} else {
						out.println("Login falhou. E-mail ou senha incorretos.");
					}
					break;

				default:
					out.println("Opção inválida. Tente novamente.");
				}

				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private static void saveUsersToFile() {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
				for (User user : users) {
					writer.write(user.getEmail() + "," + user.getPassword());
					writer.newLine();
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean authenticateUser(String email, String password) {
		for (User user : users) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
}
