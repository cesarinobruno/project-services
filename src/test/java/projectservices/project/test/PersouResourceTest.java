package projectservices.project.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import projectservices.project.Repository.SingleConnection;
import projectservices.project.model.Person;
import projectservices.project.resource.UserResource;
import projectservices.project.service.UserService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
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

    public static ObjectMapper getMapper()
    {
        return new ObjectMapper();
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
    public void loginValidateSucessTest() throws Exception
    {
        this.mockMvc.perform(post("/api/users/login")
            .param("login", "sandro.alexandre")
            .param("password", "1234"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Entrada permitida")));
    }

    @Test
    public void getUserListSuccessTest() throws Exception
    {
        final MvcResult result = this.mockMvc.perform(get("/api/users"))
                                             .andDo(print())
                                             .andExpect(status().isOk()).andReturn();


        final String total = result.getResponse().getHeader("total");
        Assertions.assertTrue(Integer.parseInt(total) > 5);
        final List<Person> personList = this.getMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<Person>>() {});

        final Person person0 = personList.get(0);
        final Person person1 = personList.get(1);
        final Person person2 = personList.get(2);
        final Person person3 = personList.get(3);

        Assertions.assertEquals(2, person0.getId());
        Assertions.assertEquals(4, person1.getId());
        Assertions.assertEquals(5, person2.getId());
        Assertions.assertEquals(6, person3.getId());
    }

//    WIP
//    @Test
//    public void saveSuccessTest() throws Exception
//    {
//        Person person = new Person();
//        person.setName("Carvalho carvalho");
//        person.setLogin("carvalho.login");
//        person.setPassword("carvalho.password");
//
//        ObjectMapper mapper = this.getMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
//        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
//        String requestJson = ow.writeValueAsString(person);
//
//
//        final MvcResult result = this.mockMvc.perform(post("/api/save").contentType(MediaType.APPLICATION_JSON)
//                .content(requestJson)).andReturn();
//        int actualResult = result.getResponse().getStatus();
//        Assertions.assertEquals(actualResult, HttpStatus.OK.value());
//    }
}
