package com.example.booking.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="reservations", indexes={@Index(name="idx_res_status", columnList="status"), @Index(name="idx_res_price", columnList="price")})
public class Reservation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="resource_id", nullable=false) private Resource resource;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="user_id", nullable=false) private AppUser user;
    @Column(nullable=false) private LocalDateTime startTime;
    @Column(nullable=false) private LocalDateTime endTime;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal price;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private ReservationStatus status = ReservationStatus.PENDING;
    public Long getId(){return id;} public Resource getResource(){return resource;} public void setResource(Resource v){resource=v;}
    public AppUser getUser(){return user;} public void setUser(AppUser v){user=v;} public LocalDateTime getStartTime(){return startTime;} public void setStartTime(LocalDateTime v){startTime=v;}
    public LocalDateTime getEndTime(){return endTime;} public void setEndTime(LocalDateTime v){endTime=v;} public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
    public ReservationStatus getStatus(){return status;} public void setStatus(ReservationStatus v){status=v;}
}
