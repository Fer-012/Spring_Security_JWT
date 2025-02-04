package org.example.spring_security_jwt.Service;

import org.example.spring_security_jwt.models.Produit;
import org.example.spring_security_jwt.Repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {

    @Autowired
     ProduitRepository produitRepository;

    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit) ;
    }


    public List<Produit> getAllProduit() {
        return produitRepository.findAll();

    }
    public Produit getProduitById(Long id) {
     return produitRepository.findById(id).get();
     }


    public Produit updateProduit(Produit produit) {
        return produitRepository.saveAndFlush(produit);

    }
     public void deleteProduit(Long id){
        produitRepository.deleteById(id);
    }

}
