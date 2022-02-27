package projectservices.project.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectservices.project.model.User;
import projectservices.project.repository.UserRepository;
import projectservices.project.service.UserService;

import javax.persistence.Access;
import java.util.*;


@CrossOrigin(origins = "*")
@RestController
public class UserResource {

    @Autowired
    private UserRepository userRepository;
    UserService userService = new UserService();

    @GetMapping(path = "/api/user/{id}")
    public ResponseEntity oneUser(@PathVariable("id") Integer id)
    {
       return userService.oneUser(userRepository, id);
    }

    @GetMapping( path = "/api/users")
    public List<User> allUser(@RequestParam("reorder") Optional<Boolean> reorder)
    {
        return userService.allUsers(userRepository, reorder.orElse(false));
    }

    @PostMapping(path = "/api/user/save")
    public ResponseEntity<String> save(@RequestBody User user)
    {
        userService.save(userRepository, user);
        return ResponseEntity.ok().body("Salvo com sucesso!");
    }
}
