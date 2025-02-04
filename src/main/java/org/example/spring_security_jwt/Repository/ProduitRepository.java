package org.example.spring_security_jwt.Repository;

import org.example.spring_security_jwt.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

 List<Produit> findByNameProduit(String nameProduit);


}
