package com.noleggiobiciclette.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.noleggiobiciclette.dao.SlotDao;
import com.noleggiobiciclette.domain.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path="/api/slots",produces = "application/json")
public class SlotController{

    @Autowired
    SlotDao slotDao;
    @GetMapping(value="")
    public List<Slot> getutenti() {
        return this.slotDao.findAll();
    }

    @GetMapping(value="/id/{idSlot}")
    public Slot getSlot(@PathVariable Long idSlot) {
        return this.slotDao.findById(idSlot).get();
    }

    @PostMapping(value="/save")
    public Slot postMethodName(@RequestBody Slot slot) {
        return this.slotDao.save(slot);
    }
    
    @PutMapping(value="/update")
    public Slot putMethodName(@RequestBody Slot slot) {
        return this.slotDao.saveAndFlush(slot);
    }

    @DeleteMapping(path="/id/{idSlot}")
    public ResponseEntity deleteSlot(@PathVariable Long idSlot) {
        return this.slotDao.findById(idSlot).map(slot -> {
            this.slotDao.delete(slot);
            return ResponseEntity.ok().build();
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
    }
}