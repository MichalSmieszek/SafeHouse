package project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;

@Entity
@Table(name="Sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double value;
    private double acceptedValueMin;
    private double acceptedValueMax;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "SensorTypeId")
    private SensorType sensorType;
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getAcceptedValueMin() {
        return acceptedValueMin;
    }

    public void setAcceptedValueMin(double acceptedValueMin) {
        this.acceptedValueMin = acceptedValueMin;
    }

    public double getAcceptedValueMax() {
        return acceptedValueMax;
    }

    public void setAcceptedValueMax(double acceptedValueMax) {
        this.acceptedValueMax = acceptedValueMax;
    }


    public int getId() {
        return id;
    }
    public SensorType getType() {
        return sensorType;
    }

    public void setType(SensorType type) {
        this.sensorType = type;
    }
}
