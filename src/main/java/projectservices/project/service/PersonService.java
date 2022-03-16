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

    PersonDao userDao = new PersonDao();

    public void save(Person person)
     {
         userDao.save(person);
     }

     public List<Person> allUsers(boolean orderBy)
     {
         List<Person> listPerson = userDao.list();

         if(orderBy)
         {
             this.reorder(listPerson);
         }

         return listPerson;
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
       userDao.delete(id);
    }
}
