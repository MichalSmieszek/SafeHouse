package project.Repository;

import org.springframework.data.repository.CrudRepository;
import project.Model.Measurement;
import project.Model.Sensor;

import java.util.Set;

public interface SensorRepository extends CrudRepository<Sensor, Integer> {
    Sensor findById(int id);
    Set<Sensor> findAll();
}
