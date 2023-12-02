package com.dfleper.servidor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParqueEolico {
	private List<Aerogenerador> aerogeneradores;

	public ParqueEolico() {
		this.aerogeneradores = new ArrayList<>();
		agregarAerogenerador();
	}

	public synchronized void conectarCliente() {
		System.out.println("Se ha conectado un cliente.");
	}

	public synchronized List<Aerogenerador> obtenerAerogeneradores() {
		if (aerogeneradores.isEmpty()) {
			return Collections.emptyList();
		} else {
			return new ArrayList<>(aerogeneradores);
		}
	}

	public synchronized void activarAerogenerador(int id) {
		for (Aerogenerador aerogenerador : aerogeneradores) {
			if (aerogenerador.getId() == id) {
				aerogenerador.setActivado(true);
				aerogenerador.setVelocidad(11);
				System.out.println("Aerogenerador " + id + " activado.");
				return;
			}
		}
		System.out.println("No se encontro un aerogenerador con el ID " + id);
	}

	public synchronized void desactivarAerogenerador(int id) {
		for (Aerogenerador aerogenerador : aerogeneradores) {
			if (aerogenerador.getId() == id) {
				aerogenerador.setActivado(false);
				aerogenerador.setVelocidad(0);
				System.out.println("Aerogenerador " + id + " desactivado.");
				return;
			}
		}
		System.out.println("No se encontro un aerogenerador con el ID " + id);
	}

	private int proximaId = 1;

	public synchronized void agregarAerogenerador() {
		Aerogenerador nuevoAerogenerador = new Aerogenerador(proximaId, false, 0);
		aerogeneradores.add(nuevoAerogenerador);
		proximaId++;
		System.out.println("Aerogenerador agregado: " + nuevoAerogenerador);
	}

	public synchronized void eliminarAerogenerador(int id) {
		aerogeneradores.removeIf(a -> a.getId() == id);
		System.out.println("Aerogenerador eliminado con ID " + id);
	}

	public synchronized Aerogenerador obtenerUltimoAerogeneradorAgregado() {
		if (!aerogeneradores.isEmpty()) {
			return aerogeneradores.get(aerogeneradores.size() - 1);
		}
		return null;
	}

	public synchronized Aerogenerador obtenerAerogeneradorPorId(int id) {
		for (Aerogenerador aerogenerador : aerogeneradores) {
			if (aerogenerador.getId() == id) {
				return aerogenerador;
			}
		}
		return null;
	}

	public synchronized int getNumeroAerogeneradores() {
		return aerogeneradores.size();
	}
}
