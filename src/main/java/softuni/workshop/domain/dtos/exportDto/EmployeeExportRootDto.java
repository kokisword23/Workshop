package softuni.workshop.domain.dtos.exportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeExportRootDto {

    @XmlElement(name = "employee")
    private List<EmployeeExportDto> employeeExportDtos;

    public EmployeeExportRootDto(){}

    public List<EmployeeExportDto> getEmployeeExportDtos() {
        return employeeExportDtos;
    }

    public void setEmployeeExportDtos(List<EmployeeExportDto> employeeExportDtos) {
        this.employeeExportDtos = employeeExportDtos;
    }
}
