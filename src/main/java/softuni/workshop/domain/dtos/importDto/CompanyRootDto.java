package softuni.workshop.domain.dtos.importDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyRootDto {

    @XmlElement(name = "company")
    private List<CompanyDto> companyDtos;

    public CompanyRootDto(){}

    public List<CompanyDto> getCompanyDtos() {
        return companyDtos;
    }

    public void setCompanyDtos(List<CompanyDto> companyDtos) {
        this.companyDtos = companyDtos;
    }
}
