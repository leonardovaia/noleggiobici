package com.noleggiobiciclette.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.noleggiobiciclette.dao.BiciclettaDao;
import com.noleggiobiciclette.dao.NoleggioDao;
import com.noleggiobiciclette.dao.SlotDao;
import com.noleggiobiciclette.dao.UtenteDao;
import com.noleggiobiciclette.domain.Noleggio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
//@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping(path="/api/noleggi",produces = "application/json")
public class NoleggioController{

    @Autowired
    NoleggioDao noleggioDao;
    @Autowired
    UtenteDao utenteDao;
    @Autowired
    BiciclettaDao biciclettaDao;
    @Autowired 
    SlotDao slotDao;

    ///// noleggio  biciletta ////////////////////////////
    @PostMapping(path="/noleggio/idUtente/{idUtente}/idBicicletta/{idBicicletta}/idSlot/{idSlot}")
    public ResponseEntity noleggioBicicletta( @PathVariable Long idUtente, 
                                        @PathVariable Long idBicicletta,
                                        @PathVariable Long idSlot)  {
        if (this.noleggioDao.noleggioBicicletta(LocalDateTime.now(), idUtente, idBicicletta, idSlot) !=0 )
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    ///// restituzione  biciletta ////////////////////////////
    @PostMapping(path="/restituzione/idUtente/{idUtente}/idSlot/{idSlot}")
    public ResponseEntity restituzioneBicicletta( @PathVariable Long idUtente, 
                                                  @PathVariable Long idSlot)  {
        if (this.noleggioDao.restituzioneBicicletta(LocalDateTime.now(), idUtente, idSlot) !=0 )
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    
    @GetMapping(value="")
    public List<Noleggio> getutenti() {
        return this.noleggioDao.findAll();
    }

    @GetMapping(value="/id/{idNoleggio}")
    public Noleggio getNoleggio(@PathVariable Long idNoleggio) {
        return this.noleggioDao.findById(idNoleggio).get();
    }


    @PostMapping(value="/save")
    public Noleggio postMethodName(@RequestBody Noleggio noleggio) {
        return this.noleggioDao.save(noleggio);
    }
    
    @PutMapping(value="/update")
    public Noleggio putMethodName(@RequestBody Noleggio noleggio) {
        return this.noleggioDao.saveAndFlush(noleggio);
    }

    @DeleteMapping(path="/id/{idNoleggio}")
    public ResponseEntity deleteNoleggio(@PathVariable Long idNoleggio) {
        return this.noleggioDao.findById(idNoleggio).map(noleggio -> {
            this.noleggioDao.delete(noleggio);
            return ResponseEntity.ok().build();
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
    }
}