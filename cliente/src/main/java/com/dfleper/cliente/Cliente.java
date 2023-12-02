package com.dfleper.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@SpringBootApplication
public class Cliente {

	public static void main(String[] args) {
		SpringApplication.run(Cliente.class, args);

		final String SERVER_IP = "localhost";
		final int SERVER_PORT = 40080;

		try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

			System.out.println("Conexion establecida con el servidor...");

			while (true) {
				mostrarMenu();
				int opcion = Integer.parseInt(consoleInput.readLine());
				output.println(opcion);
				switch (opcion) {
				case 0:
					System.out.println("Desconectando el cliente...");
					return;
				case 1:
					String listaAerogeneradores = input.readLine();
					if (listaAerogeneradores.isEmpty()) {
						System.out.println("No hay aerogeneradores en el sistema.");
					} else {
						while (!listaAerogeneradores.isEmpty()) {
							System.out.println(listaAerogeneradores);
							listaAerogeneradores = input.readLine();
						}
					}
					break;
				case 2:
					System.out.print("Ingrese la ID del aerogenerador a activar:\n");
					int idActivar = Integer.parseInt(consoleInput.readLine());
					output.println(idActivar);

					String mensajeActivacion = input.readLine();
					System.out.println(mensajeActivacion);
					break;
				case 3:
					System.out.print("Ingrese la ID del aerogenerador a desactivar:\n");
					int idDesactivar = Integer.parseInt(consoleInput.readLine());
					output.println(idDesactivar);

					String mensajeDesactivacion = input.readLine();
					System.out.println(mensajeDesactivacion);
					break;
				case 4:
					System.out.println(input.readLine());
					break;
				case 5:
					System.out.print("Ingrese la ID del aerogenerador a eliminar:\n");
					int idEliminar = Integer.parseInt(consoleInput.readLine());
					output.println(idEliminar);

					String mensajeEliminacion = input.readLine();
					System.out.println(mensajeEliminacion);

					String confirmacion = input.readLine();
					System.out.println(confirmacion);
					break;
				default:
					System.out.println("Tecla no valida. Intentelo nuevamente.");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void mostrarMenu() {
		System.out.println("");
		System.out.println("----------");
		System.out.println("   Menu   ");
		System.out.println("----------");
		System.out.println("<1> Mostrar aerogeneradores");
		System.out.println("<2> Activar aerogenerador");
		System.out.println("<3> Desactivar aerogenerador");
		System.out.println("<4> Añadir aerogenerador");
		System.out.println("<5> Eliminar aerogenerador");
		System.out.println("<0> Salir");
		System.out.println("");
		System.out.print("Seleccione una opcion: ");
	}
}
