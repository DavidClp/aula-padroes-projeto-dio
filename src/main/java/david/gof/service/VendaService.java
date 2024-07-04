package david.gof.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import david.gof.model.Cliente;
import david.gof.model.Venda;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de cliente. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 * 
 * @author david_clp
 */

public interface VendaService {

	Iterable<Venda> buscarTodos();

	Venda buscarPorId(Long id);

	void inserir(Venda venda);

	void atualizar(Long id, Venda venda);

	void deletar(Long id);

}
