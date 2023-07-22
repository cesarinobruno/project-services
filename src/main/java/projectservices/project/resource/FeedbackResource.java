package projectservices.project.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectservices.project.model.Feedback;
import projectservices.project.service.FeedbackService;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class FeedbackResource
{
    @Autowired
    FeedbackService feedbackService;

    @PostMapping(path = "/api/feedbacks/{personId}/save")
    public ResponseEntity<String> newPost(@RequestBody Feedback feedback, @PathVariable("personId") Integer personId) throws Exception
    {
        try
        {
            this.feedbackService.save(feedback, personId);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }
    @GetMapping(path="/api/feedbacks")
    public ResponseEntity feedback() throws SQLException
    {
        return ResponseEntity.ok(feedbackService.list());
    }
}
