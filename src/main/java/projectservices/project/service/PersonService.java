package projectservices.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectservices.project.Dao.PersonDao;
import projectservices.project.Util.Util;
import projectservices.project.model.Feedback;
import projectservices.project.model.Person;

import java.util.List;

@Service
public class PersonService
{

    PersonDao personDao = new PersonDao();
    Util util = new Util();

    @Autowired
    FeedbackService feedbackService;

    public void save(Person person, Integer personId) throws Exception
    {
        if(!util.isNull(person))
        {
            if(!util.isNull(personId))
            {
                Person hasPerson = personDao.getPerson(personId);

                if(util.isNull(hasPerson.getId()))
                {
                    throw new Exception("Usuário não existe na base de dados");
                }
                else
                {
                    personDao.update(person, hasPerson.getId());
                }
            }
            else
            {
                personDao.save(person);
            }
        }
     }

     public List<Person> listPerson(boolean orderBy, String sortType)
     {
         Person person = new Person();
         List<Person> listPerson;

         Integer count = personDao.countPerson();

         if(orderBy && count > 1)
         {
             listPerson = personDao.listSortedByName(sortType);
             person.setPersons(listPerson);
         }
         //faz sentido manter os dois, pois é processamento para realizar o orderBy geralmente é maior
         else
         {
             listPerson = personDao.list();
             person.setPersons(listPerson);
         }

         return person.getPersons();
     }

     public Person getPerson(Integer id)
     {
         //has a person 
         Person person = personDao.getPerson(id);

         if(person != null)
         {
             return person;
         }
         return null;
     }

    public void delete(Integer id)
    {
       Feedback feedback = new Feedback();

       boolean feedbackAssociateFromPerson = false;

       feedbackService.getFeedback(id, feedback);

       if(id.equals(feedback.getPersonId()))
       {
           feedbackAssociateFromPerson = true;
       }
       personDao.delete(id, feedbackAssociateFromPerson);
    }
}
