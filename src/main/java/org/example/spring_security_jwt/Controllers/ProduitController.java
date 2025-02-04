package org.example.spring_security_jwt.Controllers;

import org.example.spring_security_jwt.models.Produit;
import org.example.spring_security_jwt.Service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProduitController {

    @Autowired
     ProduitService produitService;

    @RequestMapping("/addProduit")
    public String addProduit(Model model) {
        Produit produit = new Produit();
        model.addAttribute("ProduitForm",produit);
        return "new_produit";

    }

    @RequestMapping(value = "/saveProduit" , method = RequestMethod.POST)
    public String saveProduit(@ModelAttribute("ProduitForm") Produit produit) {
        produitService.createProduit(produit);
        return "redirect:/allProduits";
    }

    @RequestMapping("/allProduit")
    public String listProduit(Model model) {
        List<Produit> listProduit = produitService.getAllProduit();
        model.addAttribute("listProduits", listProduit);
        return "liste_produit";

    }
    @GetMapping("deleteProduit/{id}")
    public String deleteProduit(@PathVariable("id") long id) {

        produitService.deleteProduit(id);
        return "redirect:/allProduit";
    }


    @GetMapping("editProduit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Produit produit = produitService.getProduitById(id);
        model.addAttribute("produit", produit);
        return "update_produit";
    }

    @PostMapping("updateProduit/{id}")
    public String updateProduit(@PathVariable("id") long id, Produit produit) {
        produitService.updateProduit(produit);
        return "redirect:/allProduit";
    }


}
