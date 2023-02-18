package projectservices.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projectservices.project.Dao.UserDao;
import projectservices.project.model.Feedback;
import projectservices.project.model.Person;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService
{

    UserDao userDao = new UserDao();

    @Autowired
    FeedbackService feedbackService;

    public void save(Person person) throws Exception
    {
        try
        {
            if(person != null)
            {
                userDao.save(person);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new IllegalArgumentException("Operação não realizada");
        }
     }

     public Person validatePersonByNameAndPassword(final String login, final String password, final PasswordEncoder encoder) throws SQLException
     {
         final Person personData = userDao.validatePersonByNameAndPasswordDAO(login, password, encoder);
         return personData;
     }

     public List<Person> listPerson(boolean orderBy, String sortType)
     {
         final Person person = new Person();

         final Integer count = userDao.countPerson();

         if(orderBy && count > 1)
         {
             person.setPersons(userDao.listSortedByName(sortType));
         }
         //faz sentido manter os dois, pois é processamento para realizar o orderBy geralmente é maior
         else
         {
             person.setPersons(userDao.list());
         }
         return person.getPersons();
     }

     public Person getPerson(Integer id) {
         //has a person 
         final Person person = userDao.getPersonById(id);
         try
         {
             if (person != null)
             {
                 return person;
             }
             throw new Exception("Não existe person para esse id: " + id);
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
         return null;
    }

    public void delete(Integer id)
    {
       final Feedback feedback = new Feedback();

       boolean feedbackAssociateFromPerson = false;

       feedbackService.getFeedback(id, feedback);

       if(id.equals(feedback.getPersonId()))
       {
           feedbackAssociateFromPerson = true;
       }
       userDao.delete(id, feedbackAssociateFromPerson);
    }

    public void update(Person person, Integer id) throws Exception
    {
        if(person != null)
        {
            final Person personFromData = getPerson(id);

            if(personFromData == null || personFromData.getId() != person.getId())
            {
               throw new IllegalArgumentException("Person id não corresponde a de nenhum person do banco");
            }
             userDao.update(person, id);
             return;
        }
        throw new IllegalArgumentException("Person vindo do body está nulo");
    }
}
