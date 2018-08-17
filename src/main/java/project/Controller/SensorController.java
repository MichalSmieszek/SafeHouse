package project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.Model.Measurement;
import project.Model.Sensor;
import project.Model.User;
import project.Repository.MeasurementRepository;
import project.Repository.SensorRepository;
import project.Model.SensorType;
import project.Repository.SensorTypeRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    @CrossOrigin
    @ResponseBody
    @GetMapping (path = "/add")
    public String addSensorValue(@RequestParam double minValue,
                                 @RequestParam double maxValue,
                                 @RequestParam SensorType sensorType){
        Sensor sensor = new Sensor();
        sensor.setAcceptedValueMin(minValue);
        sensor.setAcceptedValueMax(maxValue);
        sensor.setType(sensorType);
        sensorRepository.save(sensor);
        return ("Data saved");
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/changeMinValue", method = RequestMethod.POST)
    public String updateMinValue (@RequestBody Sensor newSensor) {
        try {
            Sensor sensor = sensorRepository.findById(newSensor.getId());
            if (sensor.getAcceptedValueMax()<newSensor.getAcceptedValueMin()) {
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
            if (newSensor.getAcceptedValueMax()<sensor.getAcceptedValueMin()) {
                sensor.setAcceptedValueMax(newSensor.getAcceptedValueMax());
                sensorRepository.save(sensor);
              //  sendEmail();
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

        } catch (Exception e) {
            e.printStackTrace();
            return("Data hasn't been changed.");
        }
        return("Temperature changed.");
    }


    public String sendEmail(){
        Properties prop =new Properties();
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587");
        prop.put("mail.smtp.ssl.trust", "*");

        final String username = "";
        final String password = "asd1fgh2jkl3";
        final String fromEmail= "";
        final String toEmail = "";
        final String subject = "Spoważniej";
        final String text = "Spoważniej";
        Session session = Session.getDefaultInstance(prop, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username,password);
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
