package projectservices.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import projectservices.project.Dao.FeedbackDao;
import projectservices.project.model.Feedback;

@Controller
public class FeedbackService
{
    @Autowired
    PersonService personService;

    FeedbackDao feedbackDao = new FeedbackDao();

    public void save(Feedback feedback, Integer id) throws Exception {

        Integer personId = personService.getPerson(id);

        if(personId == null)
        {
            throw new Exception("Usuário não é o mesmo da publicação");
        }

        feedbackDao.save(feedback, personId);
    }

    public void getFeedback(Integer personId, Feedback feedback)
    {
        feedbackDao.checkForFeedbackFromUser(personId, feedback);
    }
}
