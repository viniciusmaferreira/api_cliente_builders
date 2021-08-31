package br.com.builders.apicliente.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.builders.apicliente.domain.Cliente;
import br.com.builders.apicliente.dto.ClienteEdicaoDTO;
import br.com.builders.apicliente.exception.BusinessException;
import br.com.builders.apicliente.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Optional<Cliente> obterClientePorId(Long id) {

		return clienteRepository.findById(id);
	}

	public Optional<Cliente> obterClientePorCPF(String cpf) {

		return clienteRepository.obterClientePorCPF(cpf);
	}

	public void inserirCliente(Cliente cliente) throws Exception {
		
		if(cliente.getDataNascimento().isAfter(LocalDate.now()))throw new IllegalArgumentException("Data de nascimento com data futura!");
		Optional<Cliente> optional = clienteRepository.obterClientePorCPF(cliente.getCpf());
		if (!optional.isPresent()) {
			clienteRepository.save(cliente);
		} else {
			throw new BusinessException("CPF já cadastrado!");
		}
	}
	
	public void alterarCliente(ClienteEdicaoDTO cliente,String cpf) throws Exception {

		Optional<Cliente> optional = clienteRepository.obterClientePorCPF(cpf);
		if (optional.isPresent()) {
			Cliente clienteDB = optional.get();
			clienteDB.setNome(cliente.getNome());
			clienteDB.setEmail(cliente.getEmail());
			clienteDB.setDataNascimento(cliente.getDataNascimento());
			clienteRepository.save(clienteDB);
		} else {
			throw new BusinessException("Cliente com CPF informado não encontrado para edição!");
		}
	}

	
	public void removerCliente(Cliente cliente) {
		clienteRepository.delete(cliente);
	}

	public List<Cliente> listarClientes() {
		return (List<Cliente>) clienteRepository.findAll();
	}

}
