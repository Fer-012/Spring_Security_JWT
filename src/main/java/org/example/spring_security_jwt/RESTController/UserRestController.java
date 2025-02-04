package org.example.spring_security_jwt.RESTController;

import org.example.spring_security_jwt.models.User;
import org.example.spring_security_jwt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

  @Autowired
   UserService userService;



    @PostMapping("/saveUser")
    public User saveUser (@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/allU")
    public List<User> getallUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/getoneUser/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);

    }

    @PutMapping("/updateUser/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User user) {
        User u1 =userService.getUserById(id);
        if(u1!=null) {
            user.setId(id);
            return userService.updateUser(user);

        }
        else{
            return new User();
        }
    }

    @DeleteMapping("/Users/{id}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable ("id") long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
