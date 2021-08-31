package br.com.builders.apicliente.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.builders.apicliente.domain.Cliente;
import br.com.builders.apicliente.dto.ClienteEdicaoDTO;
import br.com.builders.apicliente.service.ClienteService;

@RestController
@RequestMapping(value = "/api/v1/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService; 
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> inserirCliente(@Valid @RequestBody Cliente cliente) throws Exception {
		clienteService.inserirCliente(cliente);  
		Object body = "Cliente criado com sucesso!";
		return new ResponseEntity<Object>(body, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/cpf/{cpf}" ,method = RequestMethod.PUT)
	public ResponseEntity<Object> alterarCliente(@Valid @RequestBody ClienteEdicaoDTO cliente,@PathVariable String cpf) throws Exception{
		clienteService.alterarCliente(cliente,cpf);
		Object body = "Cliente atualizado com sucesso!";
		return new ResponseEntity<Object>(body, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/cpf/{cpf}",method = RequestMethod.DELETE)
	public ResponseEntity<?> removerCliente(@PathVariable String cpf) {
		
		  Optional<Cliente> optional = clienteService.obterClientePorCPF(cpf);
		
		  if(optional.isPresent()) {
			  	clienteService.removerCliente(optional.get());
			  	Object body = "Cliente removido com sucesso!";
				return new ResponseEntity<Object>(body, HttpStatus.OK);
		  }else {
			  Object body = "Cliente não encontrado!";
			  return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
		  }
	}
	
	@RequestMapping(value = "/cpf/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<?> obterClientePorCPF(@PathVariable String cpf) {
		
		Optional<Cliente> optionalCliente = clienteService.obterClientePorCPF(cpf);
		
		if(optionalCliente.isPresent()) {
			return ResponseEntity.ok().body(optionalCliente.get());
		}else {
			Object body = "Cliente não encontrado!";
			return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listarClientes() {
			List<Cliente> clientes = clienteService.listarClientes();
		if(clientes!=null && !clientes.isEmpty()) {
			return ResponseEntity.ok().body(clientes);
		}else {
			Object body = "Não existem clientes cadastrados!";
			return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
		}
	}
}
