package com.sda.gda17;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Payer extends SingleSaver{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String nip;

    @ManyToMany(mappedBy = "payersList")
    private List<Company> companyList;


}
