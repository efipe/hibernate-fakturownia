package com.sda.gda17;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Invoice {
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private long id;


    private String name;
    private Date dateOfIssue;
    private Date paymentDeadline;
    private double amount;
    private long issuerNIP;
    private long payerNIP;


}
