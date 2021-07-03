package projectservices.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectservices.project.model.User;
import projectservices.project.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/api/user/{id}")
    public ResponseEntity oneUser(@PathVariable("id") Integer id)
    {
        return userRepository.findById(id)
                .map(u -> ResponseEntity.ok().body(u))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping("/api/user")
    public List<User> allUser()
    {
        List<User> listUser = (List) userRepository.findAll();
        return listUser.size() > 2 ? listUser : null;
    }

    @GetMapping("/api/user/reorder")
    public List<User> reorder()
    {
        List<User> listUser = (List)userRepository.findAll();
        Collections.sort(listUser, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2)
            {
               return u1.getName().compareTo(u2.getName());
            }
        });
        return listUser;
    }

    @PostMapping(path = "/api/user/save")
    public User save(@RequestBody User user)
    {
        return userRepository.save(user);
    }
}