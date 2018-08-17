package project.Repository;

import org.springframework.data.repository.CrudRepository;
import project.Model.Measurement;
import project.Model.Sensor;

import java.util.List;
import java.util.Set;

public interface MeasurementRepository extends CrudRepository<Measurement,Integer> {
    List<Measurement> findAllBySensorOrderById(Sensor sensor);
}
