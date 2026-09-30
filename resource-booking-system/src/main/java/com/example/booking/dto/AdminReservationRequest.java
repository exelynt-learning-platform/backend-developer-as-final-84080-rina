package com.example.booking.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.time.LocalDateTime; import com.example.booking.entity.ReservationStatus;
public record AdminReservationRequest(@NotNull Long resourceId,@NotNull Long userId,@NotNull LocalDateTime startTime,@NotNull LocalDateTime endTime,@NotNull @DecimalMin("0.01") BigDecimal price,ReservationStatus status) {}
