package projectservices.project.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import projectservices.project.model.Person;
import projectservices.project.service.PersonService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class PersonResource {

    private final static String API_BASE_PATH = "/api/users";

    private final PasswordEncoder encoder;

    @Autowired
    PersonService personService;

    public PersonResource(PasswordEncoder encoder)
    {
        this.encoder = encoder;
    }

    @GetMapping(path = API_BASE_PATH)
    public List<Person> users(@RequestParam("orderBy") Optional<Boolean> orderBy)
    {
        return personService.listPerson(orderBy.orElse(false));
    }

    @PostMapping(path = API_BASE_PATH + "/save")
    public ResponseEntity<String> save(@RequestBody Person person)
    {
        person.setPassword(encoder.encode(person.getPassword()));
        personService.save(person);
        return ResponseEntity.ok().body("Salvo com sucesso!");
    }

    @DeleteMapping(path = API_BASE_PATH + "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id)
    {
        personService.delete(id);
        return ResponseEntity.ok().body("Excluido com sucesso!");
    }
}
