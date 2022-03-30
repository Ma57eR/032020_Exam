package softuni.exam.models.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "cars", uniqueConstraints = @UniqueConstraint(columnNames = {"make", "model", "kilometers"}))
public class Car extends BaseEntity{

    @Column(length = 20)
    private String make;

    @Column(length = 20)
    private String model;

    @Column()
    private Integer kilometers;

    @Column(name = "registered_on")
    private LocalDate registeredOn;

    public Car() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }
}
