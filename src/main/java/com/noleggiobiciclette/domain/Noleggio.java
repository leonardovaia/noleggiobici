package com.noleggiobiciclette.domain;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Setter @Getter
@Entity
@Table(name="noleggi")
public class Noleggio{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idNoleggio;
  
    LocalDateTime dataInizio;
    LocalDateTime dataFine;

    @ManyToOne
    @JoinColumn(name="idUtente",nullable=false)
    Utente utente;

    @ManyToOne
    @JoinColumn(name="idBicicletta",nullable=false)
    Bicicletta bicicletta;

    @ManyToOne
    @JoinColumn(name="slotNoleggioPartenza",nullable=false)
    Slot slotNoleggioPartenza;

    @ManyToOne
    @JoinColumn(name="slotNoleggioArrivo",nullable=true)
    Slot slotNoleggioArrivo;
}