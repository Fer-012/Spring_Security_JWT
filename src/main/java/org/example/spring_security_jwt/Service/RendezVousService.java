package org.example.spring_security_jwt.Service;

import org.example.spring_security_jwt.Repository.RendezVousRepository;
import org.example.spring_security_jwt.models.RendezVous;
import org.example.spring_security_jwt.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RendezVousService {

    @Autowired
    RendezVousRepository rendezVousRepository;

    public RendezVous createRendezVous(RendezVous rendezVous) {

        return rendezVousRepository.save(rendezVous) ;
    }

    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();

    }

    public RendezVous getRendezVousById(Long id) {
        return rendezVousRepository.findById(id).get();
    }


    public RendezVous updateRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.saveAndFlush(rendezVous) ;

    }
    public void deleteRendezVous( Long id ){
        rendezVousRepository.deleteById(id);
    }


    public void save(RendezVous rendezVous) {
        rendezVousRepository.save(rendezVous) ;
    }

    public List<RendezVous> findByPatient(User patient) {
        return rendezVousRepository.findByPatient(patient);
    }

    public List<RendezVous> findByDoctor(User doctor) {
        return rendezVousRepository.findByDoctor(doctor);
    }

    public RendezVous findById(Long id) {
        return rendezVousRepository.findById(id).orElse(null);
    }

    public long count() {
        return rendezVousRepository.count();
    }

    public long countByStatutR(String statutR) {
        return rendezVousRepository.countByStatutR(statutR);
    }

    public long countByStatusNot(String statutR) {
        return rendezVousRepository.countByStatutRNot(statutR);
    }

}
