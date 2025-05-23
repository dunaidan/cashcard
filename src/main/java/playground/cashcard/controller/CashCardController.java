package playground.cashcard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.cashcard.model.entity.CashCard;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    @GetMapping("/{id}")
    public ResponseEntity<CashCard>CashCardById(@PathVariable("id") Long id) {
        if (id == 99) {
            CashCard cashCard = new CashCard(99L, 0.0);
            return ResponseEntity.ok(cashCard);
        }
        return ResponseEntity.notFound().build();
    }
}
