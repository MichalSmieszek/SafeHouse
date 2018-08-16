package project;

import org.springframework.data.repository.CrudRepository;

public interface SensorTypeRepository extends CrudRepository<SensorType, Integer> {
    SensorType findById(int id);
}
