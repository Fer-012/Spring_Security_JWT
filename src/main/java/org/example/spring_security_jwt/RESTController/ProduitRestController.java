package org.example.spring_security_jwt.RESTController;

import org.example.spring_security_jwt.models.Produit;
import org.example.spring_security_jwt.Service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produit")
public class ProduitRestController {

    @Autowired
    ProduitService produitService;


    @PostMapping("/saveProduit")
    public Produit saveProduit (@RequestBody Produit produit) {
        return produitService.createProduit(produit);
    }

    @GetMapping("/allP")
    public List<Produit> getallProduit() {
        return produitService.getAllProduit();
    }

    @GetMapping("/getoneProduit/{id}")
    public Produit getProduit(@PathVariable Long id) {
        return produitService.getProduitById(id);

    }

    @PutMapping("/updateProduit/{id}")
    public Produit updateProduit(@PathVariable long id, @RequestBody Produit produit) {
        Produit pr1 =produitService.getProduitById(id);
        if(pr1!=null) {
            produit.setId(id);
            return produitService.updateProduit(produit);

        }
        else{
            return new Produit();
        }
    }

    @DeleteMapping("/Produits/{id}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable ("id") long id) {
        try {
            produitService.deleteProduit(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
