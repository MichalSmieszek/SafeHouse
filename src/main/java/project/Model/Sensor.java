package project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="Sensor")
public class Sensor {
    public Sensor(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double value;
    private double acceptedValueMin;
    private double acceptedValueMax;
    private String name;
    private double posX;
    private double posY;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

}
