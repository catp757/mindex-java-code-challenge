package com.mindex.challenge.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Component
@NoArgsConstructor
@Accessors(chain = true)
public class Compensation {
    @Id
    private String employeeId;
    @NotNull
    private BigDecimal salary;
    @NotNull
    private Date effectiveDate;
}
