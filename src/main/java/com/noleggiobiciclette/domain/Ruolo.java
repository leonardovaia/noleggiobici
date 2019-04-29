package com.noleggiobiciclette.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ruoli")
public class Ruolo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_ruolo")
	private int idRuolo;
	private String ruolo;
}