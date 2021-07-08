package com.mindex.challenge.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure getReportingStructure(String id) {
        
        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        employee = fillEmployee(employee);
        int numberOfReports = getNumberOfReports(employee);

        ReportingStructure reportingStructure = new ReportingStructure(employee, numberOfReports);
        return reportingStructure;
    }

    private Employee fillEmployee(Employee employee){
        if(employee == null) return null;
        List<Employee> dirReportsList = employee.getDirectReports();
        if(dirReportsList == null) return employee;

        List<Employee> filledDirReports = new ArrayList<Employee>();
        for(Employee dirReport : dirReportsList){
            Employee dirEmployee = employeeRepository.findByEmployeeId(dirReport.getEmployeeId());
            fillEmployee(dirEmployee);
            filledDirReports.add(dirEmployee);
        }

        employee.setDirectReports(filledDirReports);
        return employee;

    }

    private int getNumberOfReports(Employee employee) {
        if(employee == null) return 0;
        List<Employee> directReportsList = employee.getDirectReports();
        if(directReportsList == null) return 0;
        int rep = directReportsList.size();

        for(Employee directReport : directReportsList){
            if(directReport == null) continue;
            Employee dirEmployee = employeeRepository.findByEmployeeId(directReport.getEmployeeId());
            rep += getNumberOfReports(dirEmployee);
        }
        return rep;
    }
    
}
