package projectservices.project.service;

import org.springframework.stereotype.Service;
import projectservices.project.Dao.PersonDao;
import projectservices.project.model.Person;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PersonService
{

    PersonDao personDao = new PersonDao();

    public void save(Person person)
     {
         personDao.save(person);
     }

     public List<Person> allUsers(boolean orderBy)
     {
         List<Person> listPerson = personDao.list();

         if(orderBy)
         {
             this.reorder(listPerson);
         }

         return listPerson;
     }

     public Integer getPerson(Integer id)
     {
         Integer personId = personDao.getPerson(id);

         if(personId == null)
         {
             return personId;
         }
         return personId;

     }

     public List<Person> reorder(final List<Person> people)
      {
      Collections.sort(people, new Comparator<Person>()
        {
            @Override
                public int compare(Person o1, Person o2)
                {
                   return o1.getName().compareTo(o2.getName());
                }
            });
          return people;
    }

    public void delete(Integer id)
    {
       personDao.delete(id);
    }
}
