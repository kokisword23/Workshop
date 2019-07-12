package softuni.workshop.domain.dtos.jsonDtos.exportDto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class CompanyJsonDto implements Serializable {

    @Expose
    private String name;

    public CompanyJsonDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
