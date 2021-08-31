package br.com.builders.apicliente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.builders.apicliente.domain.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{

	@Query("SELECT c from Cliente c where c.cpf = :cpf")
	Optional<Cliente> obterClientePorCPF(String cpf);
}
