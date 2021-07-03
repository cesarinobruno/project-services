package projectservices.project.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projectservices.project.model.User;
import projectservices.project.repository.UserRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

     public List<User> allUsers(UserRepository repository)
     {
         List<User> listUser = (List) repository.findAll();
         return listUser;
     }

     public List<User> reorder(UserRepository repository)
      {
       List<User> list = (List)repository.findAll();
        if(list.size() >= 2)
        {
        Collections.sort(list, new Comparator<User>()
        {
            @Override
                public int compare(User o1, User o2) {
                   return o1.getName().compareTo(o2.getName());
                }
            });
        }
        return list;
    }
}
