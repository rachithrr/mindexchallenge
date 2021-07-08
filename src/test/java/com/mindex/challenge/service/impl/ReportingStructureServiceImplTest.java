package com.mindex.challenge.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.mindex.challenge.Constants;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
    private String getReportingStructureUrl;

    @Autowired
    EmployeeRepository mEmployeeRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int mPort;

    @Before
    public void setup(){
        getReportingStructureUrl = Constants.LOCAL_HOST + mPort + Constants.EMPLOYEE + 
        Constants.SAPERATOR + Constants.TEST_EMPLOYEE_ONE + Constants.REPORTING_STRUCTURE; 
    }

    @Test
    public void testReadMethod(){
        Employee actualEmployee = mEmployeeRepository.findByEmployeeId(Constants.TEST_EMPLOYEE_ONE);
        Employee employee1 = mEmployeeRepository.findByEmployeeId(Constants.EMPLOYEE1);
        Employee employee2 = mEmployeeRepository.findByEmployeeId(Constants.EMPLOYEE2);
        Employee employee21 = mEmployeeRepository.findByEmployeeId(Constants.EMPLOYEE21);
        Employee employee22 = mEmployeeRepository.findByEmployeeId(Constants.EMPLOYEE22);
        List<Employee> dirReports2 = new ArrayList<Employee>();
        dirReports2.add(employee21);
        dirReports2.add(employee22);
        employee2.setDirectReports(dirReports2);
        List<Employee> dirReports = new ArrayList<Employee>();
        dirReports.add(employee1);
        dirReports.add(employee2);
        actualEmployee.setDirectReports(dirReports);

        ReportingStructure actualReportingStructure = new ReportingStructure(actualEmployee, Constants.NUMBER_OF_REPORTS);

        ReportingStructure restReportingStructure = restTemplate.getForEntity(getReportingStructureUrl, 
            ReportingStructure.class, actualReportingStructure.getEmployee().getEmployeeId()).getBody();
        
        assertNotNull(restReportingStructure);
        assertReportingStructureEquivalance(actualReportingStructure, restReportingStructure);
        System.out.println("Hawa  reportingStructure Done");
    }

    private void assertReportingStructureEquivalance(ReportingStructure actualReportingStructure,
            ReportingStructure restReportingStructure) {

        assertEquals(actualReportingStructure.getNumberOfReports(), restReportingStructure.getNumberOfReports());
        assertEmployeeEquivalence(actualReportingStructure.getEmployee(), restReportingStructure.getEmployee());
    }

    private void assertEmployeeEquivalence(Employee actualEmployee, Employee restEmployee) {
        assertEquals(restEmployee.getFirstName(), actualEmployee.getFirstName());
        assertEquals(restEmployee.getLastName(), actualEmployee.getLastName());
        assertEquals(restEmployee.getDepartment(), actualEmployee.getDepartment());
        assertEquals(restEmployee.getPosition(), actualEmployee.getPosition());
        if(restEmployee.getDirectReports() != null && actualEmployee.getDirectReports() != null){
        assertEquals(restEmployee.getDirectReports().size(), actualEmployee.getDirectReports().size());
        for(int i = 0 ; i < restEmployee.getDirectReports().size(); i++){
            assertEmployeeEquivalence(actualEmployee.getDirectReports().get(i), restEmployee.getDirectReports().get(i));
        }
    }
    }
    
    
}
