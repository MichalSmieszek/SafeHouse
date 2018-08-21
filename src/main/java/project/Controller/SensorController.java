package project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.Model.*;
import project.Repository.MeasurementRepository;
import project.Repository.SensorRepository;
import project.Repository.SensorToUserRepository;
import project.Repository.SensorTypeRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Controller
@RequestMapping(path = "/sensor")
public class SensorController {
    @Autowired
    SensorTypeRepository sensorTypeRepository;
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    SensorToUserRepository sensorToUserRepository;
    @CrossOrigin
    @ResponseBody
    @GetMapping (path = "/add")
    public String addSensorValue(@RequestParam double minValue,
                                 @RequestParam double maxValue,
                                 @RequestParam SensorType sensorType,
                                 @RequestParam String nazwa){
        Sensor sensor = new Sensor();
        sensor.setAcceptedValueMin(minValue);
        sensor.setAcceptedValueMax(maxValue);
        sensor.setType(sensorType);
        sensor.setName(nazwa);
        sensorRepository.save(sensor);
        return ("Data saved");
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/changeMinValue", method = RequestMethod.POST)
    public String updateMinValue (@RequestBody Sensor newSensor) {
        try {
            Sensor sensor = sensorRepository.findById(newSensor.getId());
            if (sensor.getAcceptedValueMax()>newSensor.getAcceptedValueMin()) {
                sensor.setAcceptedValueMin(newSensor.getAcceptedValueMin());
                sensorRepository.save(sensor);
            }
            else return("Min Value is too high.");
        } catch (Exception e) {
            e.printStackTrace();
            return("Data hasn't been changed.");
        }
        return("Accepted value changed.");
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/changeMaxValue", method = RequestMethod.POST)
    public String updateMaxValue (@RequestBody Sensor newSensor) {
        try {
            Sensor sensor = sensorRepository.findById(newSensor.getId());
            if (newSensor.getAcceptedValueMax()>sensor.getAcceptedValueMin()) {
                sensor.setAcceptedValueMax(newSensor.getAcceptedValueMax());
                sensorRepository.save(sensor);
            }
            else
                return("Max Value to low.");
        } catch (Exception e) {
            e.printStackTrace();
            return("Data hasn't been changed.");
        }
        return("Accepted value changed.");
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/changeValue", method = RequestMethod.POST)
    public String updateValue (@RequestBody Sensor newSensor) {
        try {
            Sensor sensor = sensorRepository.findById(newSensor.getId());
            sensor.setValue(newSensor.getValue());
            sensorRepository.save(sensor);
            Measurement measurement = new Measurement();
            measurement.setValue(newSensor.getValue());
            measurement.setSensor(newSensor);
            measurementRepository.save(measurement);
            if (newSensor.getValue()>newSensor.getAcceptedValueMax() || newSensor.getValue()<newSensor.getAcceptedValueMin()) {
                Set<User> userSet = new HashSet<>();
                Set<SensorToUser> sensorToUserSet = new HashSet<>();
                sensorToUserSet = sensorToUserRepository.findAllBySensor(sensor);
                for (SensorToUser sensorToUser : sensorToUserSet)
                    userSet.add(sensorToUser.getUser());

                for (User user : userSet)
                    sendEmail(user, sensor);
            }
         } catch (Exception e) {
            e.printStackTrace();
            return("Data hasn't been changed.");
        }
        return("Temperature changed.");
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/get")
    public Sensor showSensor(@RequestParam Sensor sensor){
        return(sensorRepository.findById(sensor.getId()));

    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/getAll")
    public Set <Sensor> showSensors(){
        return(sensorRepository.findAll());

    }
    public String sendEmail(User user, Sensor sensor) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "*");
        String greetings;
        if (user.getSex()=='m')
            greetings = "Dear Sir,";
        else
            greetings ="Dear Madame,";
        final String username = "safetyhouseapplication";
        final String password = "asd1fgh2jkl3";
        final String fromEmail = "safetyhouseapplication@gmail.com";
        final String toEmail = user.getEmail();
        final String subject = "Danger at home";
        final String text = greetings+ "\n" + "your sensor called " + sensor.getName() + " has already shown value " +sensor.getValue()+
                ". Let check that quickly. \nWith regards, \nAdministration of Safety Home." ;
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject(subject);
                message.setText(text);
                Transport.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }


        return("Dziala");
    }

}
