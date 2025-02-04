package org.example.spring_security_jwt.Controllers;

import jakarta.transaction.Transactional;
import org.example.spring_security_jwt.Repository.RendezVousRepository;
import org.example.spring_security_jwt.Repository.UserRepository;
import org.example.spring_security_jwt.Service.AuthenticationService;
import org.example.spring_security_jwt.Service.JwtService;
import org.example.spring_security_jwt.Service.RendezVousService;
import org.example.spring_security_jwt.Service.UserService;
import org.example.spring_security_jwt.models.RendezVous;
import org.example.spring_security_jwt.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/appointment")
@Controller
public class RendezVousController {


    @Autowired
    RendezVousService rendezVousService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RendezVousRepository rendezVousRepository;

    @Autowired
    UserService userService;

    @Autowired
    public RendezVousController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @GetMapping("/doctor/appointments")
    public String showAppointmentDoctor(Model model, @CookieValue(name = "JwtToken", required = false) String jwtToken) {
        if (jwtToken != null) {
            User authenticatedDoctor = jwtService.getUserFromToken(jwtToken);

            List<RendezVous> allAppointments = rendezVousService.findByDoctor(authenticatedDoctor);

            List<RendezVous> confirmedAppointments = allAppointments.stream()
                    .filter(appointment -> "confirm".equalsIgnoreCase(appointment.getStatutR()))
                    .toList();

            List<RendezVous> unconfirmedAppointments = allAppointments.stream()
                    .filter(appointment -> !"confirm".equalsIgnoreCase(appointment.getStatutR()))
                    .toList();

            model.addAttribute("confirmedAppointments", confirmedAppointments);
            model.addAttribute("unconfirmedAppointments", unconfirmedAppointments);
        }
        return "doctor/appointments";
    }


    @PostMapping("/confirm")
    public String confirmAppointment(@RequestParam("id") Long id, @RequestParam("date") String date) {
        System.out.println("Confirming appointment with ID: " + id + " and Date: " + date);
        RendezVous appointment = rendezVousService.findById(id);
        System.out.println("Received ID: " + id);
        System.out.println("Received Date: " + date);

        if (appointment != null) {
            appointment.setStatutR("confirm");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            appointment.setDatep(LocalDate.parse(date, formatter));
            rendezVousService.save(appointment);
        }

        return "redirect:/appointment/doctor/appointments";
    }




    @PostMapping("/deleteRendezVous/{id}")
    @Transactional
    public String deleteRendezVous(@PathVariable("id") long id) {
        rendezVousService.deleteRendezVous(id);
        return "redirect:/appointment/patient/appointments";
    }


    @GetMapping("editRendezVous/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        RendezVous rendezVous = rendezVousService.getRendezVousById(id);
        model.addAttribute("rendezVous", rendezVous);
        return "update_rendezvous";
    }

    @PostMapping("updateRendezVous/{id}")
    public String updatePatient(@PathVariable("id") long id, RendezVous rendezvous) {
        rendezVousService.updateRendezVous(rendezvous);
        return "redirect:/appointment/patient/appointments";
    }

    @PostMapping("/rendezvous/{doctorId}")
    public String createRendezVous(
            @PathVariable Long doctorId,
            @CookieValue(name = "JwtToken", required = false) String jwtToken,
            RedirectAttributes redirectAttributes) {

        if (jwtToken != null) {
            User patient = jwtService.getUserFromToken(jwtToken);
            User doctor = userService.findById(doctorId);

            if (doctor != null) {
                RendezVous rendezVous = new RendezVous();
                rendezVous.setDater(LocalDate.now());
                rendezVous.setStatutR("not confirmed");
                rendezVous.setDoctor(doctor);
                rendezVous.setPatient(patient);

                rendezVousService.save(rendezVous);

                redirectAttributes.addFlashAttribute("successMessage", "Appointment requested successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Doctor not found.");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "You need to log in to make an appointment.");
        }

        return "redirect:/appointment/patient/appointments";
    }

    @GetMapping("/patient/appointments")
    public String getPatientAppointments(
            Model model,
            @CookieValue(name = "JwtToken", required = false) String jwtToken) {

        if (jwtToken != null) {
            User patient = jwtService.getUserFromToken(jwtToken);
            List<RendezVous> appointments = rendezVousService.findByPatient(patient);
            appointments.forEach(appointment -> System.out.println(appointment.toString()));

            model.addAttribute("appointments", appointments);
        } else {
            model.addAttribute("errorMessage", "You need to log in to view your appointments.");
        }

        return "patient/pages/appointments";
    }





}
