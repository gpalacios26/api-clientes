package com.gregpalacios.clientes.services;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.gregpalacios.clientes.entities.Cliente;

public interface ClienteService {

	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public Cliente delete(Cliente cliente);
	
	public List<Cliente> findAllActivos();
	
	public DataTablesOutput<Cliente> findAll(DataTablesInput input);
}
