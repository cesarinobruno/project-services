package projectservices.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectservices.project.Dao.PersonDao;
import projectservices.project.model.Feedback;
import projectservices.project.model.Person;

import java.util.List;

@Service
public class PersonService
{

    PersonDao personDao = new PersonDao();

    @Autowired
    FeedbackService feedbackService;

    public void save(Person person) throws Exception
    {
        try
        {
            if(person != null)
            {
                personDao.save(person);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new IllegalArgumentException("Operação não realizada");
        }
     }

     public List<Person> listPerson(boolean orderBy, String sortType)
     {
         final Person person = new Person();

         final Integer count = personDao.countPerson();

         if(orderBy && count > 1)
         {
             person.setPersons(personDao.listSortedByName(sortType));
         }
         //faz sentido manter os dois, pois é processamento para realizar o orderBy geralmente é maior
         else
         {
             person.setPersons(personDao.list());
         }
         return person.getPersons();
     }

     public Person getPerson(Integer id) {
         //has a person 
         final Person person = personDao.getPerson(id);
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
       personDao.delete(id, feedbackAssociateFromPerson);
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
             personDao.update(person, id);
             return;
        }
        throw new IllegalArgumentException("Person vindo do body está nulo");
    }
}
