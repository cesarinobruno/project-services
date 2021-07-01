package projectservices.project.repository;

import org.springframework.data.repository.CrudRepository;
import projectservices.project.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {}
