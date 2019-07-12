package softuni.workshop.service;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.importDto.CompanyDto;
import softuni.workshop.domain.dtos.importDto.CompanyRootDto;
import softuni.workshop.domain.dtos.jsonDtos.exportDto.CompanyJsonDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final static String COMPANIES_XML_FILE_PATH =
            "C:\\SoftUni\\DB\\workshop-skeleton\\src\\main\\resources\\files\\xmls\\companies.xml";
    private final static String COMPANIES_JSON_FILE_PATH =
            "C:\\SoftUni\\DB\\workshop-skeleton\\src\\main\\resources\\files\\jsons\\companies.json";

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validatorUtil, FileUtil fileUtil, Gson gson) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public void importCompanies() throws JAXBException {

        CompanyRootDto companyRootDto = this.xmlParser.importXMl(CompanyRootDto.class,COMPANIES_XML_FILE_PATH);

        for (CompanyDto companyDto : companyRootDto.getCompanyDtos()) {
            Company company = this.modelMapper.map(companyDto, Company.class);
            if (!this.validatorUtil.isValid(company)){
                this.validatorUtil.violations(company).forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            this.companyRepository.saveAndFlush(company);
        }
    }

    @Override
    public boolean areImported() {
       return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {
        return this.fileUtil.readFile(COMPANIES_XML_FILE_PATH);
    }


    @Override
    public void exportJsonCompanies() throws IOException {
        List<CompanyJsonDto> companies = this.companyRepository.findAll()
                .stream()
                .map(c->this.modelMapper.map(c, CompanyJsonDto.class))
                .collect(Collectors.toList());

        String content = this.gson.toJson(companies);
        FileWriter writer = new FileWriter(new File(COMPANIES_JSON_FILE_PATH));
        writer.write(content);

        writer.close();
    }

    @Override
    public String readCompaniesJsonFile() throws IOException {
        return this.fileUtil.readFile(COMPANIES_JSON_FILE_PATH);
    }

    @Override
    public boolean areExported() throws IOException {
       return this.readCompaniesJsonFile().length() > 0;
    }
}
