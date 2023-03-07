package test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import projectservices.project.service.StatusService;

public class StatusResourceTest
{
    StatusService statusService = new StatusService();
    @Test
    public void helloSuccessTest()
    {
        Assertions.assertEquals("Online", this.statusService.check());
    }
}