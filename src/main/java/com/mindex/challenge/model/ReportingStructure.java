package com.mindex.challenge.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@Accessors(chain = true)
public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;
}
