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

    public void save(Person person)
     {
         personDao.save(person);
     }

     public List<Person> listPerson(boolean orderBy)
     {
         Person person = new Person();
         List<Person> listPerson;

         Integer count = personDao.countPerson();

         if(orderBy && count > 1)
         {
             listPerson = personDao.listSortedByName();
             person.setPersons(listPerson);
         }
         else
         {
             listPerson = personDao.list();
             person.setPersons(listPerson);
         }

         return person.getPersons();
     }

     public Integer getPerson(Integer id)
     {
         //verify person exists
         Integer personId = personDao.getPerson(id);

         if(personId == null)
         {
             return personId;
         }
         return personId;

     }

//excluir ---> agora é feito no banco
//     public List<Person> reorder(final List<Person> people)
//      {
//      Collections.sort(people, new Comparator<Person>()
//        {
//            @Override
//                public int compare(Person o1, Person o2)
//                {
//                   return o1.getName().compareTo(o2.getName());
//                }
//            });
//          return people;
//    }

    public void delete(Integer id)
    {
       Feedback feedback = new Feedback();

       Boolean feedbackAssociateFromPerson = false;

       feedbackService.getFeedback(id, feedback);

       if(id.equals(feedback.getPersonId()))
       {
           feedbackAssociateFromPerson = true;
       }

       personDao.delete(id, feedbackAssociateFromPerson);
    }
}
