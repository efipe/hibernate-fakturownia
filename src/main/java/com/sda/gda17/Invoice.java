package com.sda.gda17;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Invoice {
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private Long id;


    private String name;
    private LocalDate dateOfIssue;
    private LocalDate paymentDeadline;
    private double amount;
    private long issuerNIP;
    private long payerNIP;


}
