package com.noleggiobiciclette.controllers.query.dao;
import com.noleggiobiciclette.controllers.query.views.BiciclettaDisponibile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface BiciclettaDisponibileDao extends JpaRepository<BiciclettaDisponibile,Long>{
    @Query(value="select id_stazione,count(*) as numero_Biciclette from stazioni st join slots sl on st.id_stazione=sl.slot_stazione where id_stazione=?1",nativeQuery=true) 
    BiciclettaDisponibile biciclettaDisponibile(Long idStazione);
}