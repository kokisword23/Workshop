package softuni.workshop.domain.dtos.jsonDtos.exportDto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class ProjectJsonDto {

    @Expose
    private String name;

    @Expose
    private String description;

    @Expose
    @SerializedName(value = "is-finished")
    private Boolean isFinished;

    @Expose
    private BigDecimal payment;

    @Expose
    @SerializedName(value = "start-date")
    private String startDate;

    @Expose
    private CompanyJsonDto company;

    public ProjectJsonDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public CompanyJsonDto getCompany() {
        return company;
    }

    public void setCompany(CompanyJsonDto company) {
        this.company = company;
    }
}
