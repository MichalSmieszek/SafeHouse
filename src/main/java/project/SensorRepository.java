package project;

import org.springframework.data.repository.CrudRepository;

public interface SensorRepository extends CrudRepository<Sensor, Integer> {
    Sensor findById(int id);
}
