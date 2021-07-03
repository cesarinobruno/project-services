package projectservices.project.service;

import org.springframework.stereotype.Service;
import projectservices.project.model.User;
import projectservices.project.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class UserService {

    public List<User> reorder(UserRepository userRepository)
    {
       List<User> list = (List)userRepository.findAll();
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
