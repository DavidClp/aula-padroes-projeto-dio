package david.gof.repositoy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import david.gof.model.Cliente;
import david.gof.model.Venda;

@Repository
public interface VendaRepository extends CrudRepository<Venda, Long> {

}