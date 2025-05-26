package playground.cashcard.repository;

import org.springframework.stereotype.Repository;
import playground.cashcard.model.entity.CashCard;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface CashCardRepository extends CrudRepository<CashCard, Long> {
}