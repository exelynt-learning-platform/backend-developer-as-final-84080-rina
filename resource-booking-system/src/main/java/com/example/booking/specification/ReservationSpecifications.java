package com.example.booking.specification;
import com.example.booking.entity.*; import org.springframework.data.jpa.domain.Specification; import java.math.BigDecimal;
public final class ReservationSpecifications{
 private ReservationSpecifications(){}
 public static Specification<Reservation> status(ReservationStatus s){return (r,q,c)->s==null?null:c.equal(r.get("status"),s);}
 public static Specification<Reservation> minPrice(BigDecimal p){return (r,q,c)->p==null?null:c.greaterThanOrEqualTo(r.get("price"),p);}
 public static Specification<Reservation> maxPrice(BigDecimal p){return (r,q,c)->p==null?null:c.lessThanOrEqualTo(r.get("price"),p);}
 public static Specification<Reservation> username(String u){return (r,q,c)->u==null?null:c.equal(r.get("user").get("username"),u);}
}
