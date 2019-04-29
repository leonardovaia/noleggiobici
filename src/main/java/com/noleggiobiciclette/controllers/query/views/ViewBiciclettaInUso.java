package com.noleggiobiciclette.controllers.query.views;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Getter 
@Entity
@Table(name="view_biciclette_in_uso")
public class ViewBiciclettaInUso{
    @Id
    Long idBicicletta;
    String nome;
    String cognome;
    Long idStazione;
}