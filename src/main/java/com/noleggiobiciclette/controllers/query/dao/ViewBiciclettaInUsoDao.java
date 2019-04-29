package com.noleggiobiciclette.controllers.query.dao;
import com.noleggiobiciclette.controllers.query.views.ViewBiciclettaInUso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewBiciclettaInUsoDao extends JpaRepository<ViewBiciclettaInUso,Long>{}