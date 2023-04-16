package projectservices.project.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import projectservices.project.Repository.SingleConnection;
import projectservices.project.model.Person;
import projectservices.project.resource.UserResource;
import projectservices.project.service.UserService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PersouResourceTest
{
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserResource userResource;

    @BeforeEach
    public void init() throws Exception
    {
        if (SingleConnection.getConnection() == null) {
            throw new Exception("Sem conexão com o banco de dados");
        }
        Thread.sleep(2000);
    }
    @Test
    public void getPersonNotFoundExceptionTest()
    {
        final Exception exc = Assertions.assertThrows(Exception.class, () -> new UserService().getPerson(1000));
        Assertions.assertEquals("Não existe person para esse id: 1000", exc.getMessage());
    }

    @Test
    public void updatePersonBodyIsNullExceptionTest()
    {
        final IllegalArgumentException ilx = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserService().update(null, 1000));
        Assertions.assertEquals("Person vindo do body está nulo", ilx.getMessage());
    }
    @Test
    public void updatePersonDoesNotExistInBaseTest()
    {
        //teste que precisa de mock
        final Person person = new Person();
        person.setId(2000);
        person.setName("Person does not exist in base");
        person.setLogin("teste.teste");
        person.setPassword("1234");

        final IllegalArgumentException ilx = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserService().update(person, 4));
        Assertions.assertEquals(IllegalArgumentException.class, ilx.getClass());
    }
    @Test
    void loginValidateTest() throws Exception
    {
        this.mockMvc.perform(post("/api/users/login")
            .param("login", "sandro.alexandre")
            .param("password", "1234"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Entrada permitida")));
    }
}
