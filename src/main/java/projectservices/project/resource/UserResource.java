package projectservices.project.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import projectservices.project.model.Person;
import projectservices.project.model.PersonListResult;
import projectservices.project.service.UserService;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class UserResource {

    private final static String API_BASE_PATH = "/api/users";

    private final PasswordEncoder encoder;

    @Autowired
    UserService userService;

    public UserResource(PasswordEncoder encoder)
    {
        this.encoder = encoder;
    }

    @PostMapping(path = API_BASE_PATH + "/login")
    public ResponseEntity<String> session(@RequestParam("login") final String login,
                                          @RequestParam("password") final String password)
    {
        try
        {
            final Person person = new Person();
            person.setLogin(login);
            person.setPassword(password);

            if(userService.validatePersonOrIfExistsInBase(person, encoder) == null)
            {
                return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<String>("Entrada permitida", HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getCause();
        }
        return null;
    }

    @GetMapping(path = API_BASE_PATH)
    public ResponseEntity users(@RequestParam("orderBy") Optional<Boolean> orderBy, @RequestParam("type") Optional<String> type)
    {
        final PersonListResult personListResult = userService.listPerson(orderBy.orElse(false), type.orElse("createdOn"));
        return ResponseEntity.ok().header("total", personListResult.getTotal().toString()).body(personListResult.getPersonList());

    }

    @PostMapping(path = API_BASE_PATH + "/save")
    public ResponseEntity<String> save(@RequestBody Person person) throws Exception
    {
        person.setPassword(encoder.encode(person.getPassword()));
        try
        {
            userService.save(person);
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>("Login já existe!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = API_BASE_PATH + "/{id}")
    public ResponseEntity user(@PathVariable Integer id) throws Exception
    {
        try
        {
            return ResponseEntity.ok().body(userService.getPerson(id));
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = API_BASE_PATH + "/update/{id}")
    public ResponseEntity<String> update(@RequestBody Person person, @PathVariable Integer id) throws Exception
    {
        try
        {
            userService.update(person, id);
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = API_BASE_PATH + "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id)
    {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
