package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectservices.project.model.Person;
import projectservices.project.service.UserService;

public class PersouResourceTest
{
    @BeforeEach
    public void init() throws InterruptedException
    {
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
}
