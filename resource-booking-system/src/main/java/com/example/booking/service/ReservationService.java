package com.example.booking.service;

import com.example.booking.dto.*; 
import com.example.booking.entity.*; 
import com.example.booking.exception.*; 
import com.example.booking.repository.*;
import com.example.booking.specification.ReservationSpecifications; 
import org.springframework.data.domain.*; 
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service; 
import java.math.BigDecimal;

@Service
 public class ReservationService{
 private final ReservationRepository reservations; 
  private final ResourceRepository resources; 
  private final UserRepository users;
 public ReservationService(ReservationRepository r,ResourceRepository rr,UserRepository u)
  {
   reservations=r;resources=rr;users=u;
  }
 public ReservationResponse create(String username,ReservationRequest req)
  {validateTimes(req.startTime(),req.endTime());
   AppUser user=user(username);
   Resource resource=resources.findById(req.resourceId()).orElseThrow(()->new NotFoundException("Resource not found"));
   if(!resource.isAvailable())throw new BadRequestException("Resource is not available");
   ensureNoOverlap(resource.getId(),req.startTime(),req.endTime(),null);
   Reservation x=new Reservation();x.setUser(user);x.setResource(resource);x.setStartTime(req.startTime());
   x.setEndTime(req.endTime());x.setPrice(req.price());
   if(req.status()!=null&&req.status()!=ReservationStatus.PENDING)throw new BadRequestException("USER reservations must start with PENDING status");
   x.setStatus(ReservationStatus.PENDING);return dto(reservations.save(x));
  }
 public Page<ReservationResponse> userReservations(String username,ReservationStatus status,BigDecimal min,BigDecimal max,Pageable p)
  {
   return search(username,status,min,max,p);
  }
 public Page<ReservationResponse> adminReservations(ReservationStatus status,BigDecimal min,BigDecimal max,Pageable p){return search(null,status,min,max,p);}
 public ReservationResponse getForUser(Long id,String username){Reservation x=reservations.findByIdAndUserUsername(id,username).orElseThrow(()->new NotFoundException("Reservation not found"));return dto(x);}
 public ReservationResponse adminGet(Long id){return dto(find(id));}
 public ReservationResponse adminUpdate(Long id,AdminReservationRequest req){validateTimes(req.startTime(),req.endTime());Reservation x=find(id);AppUser user=users.findById(req.userId()).orElseThrow(()->new NotFoundException("User not found"));Resource resource=resources.findById(req.resourceId()).orElseThrow(()->new NotFoundException("Resource not found"));ensureNoOverlap(resource.getId(),req.startTime(),req.endTime(),id);x.setUser(user);x.setResource(resource);x.setStartTime(req.startTime());x.setEndTime(req.endTime());x.setPrice(req.price());if(req.status()!=null)x.setStatus(req.status());return dto(reservations.save(x));}
 public void adminDelete(Long id){if(!reservations.existsById(id))throw new NotFoundException("Reservation not found");reservations.deleteById(id);}
 public ReservationResponse adminCreate(AdminReservationRequest req)
  {validateTimes(req.startTime(),req.endTime());
   AppUser user=users.findById(req.userId()).orElseThrow(()->new NotFoundException("User not found"));
   Resource resource=resources.findById(req.resourceId()).orElseThrow(()->new NotFoundException("Resource not found"));
    if(!resource.isAvailable())throw new BadRequestException("Resource is not available");
   ensureNoOverlap(resource.getId(),req.startTime(),req.endTime(),null);
   Reservation x=new Reservation();x.setUser(user);x.setResource(resource);
   x.setStartTime(req.startTime());
   x.setEndTime(req.endTime());x.setPrice(req.price());
   x.setStatus(req.status()==null?ReservationStatus.PENDING:req.status())
    ;return dto(reservations.save(x));
  }
 private Page<ReservationResponse> search(String username,ReservationStatus status,BigDecimal min,BigDecimal max,Pageable p)
  {Specification<Reservation>s=Specification.allOf(ReservationSpecifications.username(username),ReservationSpecifications.status(status),ReservationSpecifications.minPrice(min),ReservationSpecifications.maxPrice(max));
   return reservations.findAll(s,p).map(this::dto);
  }
 private void ensureNoOverlap(Long resourceId,java.time.LocalDateTime start,java.time.LocalDateTime end,Long ignoreId)
  {
   for(Reservation x:reservations.findAll())
   {if(ignoreId!=null&&ignoreId.equals(x.getId()))continue;
    if(x.getResource().getId().equals(resourceId)&&x.getStatus()!=ReservationStatus.CANCELLED&&start.isBefore(x.getEndTime())&&end.isAfter(x.getStartTime()))throw new BadRequestException("Resource is already reserved for the requested time");
   }
  }
 private void validateTimes(java.time.LocalDateTime s,java.time.LocalDateTime e)
  {if(!e.isAfter(s))throw new BadRequestException("endTime must be after startTime");
  }
 private AppUser user(String u){
  return users.findByUsername(u).orElseThrow(()->new NotFoundException("Authenticated user not found"));
 }
 private Reservation find(Long id)
  {return reservations.findById(id).orElseThrow(()->new NotFoundException("Reservation not found: "+id));
  }
 private ReservationResponse dto(Reservation x)
  {return new ReservationResponse(x.getId(),x.getResource().getId(),x.getResource().getName(),x.getUser().getId(),x.getUser().getUsername(),x.getStartTime(),x.getEndTime(),x.getPrice(),x.getStatus());
  }
}
