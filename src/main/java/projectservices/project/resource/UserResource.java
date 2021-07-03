package projectservices.project.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectservices.project.model.User;
import projectservices.project.repository.UserRepository;
import projectservices.project.service.UserService;

import java.util.*;

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

    @RequestMapping("/api/user")
    public List<User> allUser()
    {
          return userService.allUsers(userRepository);
    }

    @GetMapping("/api/user/reorder")
    public List<User> reorder()
    {
        List<User> orderedUser = userService.reorder(userRepository);
        return orderedUser;
    }

    @PostMapping(path = "/api/user/save")
    public ResponseEntity<String> save(@RequestBody User user)
    {
        userService.save(userRepository, user);
        return ResponseEntity.ok().body("Salvo com sucesso!");
    }
}
