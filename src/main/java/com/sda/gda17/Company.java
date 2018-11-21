package com.sda.gda17;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String NIP;
    private String name;
    private String Address;

    @OneToMany(mappedBy = "company",fetch = FetchType.EAGER)
    private List<Invoice> invoiceList;


}
