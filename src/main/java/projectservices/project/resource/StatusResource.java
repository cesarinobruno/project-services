package projectservices.project.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projectservices.project.service.StatusService;

@RestController
public class StatusResource {
    StatusService statusService = new StatusService();

    @GetMapping(path = "/api/status")
    public String status()
    {
        return statusService.check();
    }
}
