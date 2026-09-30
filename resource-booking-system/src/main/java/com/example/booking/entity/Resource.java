package com.example.booking.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity @Table(name="resources")
public class Resource {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=120) private String name;
    @Column(length=1000) private String description;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal price;
    @Column(nullable=false) private boolean available = true;
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
    public boolean isAvailable(){return available;} public void setAvailable(boolean v){available=v;}
}
