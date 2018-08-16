package project.Repository;

import org.springframework.data.repository.CrudRepository;
import project.Model.SensorToUser;
import project.Model.Sensor;
import project.Model.User;
import java.util.Set;

public interface SensorToUserRepository extends CrudRepository<SensorToUser,Integer> {
    Set<SensorToUser> findAllBySensorAndUser(Sensor sensor, User user);
    SensorToUser findById(int id);
    Set<SensorToUser> findAllByUser(User user);
}
