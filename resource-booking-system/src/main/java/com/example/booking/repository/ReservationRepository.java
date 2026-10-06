package com.example.booking.repository;
import com.example.booking.entity.*; 
import java.util.*; 
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReservationRepository extends JpaRepository<Reservation,Long>, JpaSpecificationExecutor<Reservation>{
  Optional<Reservation> findByIdAndUserUsername(Long id,String username);
}
