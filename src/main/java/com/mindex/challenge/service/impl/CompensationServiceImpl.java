package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService{

    @Autowired
    private CompensationRepository mCompensationRepository;

    @Autowired
    private EmployeeRepository mEmployeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        return mCompensationRepository.insert(compensation);
    }

    @Override
    public Compensation read(String employeeId) {
        Employee mEmployee = mEmployeeRepository.findByEmployeeId(employeeId);

        Compensation compensation = mCompensationRepository.findByEmployee(mEmployee);
        return compensation;
    }
    
}
