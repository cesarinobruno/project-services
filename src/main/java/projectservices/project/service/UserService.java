package projectservices.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projectservices.project.Dao.UserDao;
import projectservices.project.model.Feedback;
import projectservices.project.model.Person;

import java.util.List;
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
            final Stream<Person> personWithLoginInUse = this.listPerson(false, null)
                                                            .stream()
                                                            .filter(pDB -> pDB.getLogin().equalsIgnoreCase(person.getLogin()));

            if(personWithLoginInUse != null && personWithLoginInUse.anyMatch(p -> !p.getId().equals(person.getId())))
            {
                throw new Exception();
            }
        }

        return userDao.validatePersonByNameAndPasswordDAO(person, encoder);
     }

     public List<Person> listPerson(boolean orderBy, String sortType)
     {
         final Person person = new Person();

         final Integer count = userDao.countPerson();

         if(orderBy && count > 1)
         {
             try
             {
                 person.setPersons(userDao.listSortedByName(sortType));
             }
             catch (Exception e)
             {
                e.getCause();
             }
         }
         else
         {
             person.setPersons(userDao.list());
         }
         return person.getPersons();
     }

     public Person getPerson(Integer id)
     {
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
               throw new IllegalArgumentException();
            }
             this.validatePersonOrIfExistsInBase(person, null);
             userDao.update(person, id);
             return;
        }
        throw new IllegalArgumentException("Person vindo do body está nulo");
    }

    public int sum(int a, int b)
    {
        return a + b;
    }
}
