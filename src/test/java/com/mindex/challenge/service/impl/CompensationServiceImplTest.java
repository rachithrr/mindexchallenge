package com.mindex.challenge.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import com.mindex.challenge.Constants;
import com.mindex.challenge.Constants;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

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
public class CompensationServiceImplTest {
    private String postCompensationUrl;
    private String getCompensationUrl;
    
    @Autowired
    CompensationRepository mCompensationRepository;

    @Autowired
    EmployeeRepository mEmployeeRepository;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int mPort;

    @Before
    public void setup(){
        postCompensationUrl = Constants.LOCAL_HOST + mPort + Constants.COMPENSATION;
        getCompensationUrl = Constants.LOCAL_HOST + mPort + Constants.COMPENSATION + Constants.SAPERATOR;
    }

    @Test
    public void testCreateRead(){
        
        testCompensationForEmployee(Constants.POST_EMPLOYEE_ONE, Constants.TEST_SALARY_ONE, Constants.TEST_EFFDATE_ONE);
        //System.out.println("Hawa  Compensation done" );
    }

    private void testCompensationForEmployee(String employeeId, int salary, String date){
        Employee employee = mEmployeeRepository.findByEmployeeId(employeeId);
        Compensation actualCompensation = new Compensation(employee, salary, LocalDate.parse(date));
        
        //System.out.println("Hawa   "+postCompensationUrl);
        Compensation restCompensation = restTemplate.postForEntity(
            postCompensationUrl, actualCompensation, Compensation.class).getBody();
        
        assertNotNull(restCompensation);
        assertCompensationEquivalence(actualCompensation, restCompensation);

        //System.out.println("Hawa  " + getCompensationUrl + employeeId);
        restCompensation = restTemplate.getForEntity(getCompensationUrl + employeeId, Compensation.class, employeeId).getBody();
        assertNotNull(restCompensation);
        //System.out.println("Hawa "+ restCompensation);
        assertCompensationEquivalence(actualCompensation, restCompensation);
    }

    private void assertCompensationEquivalence(Compensation actualCompensation, Compensation restCompensation) {
        
        assertEmployeeEquivalence(actualCompensation.getEmployee(), restCompensation.getEmployee());
        //System.out.println("Hawa "+actualCompensation.getSalary() + "  "+restCompensation.getSalary());
        //System.out.println("Hawa  "+restCompensation.getEffectiveDate() + "       ");
        assertEquals(actualCompensation.getSalary(), restCompensation.getSalary());
        assertEquals(actualCompensation.getEffectiveDate(), restCompensation.getEffectiveDate());

    }

    private void assertEmployeeEquivalence(Employee actualEmployee, Employee restEmployee) {
        if(restEmployee == null || actualEmployee == null) return;
        assertEquals(restEmployee.getFirstName(), actualEmployee.getFirstName());
        assertEquals(restEmployee.getLastName(), actualEmployee.getLastName());
        assertEquals(restEmployee.getDepartment(), actualEmployee.getDepartment());
        assertEquals(restEmployee.getPosition(), actualEmployee.getPosition());
    }
    
}
