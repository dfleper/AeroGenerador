package com.dfleper.servidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

@SpringBootApplication
public class Servidor {

	public static void main(String[] args) {
		SpringApplication.run(Servidor.class, args);

		ParqueEolico parqueEolico = new ParqueEolico();

		try (ServerSocket serverSocket = new ServerSocket(40080)) {
			System.out.println("Servidor en espera de conexiones...");

			while (true) {
				Socket socket = serverSocket.accept();
				parqueEolico.conectarCliente();
				Thread clienteHandlerThread = new Thread(() -> manejarCliente(socket, parqueEolico));
				clienteHandlerThread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void manejarCliente(Socket socket, ParqueEolico parqueEolico) {
		try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

			while (true) {
				int opcion = Integer.parseInt(input.readLine());
				switch (opcion) {
				case 1:
					List<Aerogenerador> aerogeneradores = parqueEolico.obtenerAerogeneradores();
					if (aerogeneradores.isEmpty()) {
						output.println("No hay aerogeneradores en el sistema.");
					} else {
						StringBuilder resultado = new StringBuilder();
						int numeroAerogeneradores = parqueEolico.getNumeroAerogeneradores();
						resultado.append("Numero total de aerogeneradores: ").append(numeroAerogeneradores)
								.append("\n");
						for (Aerogenerador aerogenerador : aerogeneradores) {
							resultado.append("Aerogenerador: ").append(aerogenerador.getId()).append("| Estado: ")
									.append(aerogenerador.isActivado()).append("| Velocidad: ")
									.append(aerogenerador.getVelocidad()).append(" m/s").append("\n");
						}
						output.println(resultado.toString());
					}
					break;
				case 2:
					int idActivar = Integer.parseInt(input.readLine());
					parqueEolico.activarAerogenerador(idActivar);
					output.println("Aerogenerador activado");
					break;
				case 3:
					int idDesactivar = Integer.parseInt(input.readLine());
					parqueEolico.desactivarAerogenerador(idDesactivar);
					output.println("Aerogenerador desactivado");
					break;
				case 4:
					parqueEolico.agregarAerogenerador();
					Aerogenerador ultimoAerogeneradorAgregado = parqueEolico.obtenerUltimoAerogeneradorAgregado();
					output.println("Aerogenerador añadido: " + "ID: " + ultimoAerogeneradorAgregado.getId()
							+ " | Estado: " + ultimoAerogeneradorAgregado.isActivado() + " | Velocidad: "
							+ ultimoAerogeneradorAgregado.getVelocidad() + " m/s");
					break;

				case 5:
					int idEliminar = Integer.parseInt(input.readLine());
					Aerogenerador aerogeneradorEliminar = parqueEolico.obtenerAerogeneradorPorId(idEliminar);

					if (aerogeneradorEliminar != null) {
						output.println("Aerogenerador eliminado: " + "ID: " + aerogeneradorEliminar.getId()
								+ " | Estado: " + aerogeneradorEliminar.isActivado() + " | Velocidad: "
								+ aerogeneradorEliminar.getVelocidad() + " m/s");

						parqueEolico.eliminarAerogenerador(idEliminar);
						output.println("Eliminacion Confirmada");
					} else {
						output.println("No se encontro un aerogenerador con el ID " + idEliminar);
					}
					break;
				case 0:
					System.out.println("Cliente desconectado...");
					return;
				default:
					System.out.println("Opcion no valida.");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
