package com.mindex.challenge.data;

public class ReportingStructure {
    private Employee mEmployee;
    private int mNumberOfReports;

    public ReportingStructure(Employee employee, int numberOfReports){
        setEmployee(employee);
        setNumberOfReports(numberOfReports);
    }

    public Employee getEmployee() {
        return mEmployee;
    }

    public void setEmployee(Employee mEmployee) {
        this.mEmployee = mEmployee;
    }

    public int getNumberOfReports() {
        return mNumberOfReports;
    }

    public void setNumberOfReports(int mNumberOfReports) {
        this.mNumberOfReports = mNumberOfReports;
    }
}
