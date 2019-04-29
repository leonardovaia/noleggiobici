package com.noleggiobiciclette.dao;
import com.noleggiobiciclette.domain.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface SlotDao extends JpaRepository<Slot,Long>{


    
}