package playground.cashcard.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import playground.cashcard.model.entity.CashCard;
import playground.cashcard.repository.CashCardRepository;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    private CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashCard> getCashCardById(@PathVariable("id") Long id) {
        Optional<CashCard> cashCard = cashCardRepository.findById(id);
        if (cashCard.isPresent()) {
            return ResponseEntity.ok(cashCard.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createCashCard(@RequestBody CashCard cashCard, UriComponentsBuilder uriBuilder) {
        CashCard savedCashCard = cashCardRepository.save(cashCard);

        URI locationOfNewCashCard = uriBuilder.path("/cashcards/{id}")
                .buildAndExpand(savedCashCard.id()).toUri();

        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping
    public ResponseEntity<Iterable<CashCard>> getAllCashCards(@SortDefault(sort = "amount", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CashCard> cashCardsPage = cashCardRepository.findAll(pageable);

        return ResponseEntity.ok(cashCardsPage.getContent());
    }
}
