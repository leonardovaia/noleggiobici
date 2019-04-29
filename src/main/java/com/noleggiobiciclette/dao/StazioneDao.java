package com.noleggiobiciclette.dao;
import com.noleggiobiciclette.domain.Stazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface StazioneDao extends JpaRepository<Stazione,Long>{}
