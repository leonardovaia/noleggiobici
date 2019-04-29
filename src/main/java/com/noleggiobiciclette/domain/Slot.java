package com.noleggiobiciclette.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name="slots")
public class Slot{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idSlot;
    Boolean stato;
    
    @ManyToOne
    @JoinColumn(name="slotBicicletta",nullable=true)
    Bicicletta slotBicicletta;
    
    @OneToMany(mappedBy = "slotNoleggioPartenza")
    @JsonIgnore
    Set<Noleggio> noleggioPartenza;

    @OneToMany(mappedBy = "slotNoleggioArrivo")
    @JsonIgnore
    Set<Noleggio> noleggioArrivo;


    @ManyToOne
    @JoinColumn(name="slotStazione",nullable = false)
    Stazione slotStazione;
}