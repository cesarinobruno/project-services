package projectservices.project.service;

import org.springframework.stereotype.Service;

@Service
public class StatusService {

    public String check()
    {
            return "Online";
    }
}
