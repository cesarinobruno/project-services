package projectservices.project.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectservices.project.model.User;
import projectservices.project.service.UserService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class UserResource {

    @Autowired
    UserService userService;

    @GetMapping( path = "/api/users")
    public List<User> allUser(@RequestParam("orderBy") Optional<Boolean> orderBy)
    {
        return userService.allUsers(orderBy.orElse(false));
    }

    @PostMapping(path = "/api/users/save")
    public ResponseEntity<String> save(@RequestBody User user)
    {
        userService.save(user);
        return ResponseEntity.ok().body("Salvo com sucesso!");
    }

    @DeleteMapping(path = "/api/users/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id)
    {
        userService.delete(id);
        return ResponseEntity.ok().body("Excluido com sucesso!");
    }
}
