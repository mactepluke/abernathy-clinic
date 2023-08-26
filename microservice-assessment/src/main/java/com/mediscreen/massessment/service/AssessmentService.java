package com.mediscreen.massessment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {
    @Value("#{'${triggering_terms}'.split(',')}")
    private List<String> triggeringTerms;



}
