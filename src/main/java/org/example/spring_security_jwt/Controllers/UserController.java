package org.example.spring_security_jwt.Controllers;

import org.example.spring_security_jwt.Repository.UserRepository;
import org.example.spring_security_jwt.Service.AuthenticationService;
import org.example.spring_security_jwt.Service.JwtService;
import org.example.spring_security_jwt.Service.UserService;
import org.example.spring_security_jwt.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RequestMapping("/api/users")
@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    public UserController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }


    private final JwtService jwtService;
    private final AuthenticationService authenticationService;




    @GetMapping("/completeProfile")
    public String showCompleteProfileForm(Model model, @CookieValue(name = "JwtToken", required = false) String jwtToken) {
        if (jwtToken != null) {
            User authenticatedUser = jwtService.getUserFromToken(jwtToken);
            model.addAttribute("medecin", authenticatedUser); // Pass user details
        }
        return "doctor/complete-profile"; // Load profile completion page
    }

    @PostMapping("/completeProfile")
    public String completeProfile(
            @ModelAttribute("medecin") User medecin,
            @CookieValue(name = "JwtToken", required = false) String jwtToken,
            Model model) {
        try {
            if (jwtToken != null) {
                User authenticatedUser = jwtService.getUserFromToken(jwtToken);

                // Update profile fields
                authenticatedUser.setPhone(medecin.getPhone());
                authenticatedUser.setAddresse(medecin.getAddresse());
                authenticatedUser.setDateNaissance(medecin.getDateNaissance());
                authenticatedUser.setSpecialite(medecin.getSpecialite());
                authenticatedUser.setHoraire_ouverture(medecin.getHoraire_ouverture());
                authenticatedUser.setHoraire_fermuture(medecin.getHoraire_fermuture());
                authenticatedUser.setJours_ouverture(medecin.getJours_ouverture());
                authenticatedUser.setDescription(medecin.getDescription());

                // Calculate age
                if (authenticatedUser.getDateNaissance() != null) {
                    int age = Period.between(authenticatedUser.getDateNaissance(), LocalDate.now()).getYears();
                    authenticatedUser.setAge(age);
                }

                // Save updated user details
                userRepository.save(authenticatedUser);
            }
            model.addAttribute("successMessage", "Profile updated successfully!");
            return "redirect:/api/users/profile";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while updating the profile.");
            return "doctor/complete-profile";
        }


    }

    @GetMapping("/profile")
    public String getDoctorProfile(
            Model model,
            @CookieValue(name = "JwtToken", required = false) String jwtToken) {
        try {
            System.out.println("JwtToken: " + jwtToken);

            if (jwtToken != null) {
                // Retrieve the authenticated user based on the token
                User authenticatedUser = jwtService.getUserFromToken(jwtToken);
                System.out.println("Authenticated User: " + authenticatedUser);

                if (authenticatedUser != null) {
                    model.addAttribute("medecin", authenticatedUser);
                    return "doctor/profile";
                }
            }

            return "redirect:/api/auth/signin"; // Redirect to login if not authenticated
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            model.addAttribute("errorMessage", "An error occurred while retrieving the profile: " + e.getMessage());
            return "error/error";
        }
    }


    @GetMapping("/medecins")
    public String getMedecins(Model model, @CookieValue(name = "JwtToken", required = false) String jwtToken) {
        if (jwtToken != null) {
            User authenticatedUser = jwtService.getUserFromToken(jwtToken);
            model.addAttribute("username", authenticatedUser.getUsername());

            List<User> medecins = userService.getAllMedecins();
            model.addAttribute("medecins", medecins);
        }
        return "patient/pages/search";
    }

}
