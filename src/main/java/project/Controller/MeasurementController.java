package project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.Model.Measurement;
import project.Model.Sensor;
import project.Repository.MeasurementRepository;
import project.Repository.SensorRepository;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path = "/measurement")
public class MeasurementController {
    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    SensorRepository sensorRepository;
    @CrossOrigin
    @ResponseBody
    @GetMapping(path = "/showLastMeasurements")
    public List<Measurement> showMeasurements(@RequestParam Sensor sensor,
                                              @RequestParam Integer countOfMeasurement){
        List<Measurement> measurementList= new LinkedList<>();
        try {
            measurementList= measurementRepository.findAllBySensorOrderById(sensorRepository.findById(sensor.getId()));
            if (measurementList.size()<=countOfMeasurement) {
                return (measurementList);
            }
            else {
                List <Measurement> newList = new LinkedList<>();
                for (int i = measurementList.size() - countOfMeasurement; i < measurementList.size(); i++) {
                    newList.add(measurementList.get(i));
                    System.out.println(measurementList.get(i).getId());
                }
                return (newList);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return (measurementList);
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(path = "/deleteMeasurements")
    public String deleteMeasurements(@RequestParam Sensor sensor,
                                     @RequestParam Integer numberOfSensorMeasurements){
        List <Measurement> measurementList = new LinkedList<>();
        try {
            measurementList = measurementRepository.findAllBySensorOrderById(sensorRepository.findById(sensor.getId()));
            if (measurementList.size()-numberOfSensorMeasurements<0)
                return("There aren't so many elements");
            for(int i=0;i<measurementList.size()-numberOfSensorMeasurements;i++)
                measurementRepository.delete(measurementList.get(i));
                 return("Done");
        }catch(Exception e){
            e.printStackTrace();
        }
        return("Error");
    }

}
