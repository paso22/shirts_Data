package ge.bog.ShirtInfo.ShirtData.controller;

import ge.bog.ShirtInfo.ShirtData.dto.PurchaseNumericDataDto;
import ge.bog.ShirtInfo.ShirtData.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/get/numericData/{team}/{type}/{isHome}/{season}/{hasNameNumber}/{quantity}/{wallet}")
    public ResponseEntity<PurchaseNumericDataDto> validateAndMakePurchase(@PathVariable String team, @PathVariable String type,
                                                           @PathVariable String isHome, @PathVariable String season,
                                                           @PathVariable String hasNameNumber, @PathVariable Integer quantity,
                                                           @PathVariable Integer wallet) {
        PurchaseNumericDataDto res = purchaseService.getNumericData(team, type, isHome, season, hasNameNumber, quantity, wallet);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
