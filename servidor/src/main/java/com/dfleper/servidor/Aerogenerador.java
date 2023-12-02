package com.dfleper.servidor;

public class Aerogenerador {
	private int id;
	private boolean estado;
	private int velocidad;

	public Aerogenerador(int id, boolean activado, int velocidad) {
		this.id = id;
		this.estado = activado;
		this.velocidad = velocidad;
	}

	public int getId() {
		return id;
	}

	public boolean isActivado() {
		return estado;
	}

	public void setActivado(boolean activado) {
		this.estado = activado;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	@Override
	public String toString() {
		return "Aerogenerador: " + id + "| Estado: " + estado + "| Velocidad: " + velocidad + " m/s" + "\n";
	}
}
