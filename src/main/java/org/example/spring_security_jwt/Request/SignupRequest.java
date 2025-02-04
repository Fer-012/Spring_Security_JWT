package org.example.spring_security_jwt.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.spring_security_jwt.models.Role;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;


    private Role role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public void setRoles(Role role) {
        this.role = role;
    }

    public Role getRoles() {
        return role;
    }
}
