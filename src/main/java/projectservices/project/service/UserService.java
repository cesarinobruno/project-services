package projectservices.project.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projectservices.project.model.User;
import projectservices.project.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    public UserService(){}

     public ResponseEntity<User> oneUser(UserRepository repository, Integer id)
     {
         return repository.findById(id)
                 .map(u -> ResponseEntity.ok().body(u))
                 .orElse(ResponseEntity.notFound().build());
     }

     public void save(UserRepository repository, User user)
     {
            repository.save(user);
     }

     public List<User> allUsers(UserRepository repository, boolean reorder)
     {
         List<User> listUser = (List) repository.findAll();
         if(reorder)
         {
             this.reorder(listUser);
         }
         return listUser;
     }

     public List<User> reorder(final List<User> users)
      {
      Collections.sort(users, new Comparator<User>()
        {
            @Override
                public int compare(User o1, User o2)
                {
                   return o1.getName().compareTo(o2.getName());
                }
            });
          return users;
    }

    public void delete(UserRepository repository, Integer id)
    {
        Optional<User> user = repository.findById(id);

        if(user.isEmpty())
        {

            throw new EntityNotFoundException("Usuário não existe na base de dados");
        }
        else
        {
            repository.delete(user.get());
        }
    }
}
