package softuni.workshop.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project extends BaseEntity{

   private String name;
   private String description;
   private Boolean isFinished;
   private BigDecimal payment;
   private String startDate;
   private Company company;
   private List<Employee> employees;

    public Project() {
    }

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 15, message = "Username is not valid")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false)
    @Size(min = 5, max = 1000, message = "Description is not valid")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "is_finished", nullable = false)
    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    @Column(name = "payment", nullable = false)
    @Min(value = 1500, message = "Payment is too low")
    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    @Column(name = "start_date",nullable = false)
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id",referencedColumnName = "id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
