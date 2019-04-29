package com.noleggiobiciclette.controllers.query.views;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class BiciclettaDisponibile{
    @Id
    Long idStazione;
    int numeroBiciclette; 
}