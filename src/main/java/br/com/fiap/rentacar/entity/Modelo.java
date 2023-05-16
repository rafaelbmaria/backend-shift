package br.com.fiap.rentacar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "TBL_MODELO")
public class Modelo {

    @Id
    @Column(name = "ID_MODELO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TX_NOME")
    private String nome;

    @Column(name = "NR_POTENCIA")
    private BigDecimal potencia;
    //private Double potencia;

    @Transient
    private Marca marca;

}
