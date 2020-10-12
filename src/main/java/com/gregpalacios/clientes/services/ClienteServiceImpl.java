package com.gregpalacios.clientes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import com.gregpalacios.clientes.repositories.ClienteRepository;

import com.gregpalacios.clientes.entities.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public Cliente findById(Long id) {
		return clienteRepository.findById(id).get();
	}

	@Override
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@Override
	public Cliente delete(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@Override
	public List<Cliente> findAllActivos() {
		return clienteRepository.findAllActivos();
	}
	
	@Override
	public DataTablesOutput<Cliente> findAll(DataTablesInput input) {
		return clienteRepository.findAll(input);
	}
}
