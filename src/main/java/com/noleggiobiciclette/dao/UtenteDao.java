package com.noleggiobiciclette.dao;

import com.noleggiobiciclette.domain.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UtenteDao extends JpaRepository<Utente,Long>{
    Utente findBymail(String mail);
}