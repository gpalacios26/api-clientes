package com.gregpalacios.clientes.repositories;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

import com.gregpalacios.clientes.entities.Cliente;

public interface ClienteRepository extends DataTablesRepository<Cliente, Long>{

	@Query("SELECT c FROM Cliente c WHERE c.flg_status=1")
	List<Cliente> findAllActivos();
}
