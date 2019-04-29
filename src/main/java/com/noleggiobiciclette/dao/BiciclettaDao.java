package com.noleggiobiciclette.dao;

import com.noleggiobiciclette.domain.Bicicletta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BiciclettaDao extends JpaRepository<Bicicletta,Long>{}