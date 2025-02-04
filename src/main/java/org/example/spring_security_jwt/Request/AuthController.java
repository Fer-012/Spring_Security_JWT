package org.example.spring_security_jwt.Request;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.spring_security_jwt.Repository.UserRepository;
import org.example.spring_security_jwt.Service.AuthenticationService;
import org.example.spring_security_jwt.Service.JwtService;
import org.example.spring_security_jwt.Service.RendezVousService;
import org.example.spring_security_jwt.Service.UserService;
import org.example.spring_security_jwt.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  UserRepository userRepository;

  @Autowired
  RendezVousService rendezVousService;

  @Autowired
  UserService userService;
  @Autowired
  PasswordEncoder encoder;

  @Autowired
  public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
    this.jwtService = jwtService;
    this.authenticationService = authenticationService;
  }

  private final JwtService jwtService;
  private final AuthenticationService authenticationService;

  @GetMapping("/signupForm")
  public String showSignupPage(Model model) {
    model.addAttribute("registerUserDto", new SignupRequest());
    return "patient/pages/sign-up";
  }

  @GetMapping("/dashboardDoctor")
  public String showDashboardDoctor(Model model, @CookieValue(name = "JwtToken", required = false) String jwtToken) {
    if (jwtToken != null) {
      User authenticatedUser = jwtService.getUserFromToken(jwtToken);
      model.addAttribute("username", authenticatedUser.getUsername());


      Long doctorId = authenticatedUser.getId();


      long totalPatients = userService.countPatientsForDoctor(doctorId);
      long totalAppointments = rendezVousService.count();
      long confirmedAppointments = rendezVousService.countByStatutR("confirm");
      long unconfirmedAppointments = rendezVousService.countByStatutR("confirm");

      model.addAttribute("totalPatients", totalPatients);
      model.addAttribute("totalAppointments", totalAppointments);
      model.addAttribute("confirmedAppointments", confirmedAppointments);
      model.addAttribute("unconfirmedAppointments", unconfirmedAppointments);
    }

    return "doctor/index";
  }



  @GetMapping("/dashboardPatient")
  public String showDashboardPatient(Model model, @CookieValue(name = "JwtToken", required = false) String jwtToken) {
    if (jwtToken != null) {
      User authenticatedUser = jwtService.getUserFromToken(jwtToken);
      model.addAttribute("username", authenticatedUser.getUsername());
    }
    return "patient/pages/dashboard"; // Dashboard for patients
  }


  @GetMapping("/signinForm")
  public String showSigninPage(Model model) {
    model.addAttribute("loginUserDto", new LoginRequest());
    return "patient/pages/sign-in"; // Path to your Thymeleaf template
  }

  @PostMapping("/signin")
  public String authenticate(LoginRequest loginUserDto, Model model, HttpServletResponse response) {
    try {
      // Authenticate user
      User authenticatedUser = authenticationService.authenticate(loginUserDto);
      String jwtToken = jwtService.generateToken(authenticatedUser, authenticatedUser.getRoles());
      System.out.println("Generated JWT Token: " + jwtToken);
      System.out.println("Authenticated user: " + authenticatedUser.getUsername());

      // Create JWT Cookie
      addJwtCookie(response, jwtToken);

      // Determine role-based redirection
      String role = String.valueOf(authenticatedUser.getRoles());
      System.out.println("Role: " + role); // Debug statement
      if ("ROLE_MEDECIN".equalsIgnoreCase(role)) {
        return "redirect:/api/auth/dashboardDoctor"; // Redirect to admin dashboard
      } else {
        return "redirect:/api/auth/dashboardPatient"; // Redirect to user home
      }
    } catch (Exception e) {
      model.addAttribute("errorMessage", "Invalid username or password");
      return "/patient/pages/sign-in"; // Return to login page with error message
    }
  }

  private void addJwtCookie(HttpServletResponse response, String jwtToken) {
    Cookie jwtCookie = new Cookie("JwtToken", jwtToken);
    jwtCookie.setHttpOnly(true);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(86400); // 1 day
    response.addCookie(jwtCookie);

    // Log the cookie for debugging
    System.out.println("JWT Cookie Created: " + jwtCookie.getName() + " = " + jwtCookie.getValue());
  }

  @PostMapping("/logout")
  public String logout(HttpServletResponse response) {
    // Clear the JWT cookie
    Cookie jwtCookie = new Cookie("JwtToken", null);
    jwtCookie.setHttpOnly(true);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(0); // Set cookie max age to 0 to delete it
    response.addCookie(jwtCookie);

    return "redirect:/login"; // Redirect to login page after logout
  }

  @PostMapping("/signup")
  public String signup(@ModelAttribute("registerUserDto") SignupRequest input, Model model) {
    try {
      authenticationService.signup(input);
      model.addAttribute("successMessage", "User registered successfully!");
      return "redirect:/api/auth/signup";
    } catch (IllegalArgumentException ex) {
      model.addAttribute("errorMessage", ex.getMessage());
      return "/patient/pages/sign-up";
    }
  }


}
