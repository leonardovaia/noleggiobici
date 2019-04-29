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
@Table(name="stazioni")
public class Stazione{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idStazione;
    String indirizzo;
    String cap;


    @OneToMany(mappedBy = "slotStazione",cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Slot> slotStazione;
}

