package ge.bog.ShirtInfo.ShirtData.controller;

import ge.bog.ShirtInfo.ShirtData.entity.PasoShopShirts;
import ge.bog.ShirtInfo.ShirtData.service.ShirtInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("ShirtInfo")
public class ShirtInfoController {

    private final ShirtInfoService shirtInfoService;

    public ShirtInfoController(ShirtInfoService shirtInfoService) {
        this.shirtInfoService = shirtInfoService;
    }

    @PostMapping("/add/{userName}/{password}")
    public ResponseEntity<PasoShopShirts> addShirt(@PathVariable String userName, @PathVariable String password, @Valid  @RequestBody PasoShopShirts shirt, BindingResult b) {
        PasoShopShirts res = shirtInfoService.addShirt(shirt);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/get/{userName}/{password}/{team}")
    public ResponseEntity<List<PasoShopShirts>> getShirts(@PathVariable String userName, @PathVariable String password, @PathVariable String team) {
        List<PasoShopShirts> shirts = shirtInfoService.getShirts(team);
        return new ResponseEntity<>(shirts, HttpStatus.OK);
    }

    @PutMapping("/update/{userName}/{password}")
    public ResponseEntity<PasoShopShirts> updateShirt(@PathVariable String userName, @PathVariable String password, @Valid @RequestBody PasoShopShirts shirt, BindingResult b) {
        PasoShopShirts res = shirtInfoService.updateShirt(shirt);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("delete/{userName}/{password}/{team}")
    public ResponseEntity<String> deleteShirt(@PathVariable String userName, @PathVariable String password, @PathVariable String team) {
        String res = shirtInfoService.deleteShirt(team);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
