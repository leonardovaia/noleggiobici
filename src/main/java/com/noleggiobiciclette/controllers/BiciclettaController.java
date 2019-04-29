package com.noleggiobiciclette.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.noleggiobiciclette.controllers.query.dao.ViewBiciclettaInUsoDao;
import com.noleggiobiciclette.controllers.query.views.ViewBiciclettaInUso;
import com.noleggiobiciclette.dao.BiciclettaDao;
import com.noleggiobiciclette.domain.Bicicletta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin
@RestController
@RequestMapping(path="/api/biciclette",produces = "application/json")
public class BiciclettaController{

    @Autowired
    BiciclettaDao biciclettaDao;
    @Autowired ViewBiciclettaInUsoDao viewBiciclettaInUsoDao;
    @GetMapping(value="")
    public List<Bicicletta> getutenti() {
        return this.biciclettaDao.findAll();
    }

    @GetMapping(value="/id/{idBicicletta}")
    public Bicicletta getBicicletta(@PathVariable Long idBicicletta) {
        return this.biciclettaDao.findById(idBicicletta).get();
    }

    @PostMapping(value="/save")
    public Bicicletta postMethodName(@RequestBody Bicicletta bicicletta) {
        return this.biciclettaDao.save(bicicletta);
    }
    
    @PutMapping(value="/update")
    public Bicicletta putMethodName(@RequestBody Bicicletta bicicletta) {
        return this.biciclettaDao.saveAndFlush(bicicletta);
    }

    @DeleteMapping(path="/id/{idBicicletta}")
    public ResponseEntity deleteBicicletta(@PathVariable Long idBicicletta) {
        return this.biciclettaDao.findById(idBicicletta).map(bicicletta -> {
            this.biciclettaDao.delete(bicicletta);
            return ResponseEntity.ok().build();
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value="/bicicletteinuso")
    public List<ViewBiciclettaInUso> listBiciclettaInUso() {
        return this.viewBiciclettaInUsoDao.findAll();
    }
    
}