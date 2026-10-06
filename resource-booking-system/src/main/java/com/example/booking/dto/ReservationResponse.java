package com.example.booking.dto;
import java.math.BigDecimal; 
import java.time.LocalDateTime;
import com.example.booking.entity.ReservationStatus;
public record ReservationResponse(Long id,Long resourceId,String resourceName,Long userId,String username,LocalDateTime startTime,LocalDateTime endTime,BigDecimal price,ReservationStatus status) {}
