package projectservices.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import projectservices.project.Dao.FeedbackDao;
import projectservices.project.model.Feedback;
import projectservices.project.model.Person;

@Controller
public class FeedbackService
{
    @Autowired
    UserService userService;

    FeedbackDao feedbackDao = new FeedbackDao();

    public void save(Feedback feedback, Integer id) throws Exception {

        Person person = userService.getPerson(id);

        if(person == null)
        {
            throw new Exception("Usuário não existe");
        }
        else if(person.getId().equals(feedback.getPersonId()))
        {
            feedbackDao.save(feedback, person.getId());
        }
        else
        {
            throw new Exception("id do usuário logado não corresponde ao personId do usuário do novo post");
        }
    }

    public void getFeedback(Integer personId, Feedback feedback)
    {
        feedbackDao.checkForFeedbackFromUser(personId, feedback);
    }
}
