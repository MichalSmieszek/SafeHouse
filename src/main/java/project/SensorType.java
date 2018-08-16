package project;

import javax.persistence.*;

@Entity
@Table(name="SensorType")
public class SensorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;

    public int getId() {
        return id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
