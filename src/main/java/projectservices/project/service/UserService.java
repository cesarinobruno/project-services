package projectservices.project.service;

import org.springframework.stereotype.Service;
import projectservices.project.Dao.UserDao;
import projectservices.project.model.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class UserService
{
    public UserService() {}

     public void save(User user)
     {
            UserDao userDao = new UserDao();
            userDao.save(user);
     }

     public List<User> allUsers(boolean orderBy)
     {
         UserDao dao = new UserDao();

         List<User> listUser = dao.list();

         if(orderBy)
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

    public void delete(Integer id)
    {
        UserDao userDao = new UserDao();

        userDao.delete(id);
    }
}
