package playground.cashcard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.cashcard.model.entity.CashCard;
import playground.cashcard.repository.CashCardRepository;

import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    private CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashCard>getCashCardById(@PathVariable("id") Long id) {
        Optional<CashCard> cashCard = cashCardRepository.findById(id);
        if (cashCard.isPresent()) {
            return ResponseEntity.ok(cashCard.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
