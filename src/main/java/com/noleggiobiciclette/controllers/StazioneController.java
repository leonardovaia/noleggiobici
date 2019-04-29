package com.noleggiobiciclette.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponses;

import java.util.List;

import com.noleggiobiciclette.controllers.query.dao.BiciclettaDisponibileDao;
import com.noleggiobiciclette.controllers.query.views.BiciclettaDisponibile;
import com.noleggiobiciclette.dao.StazioneDao;
import com.noleggiobiciclette.domain.Stazione;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path="/api/stazioni",produces = "application/json")
//@Secured({"ROLE_ADMIN"})
public class StazioneController{

    @Autowired
    StazioneDao stazioneDao;

    @Autowired
    BiciclettaDisponibileDao biciclettaDisponibileDao;
    @GetMapping(value="")
    //@PreAuthorize(value = "hasRole('ADMIN')")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")

    public List<Stazione> getStazioni() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities() + " - " +authentication.getName() +" - "+authentication.getPrincipal().toString());
        return this.stazioneDao.findAll();
    }

    @GetMapping(value="/id/{idStazione}")

    public Stazione getStazione(@PathVariable Long idStazione) {
        return this.stazioneDao.findById(idStazione).get();
    }

    @PostMapping(value="/save")
    public Stazione postMethodName(@RequestBody Stazione stazione) {
        return this.stazioneDao.save(stazione);
    }
    
    @PutMapping(value="/update")
    public Stazione putMethodName(@RequestBody Stazione stazione) {
        return this.stazioneDao.saveAndFlush(stazione);
    }

    @DeleteMapping(path="/id/{idStazione}")
    public ResponseEntity deleteStazione(@PathVariable Long idStazione) {
        return this.stazioneDao.findById(idStazione).map(stazione -> {
            this.stazioneDao.delete(stazione);
            return ResponseEntity.ok().build();
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value="/biciclettaDisponibile/id/{idStazione}")
    public BiciclettaDisponibile getBiciclettaDisponibile(@PathVariable Long idStazione) {
        return this.biciclettaDisponibileDao.biciclettaDisponibile(idStazione);
    }
}