package project.Model;

import javax.persistence.*;

@Entity
@Table(name="Measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Sensor sensor;
    private double value;


    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public int getId() {
        return id;
    }
}
