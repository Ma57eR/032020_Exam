package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import softuni.exam.models.entity.Car;

import javax.validation.constraints.Size;

public class ImportPicturesDto {

    @Expose
    @Size(min = 2, max = 19)
    private String name;

    @Expose
    private String dateAndTime;

    @Expose
    private Long car;

    public ImportPicturesDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataAndTime() {
        return dateAndTime;
    }

    public void setDataAndTime(String dataAndTime) {
        this.dateAndTime = dataAndTime;
    }

    public Long getCar() {
        return car;
    }

    public void setCar(Long car) {
        this.car = car;
    }
}
