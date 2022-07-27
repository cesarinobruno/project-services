package projectservices.project.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectservices.project.model.Feedback;
import projectservices.project.service.FeedbackService;

@CrossOrigin(origins = "*")
@RestController
public class FeedbackResource
{
    @Autowired
    FeedbackService feedbackService;

    @PostMapping(path = "/api/feedbacks/{personId}/save")
    public ResponseEntity<String> newPost(@RequestBody Feedback feedback, @PathVariable("personId") Integer personId) throws Exception
    {
        feedbackService.save(feedback, personId);
        return ResponseEntity.ok().body("Novo post");
    }
}
