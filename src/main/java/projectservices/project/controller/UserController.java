package projectservices.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectservices.project.model.User;
import projectservices.project.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/api/user/{id}")
    public ResponseEntity oneUser(@PathVariable("id") Integer id) {
        return userRepository.findById(id)
                .map(u -> ResponseEntity.ok().body(u))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/user/save")
    public User save(@RequestBody User user)
    {
        return userRepository.save(user);
    }
}
