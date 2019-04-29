package com.noleggiobiciclette.domain;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
@Entity
@Table(name="utenti")
public class Utente{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUtente;
    String nome;
    String cognome;
    String cap;
    String indirizzo;
    String numCartaDiCredito;
    String cellulare;
    String mail;
    String password;
    boolean enabled;
    String role;
    Date   dataDiNascita;   

    @OneToMany(mappedBy="utente",cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Noleggio> noleggio;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "utenti_ruoli", 
        joinColumns = @JoinColumn(name = "id_utente"), 
        inverseJoinColumns = @JoinColumn(name = "id_ruolo"))
	private Set<Ruolo> ruoli;
}