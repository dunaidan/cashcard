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
import java.security.Principal;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashCard> getCashCardById(@PathVariable("id") Long id, Principal principal) {
        CashCard cashCard = findByIdAndOwner(id, principal.getName());
        if (cashCard != null) {
            return ResponseEntity.ok(cashCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createCashCard(@RequestBody CashCard cashCard, UriComponentsBuilder uriBuilder, Principal principal) {
        CashCard cashCardWithOwner = new CashCard(null, cashCard.amount(), principal.getName());
        CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);

        URI locationOfNewCashCard = uriBuilder.path("/cashcards/{id}")
                .buildAndExpand(savedCashCard.id()).toUri();

        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping
    public ResponseEntity<Iterable<CashCard>> getAllCashCards(@SortDefault(sort = "amount", direction = Sort.Direction.DESC) Pageable pageable, Principal principal) {
        Page<CashCard> cashCardsPage = cashCardRepository.findAllByOwner(pageable, principal.getName());

        return ResponseEntity.ok(cashCardsPage.getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCashCard(@PathVariable("id") Long id, @RequestBody CashCard cashCard, Principal principal) {
        CashCard existingCashCard = findByIdAndOwner(id, principal.getName());
        if (existingCashCard != null) {
            CashCard updatedCashCard = new CashCard(existingCashCard.id(), cashCard.amount(), principal.getName());
            cashCardRepository.save(updatedCashCard);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCashCard(@PathVariable("id") Long id, Principal principal) {
        if (cashCardRepository.existsByIdAndOwner(id, principal.getName())) {
            cashCardRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CashCard findByIdAndOwner(Long id, String owner) {
        return cashCardRepository.findByIdAndOwner(id, owner);
    }
}
