package projectservices.project.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectservices.project.model.Person;
import projectservices.project.service.PersonService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class PersonResource {

    @Autowired
    PersonService personService;

    @GetMapping( path = "/api/users")
    public List<Person> allUser(@RequestParam("orderBy") Optional<Boolean> orderBy)
    {
        return personService.allUsers(orderBy.orElse(false));
    }

    @PostMapping(path = "/api/users/save")
    public ResponseEntity<String> save(@RequestBody Person person)
    {
        personService.save(person);
        return ResponseEntity.ok().body("Salvo com sucesso!");
    }

    @DeleteMapping(path = "/api/users/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id)
    {
        personService.delete(id);
        return ResponseEntity.ok().body("Excluido com sucesso!");
    }
}
