package com.mindex.challenge.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Component
@NoArgsConstructor
@Accessors(chain = true)
public class Employee {
    @Id
    private String employeeId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String position;
    @NotNull
    private String department;
    private List<String> directReports;

}
