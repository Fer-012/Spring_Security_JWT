package org.example.spring_security_jwt.Repository;

import org.example.spring_security_jwt.models.RendezVous;
import org.example.spring_security_jwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous,Long>{

    List<RendezVous> findByDoctor_Id(Long doctorId);
    @Query("SELECT r FROM RendezVous r JOIN FETCH r.patient WHERE r.patient = :patient")
    List<RendezVous> findByPatient(@Param("patient") User patient);
    List<RendezVous> findByDoctor(User doctor);
    long count();
    long countByStatutR(String statutR);
    long countByStatutRNot(String statutR);

}
