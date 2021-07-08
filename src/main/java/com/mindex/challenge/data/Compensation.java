package com.mindex.challenge.data;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Compensation {

    private Employee employee;
    private int salary;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDate;

    public Compensation(Employee employee, int salary, LocalDate effectiveDate){
        setEmployee(employee);
        setSalary(salary);
        setEffectiveDate(effectiveDate);
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate mEffectiveDate) {
        this.effectiveDate = mEffectiveDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int mSalary) {
        this.salary = mSalary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee mEmployee) {
        this.employee = mEmployee;
    }
    
}