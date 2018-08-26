package project.Repository;

import org.springframework.data.repository.CrudRepository;
import project.Model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findById(int id);
    User findByName(String name);
}
