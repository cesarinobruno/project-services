package test;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import projectservices.project.service.UserService;

public class PersouResourceTest {

    final UserService userService = new UserService();

    @BeforeAll
    static void setup() throws InterruptedException {
        Thread.sleep(2000);
    }

    @DisplayName("Single Test Success")
    @Test
    void testSingleSuccess() throws InterruptedException {
        Assert.assertEquals(5, userService.sum(2, 3));
    }

    @DisplayName("Seconde Test Success")
    @Test
    void testSecondSuccess() throws InterruptedException {
        System.out.println("Test in progress -> testSecondSuccess()");
        Assert.assertEquals("A", "A");
    }
}
