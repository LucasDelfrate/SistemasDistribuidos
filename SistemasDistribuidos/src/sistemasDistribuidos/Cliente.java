package sistemasDistribuidos;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Informe o endereço IP do servidor: ");
		String serverAddress = scanner.nextLine();
		System.out.print("Informe a porta do servidor: ");
		int serverPort = scanner.nextInt();

		try {
			Socket socket = new Socket(serverAddress, serverPort);
			System.out.println("Conectado ao servidor");

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			String serverResponse = in.readLine();
			System.out.println("Servidor diz: " + serverResponse);

			while (true) {
				System.out.println("Menu:");
				System.out.println("1 - Cadastro");
				System.out.println("2 - Login");
				System.out.println("3 - Sair");
				System.out.print("Escolha uma opção: ");
				int choice = scanner.nextInt();

				out.println(Integer.toString(choice)); // 

				switch (choice) {
				case 1:
					System.out.print("Informe seu e-mail: ");
					String email = scanner.next();
					out.println(email);
					System.out.print("Informe sua senha: ");
					String password = scanner.next();
					out.println(password);
					break;
				case 2:
				    out.println("Opção de login escolhida.");
				    System.out.print("Informe o seu e-mail: ");
				    String loginEmail = scanner.next();
				    out.println(loginEmail);
				    System.out.print("Informe a sua senha: ");
				    String loginPassword = scanner.next();
				    out.println(loginPassword);
				    break;
				case 3:
					System.out.println("Desconectando...");
					socket.close();
					System.exit(0);
				default:
					System.out.println("Opção inválida. Tente novamente.");
				}

				String serverMessage = in.readLine();
				System.out.println("Servidor diz: " + serverMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
