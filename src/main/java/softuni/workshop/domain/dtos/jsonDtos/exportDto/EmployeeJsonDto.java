package softuni.workshop.domain.dtos.jsonDtos.exportDto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeJsonDto {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Integer age;

    @Expose
    private ProjectJsonDto projectJsonDto;

    public EmployeeJsonDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProjectJsonDto getProjectJsonDto() {
        return projectJsonDto;
    }

    public void setProjectJsonDto(ProjectJsonDto projectJsonDto) {
        this.projectJsonDto = projectJsonDto;
    }
}
