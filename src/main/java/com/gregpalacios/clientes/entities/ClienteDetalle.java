package com.gregpalacios.clientes.entities;

import java.io.Serializable;

public class ClienteDetalle implements Serializable {

	private static final long serialVersionUID = 946004357128146951L;
	
	private Cliente cliente;
	
	private String archivo;
	
	private String formato;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	@Override
	public String toString() {
		return "ClienteDetalle [cliente=" + cliente + ", archivo=" + archivo + ", formato=" + formato + "]";
	}
}
