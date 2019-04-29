package com.noleggiobiciclette.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
@Entity
@Table(name="biciclette")
public class Bicicletta{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idBicicletta;
    String modello;
    int annoDiProduzione;

    @OneToMany(mappedBy = "bicicletta",cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Noleggio> noleggio;

    @OneToMany(mappedBy = "slotBicicletta",cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Slot> slotBicicletta;
}
