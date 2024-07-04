package david.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import david.gof.model.Produto;
import david.gof.repositoy.ProdutoRepository;
import david.gof.service.ProdutoService;

@Service
public class ProdutoServiceImpl  implements ProdutoService{
	 @Autowired
	 private ProdutoRepository produtoRepository;
	
	@Override
	public Iterable<Produto> buscarTodos() {
		return produtoRepository.findAll();
	}

	@Override
	public Produto buscarPorId(Long id) {
	    Optional<Produto> produto =	produtoRepository.findById(id);
		return produto.get();
	}

	@Override
	public void inserir(Produto cliente) {
		produtoRepository.save(cliente);
	}

	@Override
	public void atualizar(Long id, Produto produto) {
		Optional<Produto> clienteBd = produtoRepository.findById(id);
		if(clienteBd.isPresent()) {
			produtoRepository.save(produto);
		}
	}

	@Override
	public void deletar(Long id) {
		produtoRepository.deleteById(id);
	}
}
