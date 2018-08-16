package project.Repository;

import org.springframework.data.repository.CrudRepository;
import project.Model.SensorType;

public interface SensorTypeRepository extends CrudRepository<SensorType, Integer> {
    SensorType findById(int id);
}
