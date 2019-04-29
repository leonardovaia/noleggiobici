package com.noleggiobiciclette.controllers;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.noleggiobiciclette.dao.UtenteDao;
import com.noleggiobiciclette.domain.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path="/api/utenti",produces = "application/json")
public class UtenteController{

    @Autowired
    UtenteDao utenteDao;
    @GetMapping(value="")
    public List<Utente> getutenti() {
        return this.utenteDao.findAll();
    }

    @GetMapping(value="/id/{idUtente}")
    public Utente getUtente(@PathVariable Long idUtente) {
        return this.utenteDao.findById(idUtente).get();
    }

    @PostMapping(value="/save")
    public Utente postMethodName(@RequestBody Utente utente) {
        utente.setPassword(BCrypt.hashpw(utente.getPassword(), BCrypt.gensalt()));
        return this.utenteDao.save(utente);
    }
    
    @PutMapping(value="/update")
    public Utente putMethodName(@RequestBody Utente utente) {
        utente.setPassword(BCrypt.hashpw(utente.getPassword(), BCrypt.gensalt()));
        return this.utenteDao.saveAndFlush(utente);
    }

    @DeleteMapping(path="/id/{idUtente}")
    public ResponseEntity deleteUtente(@PathVariable Long idUtente) {
        return this.utenteDao.findById(idUtente).map(utente -> {
            this.utenteDao.delete(utente);
            return ResponseEntity.ok().build();
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

//  @RequestMapping(value = "/login/failed")
// public String loginfailed(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String user, @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY) String password) {
//      // Your code here
// }
}