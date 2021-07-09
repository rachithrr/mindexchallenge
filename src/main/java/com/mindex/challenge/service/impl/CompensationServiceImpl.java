package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService{
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository mCompensationRepository;

    @Autowired
    private EmployeeRepository mEmployeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]"+compensation);
        return mCompensationRepository.insert(compensation);
    }

    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Reading compensation for employeeID [{}]", employeeId);

        Employee mEmployee = mEmployeeRepository.findByEmployeeId(employeeId);

        if (mEmployee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        Compensation compensation = mCompensationRepository.findByEmployee(mEmployee);
        return compensation;
    }
    
}
