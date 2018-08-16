package project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.Model.SensorToUser;
import project.Model.User;
import project.Repository.SensorRepository;
import project.Repository.SensorToUserRepository;
import project.Repository.UserRepository;
import project.Model.Sensor;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(path= "/sensorToUser")
public class SensorToUserController {
    @Autowired
    SensorToUserRepository sensorToUserRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SensorRepository sensorRepository;

    @CrossOrigin
    @ResponseBody
    @GetMapping (path = "/add")
    public String addSensorToUser(@RequestParam Sensor newSensor,
                                  @RequestParam User newUser ) {
        try {
            Sensor sensor = new Sensor();
            User user = new User();
            sensor=sensorRepository.findById(newSensor.getId());
            user=userRepository.findById(newUser.getId());
            System.out.println(sensor + " " + user);
            Set<SensorToUser> sensorUser = new HashSet();
            sensorUser = sensorToUserRepository.findAllBySensorAndUser(sensor, user);

            System.out.print(sensorUser);
            if (sensorUser.size() == 0) {
                SensorToUser sensorToUser = new SensorToUser();
                sensorToUser.setSensor(sensor);
                sensorToUser.setUser(user);
                sensorToUserRepository.save(sensorToUser);
                return ("Sensor saved to User.");
            } else
                return ("This sensor is claimed to user.");
        }catch(Exception e){
            e.printStackTrace();
            return("There isn't sensor or user with that id.");
        }
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(path="/delete")
    public String deleteSensorFromUser(SensorToUser sensorToUser){
        try {
            SensorToUser element=sensorToUserRepository.findById(sensorToUser.getId());
            sensorToUserRepository.delete(element);
            return ("Element deleted.");
        }catch(Exception e){
            return("There isn't connection between sensor and user.");
        }
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(path="/user")
    public Set<SensorToUser> viewUserSensors(@RequestParam User newUser){
        Set<SensorToUser> sensorToUserSet=new HashSet<>();
        try{
            User user=userRepository.findById(newUser.getId());
            sensorToUserSet = sensorToUserRepository.findAllByUser(user);
        }catch( Exception e){
            e.printStackTrace();
        }
        return (sensorToUserSet);
    }

}
