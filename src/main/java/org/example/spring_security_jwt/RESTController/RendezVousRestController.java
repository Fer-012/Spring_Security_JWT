package org.example.spring_security_jwt.RESTController;

import org.example.spring_security_jwt.Repository.RendezVousRepository;
import org.example.spring_security_jwt.Repository.UserRepository;
import org.example.spring_security_jwt.Service.RendezVousService;
import org.example.spring_security_jwt.models.RendezVous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rendezvous")
public class RendezVousRestController {

    @Autowired
    RendezVousService rendezVousService;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/saveRendezVous")
    public RendezVous saveRendezVous (@RequestBody RendezVous rendezvous) {
        return rendezVousService.createRendezVous(rendezvous);
    }

    @GetMapping("/allRendezVous")
    public List<RendezVous> getallRendezVous() {
        return rendezVousService.getAllRendezVous();
    }

    @GetMapping("/getoneRendezVous/{id}")
    public RendezVous getRendezVous(@PathVariable Long id) {
        return rendezVousService.getRendezVousById(id);

    }

    @PutMapping("/updateRendezVous/{id}")
    public RendezVous updateRendezVous(@PathVariable long id, @RequestBody RendezVous rendezvous) {
        RendezVous r1 =rendezVousService.getRendezVousById(id);
        if(r1!=null) {
           // rendezvous.setId(id);
            return rendezVousService.updateRendezVous(rendezvous);

        }
        else{
            return new RendezVous();
        }
    }

    @DeleteMapping("/rendezvous/{id}")
    public ResponseEntity<HttpStatus> deleteRendezVous(@PathVariable ("id") long id) {
        try {
            rendezVousService.deleteRendezVous(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
