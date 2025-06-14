package playground.cashcard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import playground.cashcard.model.entity.CashCard;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface CashCardRepository extends CrudRepository<CashCard, Long>, PagingAndSortingRepository<CashCard, Long> {

    CashCard findByIdAndOwner(Long id, String owner);

    Page<CashCard> findAllByOwner(Pageable pageable, String owner);

    Boolean existsByIdAndOwner(Long id, String owner);
}