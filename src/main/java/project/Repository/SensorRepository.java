package project.Repository;

import org.springframework.data.repository.CrudRepository;
import project.Model.Sensor;

public interface SensorRepository extends CrudRepository<Sensor, Integer> {
    Sensor findById(int id);
}
