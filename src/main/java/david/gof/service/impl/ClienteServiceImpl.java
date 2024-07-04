package david.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import david.gof.model.Cliente;
import david.gof.model.Endereco;
import david.gof.repositoy.ClienteRepository;
import david.gof.repositoy.EnderecoRepository;
import david.gof.service.ClienteService;
import david.gof.service.ViaCepService;

/**
 * Implementação da Strategy {@link ClienteService}, a qual pode ser 
 * inketa pelo Spring (via {@link Autowired}). com isso, como essa classe é i,
 * {@link service}, ela será tratada como um Singleton
 * 
 * @author DavidClp
 */

@Service
public class ClienteServiceImpl  implements ClienteService{
	 @Autowired
	 private ClienteRepository clienteRepository;
	 @Autowired
	 private EnderecoRepository enderecoRepository;
	 @Autowired
	 private ViaCepService viaCepService;
	
	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
	    Optional<Cliente> cliente =	clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void inserir(Cliente cliente) {
		Endereco endereco = verificarEnderecoByCep(cliente);
		
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}


	@Override
	public void atualizar(Long id, Cliente cliente) {
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if(clienteBd.isPresent()) {
			Endereco endereco = verificarEnderecoByCep(cliente);
			
			cliente.setEndereco(endereco);
			clienteRepository.save(cliente);
		}
	}

	@Override
	public void deletar(Long id) {
		clienteRepository.deleteById(id);
	}
	
	private Endereco verificarEnderecoByCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();

		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		
		return endereco;
	}
}
