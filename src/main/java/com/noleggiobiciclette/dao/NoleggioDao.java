package com.noleggiobiciclette.dao;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import com.google.common.base.Optional;
import com.noleggiobiciclette.domain.Noleggio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface NoleggioDao extends JpaRepository<Noleggio,Long>{

    @Query("SELECT n FROM Noleggio n WHERE n.utente.idUtente=?1 and n.dataFine=null")
    Optional<Noleggio> findNoleggioByIdUtente(Long idUtente);

    @Modifying
    @Transactional // Spring apre la transazione all'inizio e la chiude alla fine
    @Query(value="Insert into Noleggi(data_Inizio,id_Utente,id_Bicicletta,slot_Noleggio_Partenza) values(?1,?2,?3,?4)",nativeQuery = true)
    int noleggioBicicletta(LocalDateTime dataInizio,Long idUtente,Long idBicicletta,Long slotNoleggioPartenza);

    @Modifying
    @Transactional 
    @Query(value="update Noleggi set data_fine=:dataFine,slot_Noleggio_arrivo=:slotNoleggioArrivo where id_utente=:idUtente",nativeQuery = true)
    int restituzioneBicicletta(@Param("dataFine") LocalDateTime dataFine,@Param("idUtente") Long idUtente,@Param("slotNoleggioArrivo") Long slotNoleggioArrivo);

}