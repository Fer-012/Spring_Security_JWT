package org.example.spring_security_jwt.Service;

import org.example.spring_security_jwt.Repository.UserRepository;
import org.example.spring_security_jwt.models.Role;
import org.example.spring_security_jwt.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired

    UserRepository userRepository;

    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRoles(role);  // Fetch users with the provided role
    }
    public List<User> getAllMedecins() {
        return userRepository.findByRoles(Role.ROLE_MEDECIN);
    }

    public User createUser(User user) {

        return userRepository.save(user) ;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();

    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }


    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);

    }
    public void deleteUser( Long id ){
        userRepository.deleteById(id);
    }


    public List<User> getMedecins() {
        return userRepository.findByRoles(Role.ROLE_MEDECIN);
    }


    public User findById(Long doctorId) {
        return userRepository.findById(doctorId).get();
    }

    public long countPatientsForDoctor(Long doctorId) {
        return userRepository.countByRolesAndDoctorId(Role.ROLE_PATIENT, doctorId);
    }
}