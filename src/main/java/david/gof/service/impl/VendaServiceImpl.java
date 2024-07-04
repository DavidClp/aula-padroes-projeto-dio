package david.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import david.gof.model.Endereco;
import david.gof.model.Produto;
import david.gof.model.Venda;
import david.gof.repositoy.EnderecoRepository;
import david.gof.repositoy.ProdutoRepository;
import david.gof.repositoy.VendaRepository;
import david.gof.service.ClienteService;
import david.gof.service.VendaService;
import david.gof.service.ViaCepService;

/**
 * Implementação da Strategy {@link ClienteService}, a qual pode ser 
 * inketa pelo Spring (via {@link Autowired}). com isso, como essa classe é i,
 * {@link service}, ela será tratada como um Singleton
 * 
 * @author DavidClp
 */

@Service
public class VendaServiceImpl  implements VendaService{
	 @Autowired
	 private VendaRepository vendaRepository;
	 @Autowired
	 private ProdutoRepository produtoRepository;
	 @Autowired
	 private EnderecoRepository enderecoRepository;
	 @Autowired
	 private ViaCepService viaCepService;
	
	@Override
	public Iterable<Venda> buscarTodos() {
		return vendaRepository.findAll();
	}

	@Override
	public Venda buscarPorId(Long id) {
	    Optional<Venda> venda = vendaRepository.findById(id);
		return venda.get();
	}
	
	@Override
	public void inserir(Venda venda) {
		Endereco enderecoEntrega = verificarEnderecoByCep(venda);
		//Endereco produto = verificaExisteProduto(venda);
		
		venda.setEnderecoEntrega(enderecoEntrega);
		vendaRepository.save(venda);
	}


	@Override
	public void atualizar(Long id, Venda venda) {
		Optional<Venda> produtoBd = vendaRepository.findById(id);
		if(produtoBd.isPresent()) {
			Endereco endereco = verificarEnderecoByCep(venda);
			
			venda.setEnderecoEntrega(endereco);
			vendaRepository.save(venda);
		}
	}

	@Override
	public void deletar(Long id) {
		vendaRepository.deleteById(id);
	}
	
	private Endereco verificarEnderecoByCep(Venda venda) {
		String cep = venda.getEnderecoEntrega().getCep();

		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		
		return endereco;
	}

	// TODO criar produto caso não exista
//	private Endereco verificaExisteProduto(Venda venda) {
//		Long proId = venda.getProduto().getId();
//		
//		Produto produto = produtoRepository.findById(proId).orElseGet(() -> {
//			Produto novoProduto = viaCepService.consultarCep(proId);
//			produtoRepository.save(novoProduto);
//			return novoProduto;
//		});
//		
//		return produto;
//	}
}
