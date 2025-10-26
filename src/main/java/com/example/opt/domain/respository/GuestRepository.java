package com.example.opt.domain.respository;

import com.example.opt.domain.Entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    @Query("select count(*) from Guest")
    int findTotalCount();

    List<Guest> findAllByTypeCode(String typeCode);
}
