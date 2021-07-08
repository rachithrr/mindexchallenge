package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportingStructureController {

    @Autowired
    private ReportingStructureService mReportingStructureService;

    @GetMapping("/employee/{id}/reporting-structure")
    public ReportingStructure getReportingStructure(@PathVariable String id){
        return mReportingStructureService.getReportingStructure(id);
    }
    
}