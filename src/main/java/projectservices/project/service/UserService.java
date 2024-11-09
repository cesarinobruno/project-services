package projectservices.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projectservices.project.Dao.UserDao;
import projectservices.project.model.Feedback;
import projectservices.project.model.Person;
import projectservices.project.model.PersonListResult;

import java.util.stream.Stream;

@Service
public class UserService
{
    static UserDao userDao = new UserDao();

    @Autowired
    FeedbackService feedbackService;

    public void save(Person person) throws Exception
    {
            if(person != null)
            {
                validatePersonOrIfExistsInBase(person, null);
                this.userDao.save(person);
            }
    }

     public Person validatePersonOrIfExistsInBase(final Person person, final PasswordEncoder encoder) throws Exception
     {
        if(encoder == null)
        {
            final Stream<Person> personWithLoginInUse = this.listPerson(false, null).getPersonList()
                                                            .stream()
                                                            .filter(pDB -> pDB.getLogin().equalsIgnoreCase(person.getLogin()));

            if(personWithLoginInUse != null && personWithLoginInUse.anyMatch(p -> !p.getId().equals(person.getId())))
            {
                throw new IllegalArgumentException("login já existe");
            }
        }

        return userDao.validatePersonByNameAndPasswordDAO(person, encoder);
     }

     public Person getPerson(Integer id)
     {
         final Person person = userDao.getPersonById(id);

         if (person == null)
         {
             throw new RuntimeException("personId fornecido: " + id + " não corresponde a nenhum person");
         }
        return person;
    }

    public void delete(Integer id)
    {
       final Person person = getPerson(id);

       final Feedback feedback = new Feedback();

       if(person == null)
       {
           throw new RuntimeException("personId fornecido: " + id + " não corresponde a nenhum person");
       }
       else
       {
         feedbackService.getFeedback(person.getId(), feedback);

         if(person.getId().equals(feedback.getPersonId()))
           {
             userDao.delete(person.getId(), true);
             return;
           }
            userDao.delete(person.getId(), false);
       }
    }
    public void update(Person person, Integer id) throws Exception
    {
        if(person != null)
        {
            final Person personFromData = getPerson(id);

            if(personFromData == null || personFromData.getId() != person.getId())
            {

               throw new IllegalArgumentException();
            }
             this.validatePersonOrIfExistsInBase(person, null);
             userDao.update(person, id);
             return;
        }
        throw new IllegalArgumentException("Person vindo do body está nulo");
    }

    public PersonListResult listPerson(boolean orderBy, String sortType)
    {
        PersonListResult personListResult = null;

        final Integer total = userDao.countPerson();

        if(orderBy && total > 1)
        {
            try
            {
               personListResult = new PersonListResult(userDao.listSortedByName(sortType), total);
            }
            catch (Exception e)
            {
                e.getCause();
            }
        }
        else
        {
            personListResult = new PersonListResult(userDao.list(), total);
        }
        return personListResult;
    }
}
